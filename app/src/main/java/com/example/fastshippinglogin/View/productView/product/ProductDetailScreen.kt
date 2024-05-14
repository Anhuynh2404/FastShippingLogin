package com.example.fastshippinglogin.View.productView.product

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.fastshippinglogin.Model.CartItem
import com.example.fastshippinglogin.Model.Product
import com.example.fastshippinglogin.View.productView.RestaurantInfo
import com.example.fastshippinglogin.viewmodel.cart.CartViewModel
import com.example.fastshippinglogin.viewmodel.restaurant.RestaurantViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProductDetailScreen(navController: NavHostController, productId: String?, viewModel: RestaurantViewModel = viewModel(), cartViewModel: CartViewModel = viewModel()) {
    val product by viewModel.product.collectAsState()

    LaunchedEffect(productId) {
        if (productId != null && productId.isNotEmpty()) {
            Log.d("ProductDetailScreen", "ProductId: $productId")
            viewModel.getProductById(productId)
        } else {
            Log.e("ProductDetailScreen", "Invalid ProductId: $productId")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Chi tiết sản phẩm") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle favorite action */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Yêu thích")
                    }
                }
            )
        },
        bottomBar = {
            product?.let {
                BottomBar(
                    product = it,
                    onAddToCart = { quantity ->
                        val cartItem = CartItem(
                            productId = it.id,
                            name = it.nameProduct,
                            price = it.moneyProduct,
                            imageUrl = it.imgProduct,
                            quantity = quantity,
                            userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                        )
                        cartViewModel.addToCart(cartItem)
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            product?.let { food ->
                ProductInfor(food)
            } ?: run {
                Text(text = "Loading...", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun ProductInfor(product: Product) {
    Column {
        Image(
            painter = rememberImagePainter(data = product.imgProduct),
            contentDescription = "Ảnh sản phẩm",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        // Tên sản phẩm
        Text(
            text = product.nameProduct,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // Giá tiền
        Text(
            text = "Giá: ${product.moneyProduct} VND",
            fontSize = 20.sp,
            color = Color.Red,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Số sao và số đơn mua
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.Yellow)
            Text(text = "4.5", modifier = Modifier.padding(start = 4.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "1000 đơn đã mua")
        }
    }
}

@Composable
fun BottomBar(product: Product, onAddToCart: (Int) -> Unit) {
    var quantity by remember { mutableStateOf(1) }
   //val totalPrice = (quantity * price.toInt()).toString()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primaryVariant)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Nút tăng giảm số lượng
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CartButton(
                icon = Icons.Default.KeyboardArrowDown,
                onClick = {
                    if (quantity > 1) quantity--
                }
            )
            Text(text = quantity.toString(), fontSize = 18.sp, modifier = Modifier.padding(horizontal = 8.dp), color = Color(0xFFFFF6EE))
            CartButton(
                icon = Icons.Default.KeyboardArrowUp,
                onClick = { quantity++ }
            )
        }

        // Hiển thị tổng giá tiền
        Text(text = "${product.moneyProduct.toInt() * quantity} VND", fontSize = 18.sp, color = Color(0xFFFFF6EE))

        // Nút thêm vào giỏ hàng
        CartButton(
            icon = Icons.Default.ShoppingCart,
            onClick = {
                onAddToCart(quantity)
            }
        )
    }
}

@Composable
fun CartButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFFFFF6EE))
        }
    }
}
