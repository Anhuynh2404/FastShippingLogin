package com.example.fastshippinglogin.View.cartview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fastshippinglogin.Model.CartItem
import com.example.fastshippinglogin.R
import com.example.fastshippinglogin.viewmodel.cart.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.livedata.observeAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController, cartViewModel: CartViewModel = viewModel()) {
    val cartItems by cartViewModel.cartItems.observeAsState(emptyList())

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
            BottomBar(cartItems = cartItems, navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Hiển thị danh sách sản phẩm trong giỏ hàng
            cartItems.forEach { cartItem ->
                CartItem(cartItem, cartViewModel)
            }
        }
    }
}

@Composable
fun CartItem(cartItem: CartItem, cartViewModel: CartViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hình ảnh sản phẩm
        Image(
            painter = rememberImagePainter(data = cartItem.product?.imgProduct),
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
            cartItem.product?.let { Text(text = it.nameProduct) }
            Text(text = "Giá: ${cartItem.product?.moneyProduct} VND")
            QuantityButton(cartItem, cartViewModel)
        }

        // Nút xóa sản phẩm khỏi giỏ hàng
        IconButton(onClick = { cartViewModel.removeCartItem(cartItem) }) {
            Icon(Icons.Default.Delete, contentDescription = "Xóa")
        }
    }
}

@Composable
fun QuantityButton(cartItem: CartItem, cartViewModel: CartViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            if (cartItem.quantity > 1) {
                cartViewModel.updateCartItemQuantity(cartItem, cartItem.quantity - 1)
            }
        }) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Giảm số lượng")
        }
        Text(text = cartItem.quantity.toString(), fontSize = 16.sp, modifier = Modifier.padding(horizontal = 8.dp))
        IconButton(onClick = {
            cartViewModel.updateCartItemQuantity(cartItem, cartItem.quantity + 1)
        }) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Tăng số lượng")
        }
    }
}

@Composable
fun BottomBar(cartItems: List<CartItem>, navController: NavHostController) {
    val totalQuantity = cartItems.sumBy { it.quantity }
    val totalPrice = cartItems.sumBy { it.quantity * (it.product?.moneyProduct?.toInt() ?: 0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
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
