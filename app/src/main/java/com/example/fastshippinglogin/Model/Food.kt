package com.example.fastshippinglogin.Model

data class Food(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val price: Double?,
    val description: String?,
    //val categoryIds: List<String>,
){
    constructor():this("","","",0.0,"")
}
