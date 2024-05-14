package com.example.fastshippinglogin.Model

data class UserProfile(
    val firstName: String?,
    val lastName: String?,
    val email: String,
    val phone: String?,
    val address: String?,
    val imageUrl: String?
) {
    constructor(): this("", "", "", "", "", "")
}
