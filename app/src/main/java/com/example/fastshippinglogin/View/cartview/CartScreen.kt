package com.example.fastshippinglogin.View.cartview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.fastshippinglogin.Model.Product
import com.example.fastshippinglogin.ui.theme.mainColorBlue
import com.example.fastshippinglogin.ui.theme.mainColorWhite
import com.example.fastshippinglogin.ui.theme.mainColorOrrange
import com.example.fastshippinglogin.ui.theme.mainColorWhite
import com.example.fastshippinglogin.ui.theme.mainColorBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController, cartViewModel: CartViewModel = viewModel()) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val cartItems by cartViewModel.cartItems.observeAsState(emptyList())
    val products by cartViewModel.products.observeAsState(emptyMap())

    LaunchedEffect(currentUser) {
        currentUser?.uid?.let { userId ->
            cartViewModel.loadCartItems(userId)
        }
    }
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    mainColorBlue,
                    contentColorFor(mainColorWhite)
                )
            )
        },
        bottomBar = {
            if (cartItems.isEmpty()){
                BottomBarNull(navController = navController)
            }else {
                BottomBar(cartItems = cartItems, products = products, navController = navController)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(color = Color.LightGray)
        ) {
            if (cartItems.isEmpty()) {
               Column(
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally,
               ) {
                   Image(
                       painter = painterResource(id = R.drawable.order_icon),
                       contentDescription = "",
                       Modifier.size(50.dp)
                   )
                   Text(
                       text = "Giỏ hàng của bạn đang trống.",
                       modifier = Modifier
                           .fillMaxSize()
                           .padding(20.dp),
                       fontSize = 18.sp,
                       color = Color.Gray
                   )
               }
            }else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = CardDefaults.cardColors(mainColorWhite)
                ) {
                    Text(
                        text = "Madam Nguyễn",
                        modifier = Modifier.padding(20.dp)
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    shape = RectangleShape,
                    colors = CardDefaults.cardColors(mainColorWhite)
                ) {
                    cartItems.forEach { cartItem ->
                        CartItem(cartItem, products[cartItem.productId], cartViewModel)
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                            //.padding(vertical = 8.dp)
                        )
                    }
                }

                Card(
                    modifier = Modifier.padding(top = 5.dp),
                    shape = RectangleShape,
                    colors = CardDefaults.cardColors(mainColorWhite)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Tổng đơn hàng",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(10.dp),
                                fontSize = 18.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "155,000 đ",
                                modifier = Modifier.padding(10.dp),
                                fontSize = 18.sp,
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Giảm giá ",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 10.dp, bottom = 10.dp),
                                fontSize = 18.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "1000 đ",
                                modifier = Modifier.padding(end = 10.dp, bottom = 10.dp),
                                fontSize = 18.sp,
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Phí ship dự kiến",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 10.dp, bottom = 10.dp),
                                fontSize = 18.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "155,000 đ",
                                modifier = Modifier.padding(end = 10.dp, bottom = 10.dp),
                                fontSize = 18.sp,
                            )
                        }
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp)
                        ) {
                            Text(
                                text = "Tổng tiền thanh toán",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(10.dp),
                                fontSize = 18.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "155,000 đ",
                                modifier = Modifier.padding(10.dp),
                                fontSize = 18.sp,
                                color = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartItem(cartItem: CartItem, product: Product?,viewModel: CartViewModel) {
    val imagePainter = rememberImagePainter(
        data = product?.imgProduct,
        builder = {
            error(R.drawable.ic_launcher_background) // Sử dụng ảnh mặc định khi xảy ra lỗi
            placeholder(R.drawable.logo) // Sử dụng ảnh mặc định trong khi tải
        }
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hình ảnh sản phẩm
        Image(
            painter = imagePainter,
            contentDescription = "Ảnh sản phẩm",
            modifier = Modifier
                .size(64.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            product?.let { Text(text = it.nameProduct) }
            Text(text = "Giá: ${product?.moneyProduct} VND")
            QuantityButton(cartItem, cartViewModel = viewModel)
        }

        // Nút xóa sản phẩm khỏi giỏ hàng
        IconButton(onClick = { viewModel.removeCartItem(cartItem) }) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(cartItems: List<CartItem>, products: Map<String, Product>, navController: NavHostController) {
    val totalQuantity = cartItems.sumBy { it.quantity }
    val totalPrice = cartItems.sumBy { it.quantity * (products[it.productId]?.moneyProduct?.toInt() ?: 0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(mainColorBlue)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Số lượng sản phẩm
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(){
                Text(
                    text = "Số lượng:",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.DarkGray),
                )
                Text(
                    text = " $totalQuantity",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
                    modifier = Modifier.padding(start = 7 .dp)
                )
            }

            Row {
                Text(
                    text = "Tổng tiền:",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.DarkGray)
                )
                Text(
                    text = " $totalPrice VND",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
                    modifier = Modifier.padding(start = 5 .dp)
                )
            }

        }


        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /* Đặt hàng */ },
                colors = ButtonDefaults.buttonColors(mainColorOrrange)
            ) {
                Text(
                    text = "Đặt hàng",
                    style = MaterialTheme.typography.titleSmall.copy(color = Color.White)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarNull( navController: NavHostController) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /* Đặt hàng */ },
                colors = ButtonDefaults.buttonColors(mainColorOrrange)
            ) {
                Text(
                    text = "Chọn món ngay",
                    style = MaterialTheme.typography.titleSmall.copy(color = Color.White)
                )
            }
        }
}
@Preview(showBackground = true)
@Composable
fun PreviewOder() {
    val navController: NavHostController = rememberNavController()
    CartScreen(navController)
}

