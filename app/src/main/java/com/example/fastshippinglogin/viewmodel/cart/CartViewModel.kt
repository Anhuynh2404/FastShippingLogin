package com.example.fastshippinglogin.viewmodel.cart

import androidx.lifecycle.ViewModel
import com.example.fastshippinglogin.Model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun addToCart(productId: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val cartRef = db.collection("carts").document(currentUser.uid)
            val productRef = db.collection("products").document(productId)

            db.runTransaction { transaction ->
                val cartSnapshot = transaction.get(cartRef)
                val productSnapshot = transaction.get(productRef)

                val productData = productSnapshot.toObject(Product::class.java)
                if (productData != null) {
                    val newTotalPrice = cartSnapshot.getDouble("totalPrice")?.plus(productData.moneyProduct.toInt())
                        ?: productData.moneyProduct.toInt()
                    val newTotalItems = cartSnapshot.getLong("totalItems")?.plus(1) ?: 1

                    transaction.update(cartRef, "totalPrice", newTotalPrice)
                    transaction.update(cartRef, "totalItems", newTotalItems)
                    transaction.set(cartRef.collection("items").document(productId), productData)
                }
            }
                .addOnSuccessListener {
                    // Xử lý thành công
                }
                .addOnFailureListener { e ->
                    // Xử lý khi thất bại
                }
        } else {

        }
    }
}
