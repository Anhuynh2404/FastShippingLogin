package com.example.fastshippinglogin.viewmodel.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fastshippinglogin.Model.CartItem
import com.example.fastshippinglogin.Model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class CartViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun addToCart(cartItem: CartItem) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val cartRef = db.collection("carts").document(userId)
            val productRef = db.collection("products").document(cartItem.productId)

            db.runTransaction { transaction ->
                val cartSnapshot = transaction.get(cartRef)
                val productSnapshot = transaction.get(productRef)

                val productData = productSnapshot.toObject(Product::class.java)
                if (productData != null && productSnapshot.exists()) {
                    val cartItemsRef = cartRef.collection("items").document(cartItem.productId)
                    val cartItemSnapshot = transaction.get(cartItemsRef)

                    val newTotalPrice = cartSnapshot.getDouble("totalPrice")?.plus(productData.moneyProduct.toInt() * cartItem.quantity)
                        ?: (productData.moneyProduct.toInt() * cartItem.quantity).toDouble()
                    val newTotalItems = cartSnapshot.getLong("totalItems")?.plus(cartItem.quantity) ?: cartItem.quantity.toLong()

                    if (cartItemSnapshot.exists()) {
                        val newQuantity = cartItemSnapshot.getLong("quantity")?.plus(cartItem.quantity) ?: cartItem.quantity.toLong()
                        transaction.update(cartItemsRef, "quantity", newQuantity)
                    } else {
                        val cartItemData = hashMapOf(
                            "productId" to cartItem.productId,
                            "name" to cartItem.name,
                            "price" to cartItem.price,
                            "imageUrl" to cartItem.imageUrl,
                            "quantity" to cartItem.quantity,
                            "userId" to userId
                        )
                        transaction.set(cartItemsRef, cartItemData)
                    }

                    transaction.update(cartRef, "totalPrice", newTotalPrice)
                    transaction.update(cartRef, "totalItems", newTotalItems)
                }
            }
                .addOnSuccessListener {
                    // Xử lý thành công
                }
                .addOnFailureListener { e ->
                    // Xử lý khi thất bại
                    Log.e("CartViewModel", "Failed to add to cart", e)
                }
        }
    }

}
