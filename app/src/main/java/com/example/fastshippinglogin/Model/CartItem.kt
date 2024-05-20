package com.example.fastshippinglogin.Model

data class CartItem(
    val userId: String,
    val productId: String,
    val quantity: Int,
    val restaurantId: String = ""
){
    constructor():this("","",0)
}

