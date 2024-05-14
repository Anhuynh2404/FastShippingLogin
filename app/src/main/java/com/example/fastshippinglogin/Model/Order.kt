package com.example.fastshippinglogin.Model

data class Order(
    val name : String,
    val quantity: Int,
    val price : Double,
    val status: String,

){
    constructor():this("",0,0.0,"")
}
