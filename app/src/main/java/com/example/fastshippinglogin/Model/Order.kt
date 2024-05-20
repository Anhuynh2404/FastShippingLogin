package com.example.fastshippinglogin.Model

data class Order(
    val userId: String,
    val items: List<OrderItem>,
    val totalOrderAmount: Int,
    val shippingFee: Int,
    val discount: Int,
    val totalPaymentAmount: Int,
    val statusOrder: String,
    val address: String,
    val phone: String,
    val paymentMethod: String
)

data class OrderItem(
    val productId: String,
    val quantity: Int,
    val price: Int
)

