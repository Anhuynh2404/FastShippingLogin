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

    init {
        loadCartItems()
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

    fun addToCart(userId: String, product: Product, quantity: Int) {
        val cartItem = CartItem(userId, product, quantity)
        db.collection("carts")
            .document(userId)
            .collection("items")
            .add(cartItem)
            .addOnSuccessListener {
                Log.d("CartViewModel", "Item added to cart")
            }
            .addOnFailureListener { e ->
                Log.e("CartViewModel", "Error adding item to cart", e)
            }
    }

    fun updateCartItemQuantity(cartItem: CartItem, newQuantity: Int) {
        currentUser?.let { user ->
            val cartRef = db.collection("carts")
                .document(user.uid)
                .collection("items")
                .document(cartItem.product?.id ?: "")

            cartRef.update("quantity", newQuantity)
                .addOnSuccessListener {
                    Log.d("CartViewModel", "Item quantity updated")
                }
                .addOnFailureListener { e ->
                    Log.e("CartViewModel", "Error updating item quantity", e)
                }
        }
    }

    fun removeCartItem(cartItem: CartItem) {
        currentUser?.let { user ->
            val cartRef = db.collection("carts")
                .document(user.uid)
                .collection("items")
                .document(cartItem.product?.id ?: "")

            cartRef.delete()
                .addOnSuccessListener {
                    Log.d("CartViewModel", "Item removed from cart")
                }
                .addOnFailureListener { e ->
                    Log.e("CartViewModel", "Error removing item from cart", e)
                }
        }
    }
}

