package com.example.fastshippinglogin.Model

data class CartItem(
    val id : String="",
    val productId: String = "",
    val name: String = "",
    val price: String = "",
    val imageUrl: String = "",
    val quantity: Int = 0,
    val userId: String=""
)
