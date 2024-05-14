package com.example.fastshippinglogin.Model

data class CartItem(
    val userId: String,
    val product: Product?,
    val quantity: Int
){
    constructor():this("",null,0)
}

