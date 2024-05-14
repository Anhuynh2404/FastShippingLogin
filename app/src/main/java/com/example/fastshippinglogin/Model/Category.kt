package com.example.fastshippinglogin.Model

import android.net.Uri

data class Category(
    val id: String = "",
    val nameCategory: String,
   // val imageUri: Uri?
){
    constructor():this("","")
}
