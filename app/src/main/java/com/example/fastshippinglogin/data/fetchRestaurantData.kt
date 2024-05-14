package com.example.fastshippinglogin.data

import com.example.fastshippinglogin.Model.Restaurant
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun fetchRestaurantData(): List<Restaurant> {
    val db = FirebaseFirestore.getInstance()
    val snapshot = db.collection("Restaurant").get().await()
    return snapshot.documents.map { document ->
        document.toObject(Restaurant::class.java) ?: Restaurant()
    }
}
