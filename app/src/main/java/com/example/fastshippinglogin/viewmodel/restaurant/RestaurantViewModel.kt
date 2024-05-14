package com.example.fastshippinglogin.viewmodel.restaurant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fastshippinglogin.Model.Category
import com.example.fastshippinglogin.Model.Product
import com.example.fastshippinglogin.Model.Restaurant
import com.example.fastshippinglogin.data.fetchRestaurantData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RestaurantViewModel : ViewModel() {
    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> get() = _restaurants


    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products
    private val _categories = MutableStateFlow<Map<String, Category>>(emptyMap())
    val categories: StateFlow<Map<String, Category>> get() = _categories
    ///////////////////////////////////////
    private val _restaurant = MutableStateFlow<Restaurant?>(null)
    val restaurant: StateFlow<Restaurant?> get() = _restaurant
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> get() = _product
    init {
        fetchRestaurants()
        fetchCategories()
    }
    private fun fetchRestaurants() {
        val db = FirebaseFirestore.getInstance()
        db.collection("restaurants")
            .get()
            .addOnSuccessListener { result ->
                val restaurantList = result.map { document ->
                    val restaurant = document.toObject(Restaurant::class.java).copy(id = document.id)
                    Log.d("Firestore", "Fetched restaurant: $restaurant")
                    restaurant
                }
                _restaurants.value = restaurantList
            }
            .addOnFailureListener { exception ->
            }
    }

    fun fetchProductsByRestaurantId(restaurantId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("products")
            .whereEqualTo("id_Restaurant", restaurantId)
            .get()
            .addOnSuccessListener { result ->
                val productList = result.map { document ->
                    document.toObject(Product::class.java).copy(id = document.id)
                }
                _products.value = productList
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }
    private fun fetchCategories() {
        val db = FirebaseFirestore.getInstance()
        db.collection("categories")
            .get()
            .addOnSuccessListener { result ->
                val categoryList = result.map { document ->
                    document.toObject(Category::class.java).copy(id = document.id)
                }
                _categories.value = categoryList.associateBy { it.id }
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

    fun getProductById(productId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("products")
            .document(productId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val product = document.toObject(Product::class.java)
                    _product.value = product
                } else {
                    _product.value = null
                    // Xử lý trường hợp không tìm thấy sản phẩm
                }
            }
            .addOnFailureListener { exception ->
                _product.value = null
                // Xử lý trường hợp lỗi khi truy vấn
            }
    }
}
