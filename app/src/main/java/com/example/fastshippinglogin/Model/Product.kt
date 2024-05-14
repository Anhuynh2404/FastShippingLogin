package com.example.fastshippinglogin.Model

data class Product(
    val id: String = "",
    val contentProduct: String = "",
    val id_Category: String = "",
    val id_Restaurant: String = "",
    val imgProduct: String = "",
    val moneyProduct: String = "",
    val nameProduct: String = "",
    val statusProduct: Boolean = true
) {
    constructor() : this("", "", "", "", "", "", "", true)
}
