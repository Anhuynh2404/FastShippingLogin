package com.example.fastshippinglogin.Model

data class User(
    val firstName: String?,
    val lastName: String?,
    val emailUser: String,
    val phone: String? = null,
    val address: String? = null,
    val imageUrl: String? = null
) {
    constructor(): this("", "", "", "", "", "")
}
