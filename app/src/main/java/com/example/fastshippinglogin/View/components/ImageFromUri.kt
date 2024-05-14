package com.example.fastshippinglogin.View.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import coil.compose.rememberImagePainter

@Composable
fun ImageFromUri(uri: Comparable<Any>) {
    val painter = rememberImagePainter(uri)
    Image(
        painter = painter,
        contentDescription = null
    )
}
