package com.example.fastshippinglogin.Model

data class Cart(
    val userId: String,
    val foods: List<Food>
){
    constructor(): this("", emptyList())
}