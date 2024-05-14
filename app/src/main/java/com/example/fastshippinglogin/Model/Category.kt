package com.example.fastshippinglogin.Model

import android.net.Uri

data class Category(
    val name: String,
    val imageUri: Uri?
){
    constructor():this("",null)
}
