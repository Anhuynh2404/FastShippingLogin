package com.example.fastshippinglogin.Model

data class CartItem(
    val userId: String,
    val productId: String,
    val quantity: Int
){
    constructor():this("","",0)
}

