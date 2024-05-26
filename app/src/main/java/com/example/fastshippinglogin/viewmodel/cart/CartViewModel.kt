package com.example.fastshippinglogin.viewmodel.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastshippinglogin.Model.CartItem
import com.example.fastshippinglogin.Model.Order
import com.example.fastshippinglogin.Model.OrderItem
import com.example.fastshippinglogin.Model.Product
import com.example.fastshippinglogin.data.PaymentMethod
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val db = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    private val _products = MutableLiveData<Map<String, Product>>()
    val products: LiveData<Map<String, Product>> = _products

    private var selectedPaymentMethod: String = PaymentMethod.CASH.toString()
    private var selectedPhone: String = ""


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


    fun addToCart(productId: String, userId: String, product: Product, quantity: Int, onFailure: (String) -> Unit) {
        val cartRef = db.collection("carts").document(userId).collection("items")

        cartRef.get().addOnSuccessListener { snapshot ->
            val currentCartItems = snapshot.documents.mapNotNull { it.toObject(CartItem::class.java) }
            val currentRestaurantId = currentCartItems.firstOrNull()?.restaurantId

            if (currentRestaurantId != null && currentRestaurantId != product.id_Restaurant) {
                onFailure("Giỏ hàng chỉ có thể chứa các món ăn từ cùng một nhà hàng. Vui lòng xóa hoặc đặt hàng giỏ hàng hiện tại trước khi thêm món ăn từ nhà hàng mới.")
                return@addOnSuccessListener
            }

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
                        val cartItem = CartItem(userId, productId, quantity, product.id_Restaurant)
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
    fun clearCart() {
        currentUser?.let { user ->
            val cartRef = db.collection("carts").document(user.uid).collection("items")
            cartRef.get()
                .addOnSuccessListener { snapshot ->
                    val batch = db.batch()
                    for (document in snapshot.documents) {
                        batch.delete(document.reference)
                    }
                    batch.commit()
                        .addOnSuccessListener {
                            Log.d("CartViewModel", "Cart cleared successfully")
                            _cartItems.value = emptyList() // Update local cart items
                        }
                        .addOnFailureListener { e ->
                            Log.e("CartViewModel", "Error clearing cart", e)
                        }
                }
                .addOnFailureListener { e ->
                    Log.e("CartViewModel", "Error getting cart items to clear", e)
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


    fun placeOrder(order: Order, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val orderData = hashMapOf(
            "userId" to order.userId,
            "items" to order.items.map { item ->
                hashMapOf(
                    "productId" to item.productId,
                    "quantity" to item.quantity,
                    "price" to item.price
                )
            },
            "totalOrderAmount" to order.totalOrderAmount,
            "shippingFee" to order.shippingFee,
            "discount" to order.discount,
            "totalPaymentAmount" to order.totalPaymentAmount,
            "statusOrder" to order.statusOrder,
            "address" to order.address,
            "phone" to order.phone,
            "paymentMethod" to order.paymentMethod,
            "timestamp" to FieldValue.serverTimestamp()
        )

        db.collection("orders")
            .add(orderData)
            .addOnSuccessListener {
                onSuccess()
                clearCart()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun getOrderbyStatus(userId: String, statusOrder: String, onSuccess: (List<Order>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("orders")
            .whereEqualTo("userId", userId)
            .whereEqualTo("statusOrder", statusOrder)
            .get()
            .addOnSuccessListener { documents ->
                val orders = documents.map { document ->
                    Order(
                        userId = document.getString("userId")!!,
                        items = (document.get("items") as List<Map<String, Any>>).map { item ->
                            OrderItem(
                                productId = item["productId"] as String,
                                quantity = (item["quantity"] as Long).toInt(),
                                price = (item["price"] as Long).toInt()
                            )
                        },
                        totalOrderAmount = (document.getLong("totalOrderAmount") ?: 0L).toInt(),
                        shippingFee = (document.getLong("shippingFee") ?: 0L).toInt(),
                        discount = (document.getLong("discount") ?: 0L).toInt(),
                        totalPaymentAmount = (document.getLong("totalPaymentAmount") ?: 0L).toInt(),
                        statusOrder = document.getString("statusOrder")!!,
                        address = document.getString("address")!!,
                        phone = document.getString("phone")!!,
                        paymentMethod = document.getString("paymentMethod")!!
                    )
                }
                onSuccess(orders)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }
    fun getShippingFee(): Int {
        return 20000
    }
    fun getDiscount(): Int {
        return 10000
    }
    fun getPaymentMethod(): String {
        return selectedPaymentMethod
    }
    fun setPaymentMethod(method: PaymentMethod) {
        selectedPaymentMethod = method.toString()
    }

    fun getPhone(): String {
        return selectedPhone
    }
    fun setPhone(method: String) {
        selectedPhone = method
    }


}

