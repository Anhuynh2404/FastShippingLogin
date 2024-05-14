package com.example.fastshippinglogin.viewmodel.restaurant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fastshippinglogin.Model.Restaurant
import com.example.fastshippinglogin.data.fetchRestaurantData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RestaurantViewModel : ViewModel() {
    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> get() = _restaurants

    private val _restaurant = MutableStateFlow<Restaurant?>(null)
    val restaurant: StateFlow<Restaurant?> get() = _restaurant

    init {
        fetchRestaurants()
    }
    private fun fetchRestaurants() {
        val db = FirebaseFirestore.getInstance()
        db.collection("restaurants")
            .get()
            .addOnSuccessListener { result ->
                val restaurantList = result.map { document ->
                    val restaurant = document.toObject(Restaurant::class.java).copy(id = document.id)
//                    val data = document.data
//                    Log.d("Firestore", "Document data: $data")
//                    Restaurant.fromMap(data, document.id)
                    Log.d("Firestore", "Fetched restaurant: $restaurant")
                    restaurant
                }
                _restaurants.value = restaurantList
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    fun getRestaurantById(id: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("restaurants").document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    _restaurant.value = document.toObject(Restaurant::class.java)?.copy(id = document.id)
                } else {
                    Log.d("Firestore", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error getting document", exception)
            }
    }
//
//    private fun fetchRestaurants() {
//        viewModelScope.launch {
//            val restaurantList = fetchRestaurantData()
//            _restaurants.value = restaurantList
//        }
//    }
}
