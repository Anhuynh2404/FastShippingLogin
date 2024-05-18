package com.example.fastshippinglogin.viewmodel.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastshippinglogin.Model.CartItem
import com.example.fastshippinglogin.Model.Product
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Tag
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val db = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    private val _products = MutableLiveData<Map<String, Product>>()
    val products: LiveData<Map<String, Product>> = _products


    init {
        loadCartItems()
        if (currentUser != null) {
            db.collection("carts")
                .document(currentUser.uid)
                .collection("items")
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        // Handle error
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        val items = snapshot.documents.mapNotNull { it.toObject(CartItem::class.java) }
                        _cartItems.value = items
                        fetchProducts(items.map { it.productId })
                    }
                }
        }
    }
    private fun fetchProducts(productIds: List<String>) {
        val validProductIds = productIds.filter { it.isNotEmpty() }

        if (validProductIds.isNotEmpty()) {
            db.collection("products")
                .whereIn(FieldPath.documentId(), validProductIds)
                .get()
                .addOnSuccessListener { documents ->
                    val productsMap = documents.documents.associateBy(
                        { it.id },
                        { it.toObject(Product::class.java)!! }
                    )
                    _products.value = productsMap
                }
                .addOnFailureListener { e ->
                    Log.e("CartViewModel", "Error fetching products", e)
                }
        } else {
            Log.e("CartViewModel", "No valid product IDs to query")
        }
    }

    private fun loadCartItems() {
        currentUser?.let { user ->
            val cartRef = db.collection("carts").document(user.uid).collection("items")
            cartRef.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("CartViewModel", "Error loading cart items", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val items = snapshot.documents.mapNotNull { document ->
                        document.toObject(CartItem::class.java)
                    }
                    _cartItems.value = items
                }
            }
        }
    }

    fun addToCart(productId:String,userId: String, product: Product, quantity: Int) {
        val cartRef = db.collection("carts").document(userId).collection("items")

        cartRef.whereEqualTo("productId", productId).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // Sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng
                    for (document in documents) {
                        val existingItem = document.toObject(CartItem::class.java)
                        val newQuantity = existingItem.quantity + quantity
                        cartRef.document(document.id).update("quantity", newQuantity)
                            .addOnSuccessListener {
                                Log.d("CartViewModel", "Item quantity updated in cart")
                            }
                            .addOnFailureListener { e ->
                                Log.e("CartViewModel", "Error updating item quantity in cart", e)
                            }
                    }
                } else {
                    // Sản phẩm chưa tồn tại trong giỏ hàng, thêm mới
                    val cartItem = CartItem(userId, productId, quantity)
                    cartRef.add(cartItem)
                        .addOnSuccessListener {
                            Log.d("CartViewModel", "Item added to cart")
                        }
                        .addOnFailureListener { e ->
                            Log.e("CartViewModel", "Error adding item to cart", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("CartViewModel", "Error checking item in cart", e)
            }
    }


    fun updateCartItemQuantity(cartItem: CartItem, newQuantity: Int) {
        val userId = cartItem.userId
        val productId = cartItem.productId ?: return

        val cartRef = db.collection("carts").document(userId).collection("items")

        cartRef.whereEqualTo("productId", productId).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        cartRef.document(document.id).update("quantity", newQuantity)
                            .addOnSuccessListener {
                                Log.d("CartViewModel", "Item quantity updated in cart")
                                // Update the local list of cart items
                                loadCartItems(userId)
                            }
                            .addOnFailureListener { e ->
                                Log.e("CartViewModel", "Error updating item quantity in cart", e)
                            }
                    }
                }else {
                    Log.d("CartViewModel", "No matching items found to remove")
                }
            }
            .addOnFailureListener { e ->
                Log.e("CartViewModel", "Error checking item in cart", e)
            }
    }
    fun removeCartItem(cartItem: CartItem) {
        currentUser?.let { user ->
            val cartRef = db.collection("carts")
                .document(user.uid)
                .collection("items")

            cartRef.whereEqualTo("productId", cartItem.productId).get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        for (document in documents) {
                            cartRef.document(document.id).delete()
                                .addOnSuccessListener {
                                    Log.d("CartViewModel", "Item removed from cart")
                                    // Update the local list of cart items
                                    loadCartItems(user.uid)
                                }
                                .addOnFailureListener { e ->
                                    Log.e("CartViewModel", "Error removing item from cart", e)
                                }
                        }
                    } else {
                        Log.d("CartViewModel", "No matching items found to remove")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("CartViewModel", "Error checking item in cart", e)
                }
        }
    }
    fun loadCartItems(userId: String) {
        db.collection("carts").document(userId).collection("items")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("CartViewModel", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val items = snapshot.documents.mapNotNull { it.toObject(CartItem::class.java) }
                    _cartItems.value = items
                }
            }
    }


}

