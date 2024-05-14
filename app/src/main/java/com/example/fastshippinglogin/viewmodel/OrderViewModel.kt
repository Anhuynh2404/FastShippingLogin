package com.example.fastshippinglogin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastshippinglogin.Model.Order
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class OrderViewModel : ViewModel() {
    private val db = Firebase.firestore
    val orders = MutableLiveData<List<Order>>()

    init {
        fetchOrders()
    }
    private fun fetchOrders() {
        db.collection("orders")
            .get()
            .addOnSuccessListener { result ->
                val orderList = result.map { doc -> doc.toObject(Order::class.java) }
                orders.value = orderList
            }
            .addOnFailureListener { exception ->
                // Log.w(Tag, "Error getting documents: ", exception)
            }
    }

    fun addOrder(order: Order) {
        db.collection("orders")
            .add(order)
            .addOnSuccessListener { documentReference ->
                //Log.d(Tag, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                //  Log.w(TAG, "Error adding document", e)
            }
    }

}