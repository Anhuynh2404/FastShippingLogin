package com.example.fastshippinglogin.View.cartview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController) {
    // Lấy danh sách sản phẩm trong giỏ hàng từ ViewModel
   // val cartItems by viewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Giỏ hàng") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        },
        bottomBar = {
            //BottomBar(cartItems)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Hiển thị danh sách sản phẩm trong giỏ hàng
//            cartItems.forEach { cartItem ->
//                CartItem(cartItem, viewModel)
//            }
        }
    }
}

@Composable
fun CartItem(cartItem: CartItem, viewModel: CartViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hình ảnh sản phẩm
        Image(
            painter = rememberImagePainter(data = cartItem.product.imgProduct),
            contentDescription = "Ảnh sản phẩm",
            modifier = Modifier
                .size(64.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Thông tin sản phẩm (tên, giá, số lượng)
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = cartItem.product.nameProduct)
            Text(text = "Giá: ${cartItem.product.moneyProduct} VND")
            QuantityButton(cartItem, viewModel)
        }

        // Nút xóa sản phẩm khỏi giỏ hàng
        IconButton(onClick = { viewModel.removeFromCart(cartItem.product.id) }) {
            Icon(Icons.Default.Delete, contentDescription = "Xóa")
        }
    }
}

@Composable
fun QuantityButton(cartItem: CartItem, viewModel: CartViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { viewModel.decreaseQuantity(cartItem.product.id) }) {
            Icon(Icons.Default.Remove, contentDescription = "Giảm số lượng")
        }
        Text(text = cartItem.quantity.toString(), fontSize = 16.sp, modifier = Modifier.padding(horizontal = 8.dp))
        IconButton(onClick = { viewModel.increaseQuantity(cartItem.product.id) }) {
            Icon(Icons.Default.Add, contentDescription = "Tăng số lượng")
        }
    }
}

@Composable
fun BottomBar(cartItems: List<CartItem>) {
    val totalQuantity = cartItems.sumBy { it.quantity }
    val totalPrice = cartItems.sumBy { it.product.moneyProduct.toInt() * it.quantity }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primaryVariant)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hiển thị số lượng sản phẩm trong giỏ hàng
        Text(text = "Số lượng: $totalQuantity", color = Color.White)

        // Hiển thị tổng tiền
        Text(text = "Tổng tiền: $totalPrice VND", color = Color.White)

        // Nút đặt hàng
        Button(onClick = { /* Đặt hàng */ }) {
            Text(text = "Đặt hàng")
        }
    }
}
