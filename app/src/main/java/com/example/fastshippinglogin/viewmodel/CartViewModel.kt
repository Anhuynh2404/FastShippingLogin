package com.example.fastshippinglogin.viewmodel

import com.example.fastshippinglogin.Model.Cart
import com.example.fastshippinglogin.Model.Food
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

fun addToCart(food: Food, userId: String) {
    val firestore = Firebase.firestore
    val cartRef = firestore.collection("carts").document(userId)

    cartRef.get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val existingCart = document.toObject(Cart::class.java)
                val updatedCart = existingCart?.copy(foods = existingCart.foods + listOf(food))
                if (updatedCart != null) {
                    cartRef.set(updatedCart)
                        .addOnSuccessListener {
                            println("Food added to cart")
                        }
                        .addOnFailureListener { e ->
                            println("Error adding food to cart: $e")
                        }
                }
            } else {
                val newCart = Cart(userId, listOf(food))
                cartRef.set(newCart)
                    .addOnSuccessListener {
                        println("Food added to cart")
                    }
                    .addOnFailureListener { e ->
                        println("Error adding food to cart: $e")
                    }
            }
        }
        .addOnFailureListener { e ->
            println("Error getting cart: $e")
        }
}