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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fastshippinglogin.Model.Product
import com.example.fastshippinglogin.View.orderView.OrderScreen
import com.example.fastshippinglogin.View.orderView.address.AddressScreen
import com.example.fastshippinglogin.View.orderView.ordersuccess.OrderSuccess
import com.example.fastshippinglogin.ui.theme.Poppins
import com.example.fastshippinglogin.ui.theme.mainColorWhite
import com.example.fastshippinglogin.ui.theme.mainColorOrrange

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
//                navigationIcon = {
//                    IconButton(onClick = {
//                        navController.popBackStack()
//                    }) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Quay lại")
//                    }
//                }
            )
        },
        bottomBar = {
            if (cartItems.isEmpty()) {
                BottomBarNull(navController = navController)
            } else {
                BottomBar(cartItems = cartItems, products = products, navController = navController)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(color = mainColorWhite)

        ) {
            if (cartItems.isEmpty()) {
                EmptyCartMessage()
            } else {
                CartContent(cartItems, products, cartViewModel)
            }
        }
    }
}

@Composable
fun EmptyCartMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.cart_empty),
            contentDescription = "",
            Modifier.size(280.dp)
        )
        Text(
            text = "Giỏ hàng của bạn đang trống!",
            modifier = Modifier.padding(20.dp),
            fontSize = 20.sp,
            color = mainColorOrrange,
            textAlign = TextAlign.Center,
            fontFamily = Poppins
        )
    }
}

@Composable
fun CartContent(cartItems: List<CartItem>, products: Map<String, Product>, cartViewModel: CartViewModel) {
    val shippingFee = cartViewModel.getShippingFee()
    val discount = cartViewModel.getDiscount()
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(mainColorWhite)
    ) {
        Text(
            text = "Madam Nguyễn",
            modifier = Modifier.padding(vertical = 14 .dp, horizontal = 16 .dp),
            color = mainColorOrrange,
            fontSize = 16 .sp,
            fontWeight = FontWeight.Bold
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(mainColorWhite)
    ) {
        Column {
            cartItems.forEach { cartItem ->
                CartItem(cartItem, products[cartItem.productId], cartViewModel)
                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
    }

    Card(
        modifier = Modifier.padding(top = 5.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(mainColorWhite)
    ) {
        OrderSummary(cartItems = cartItems, products = products,shippingFee,discount)
    }
}

@Composable
fun OrderSummary(
    cartItems: List<CartItem>, products: Map<String, Product>,
    shippingFee: Int,
    discount: Int
) {
    val totalQuantity = cartItems.sumBy { it.quantity }
    val totalPrice = cartItems.sumBy { it.quantity * (products[it.productId]?.moneyProduct?.toInt() ?: 0) }
    val totalPaymentAmount = totalPrice + shippingFee - discount

    Column {
        SummaryRow(label = "Tổng đơn hàng", amount = "$totalPrice đ")
        SummaryRow(label = "Giảm giá", amount = "$discount đ")
        SummaryRow(label = "Phí ship dự kiến", amount = "$shippingFee đ")
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
        SummaryRow(label = "Tổng tiền thanh toán", amount = "$totalPaymentAmount đ", amountColor = Color.Red, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SummaryRow(label: String, amount: String, amountColor: Color = Color.Black, fontWeight: FontWeight = FontWeight.Normal) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 16.sp,
            color = Color.Gray,
            fontWeight = fontWeight
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = amount,
            modifier = Modifier.padding(end = 10.dp),
            fontSize = 16.sp,
            color = amountColor,
            fontWeight = fontWeight
        )
    }
}

@Composable
fun CartItem(cartItem: CartItem, product: Product?, viewModel: CartViewModel) {
    val imagePainter = rememberImagePainter(
        data = product?.imgProduct,
        builder = {
            error(R.drawable.ic_launcher_background)
            placeholder(R.drawable.logo)
        }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
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
            QuantityButton(cartItem, viewModel)
        }

        IconButton(onClick = { viewModel.removeCartItem(cartItem) }) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Xóa",
            )
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
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                Text(
                    text = "Số lượng:",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.DarkGray)
                )
                Text(
                    text = " $totalQuantity",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
                    modifier = Modifier.padding(start = 7.dp)
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
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate("OrderDetail") },
                colors = ButtonDefaults.buttonColors(mainColorOrrange),
                shape = RectangleShape
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
fun BottomBarNull(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(mainColorWhite)
            .padding(16.dp),
    ) {
        Button(
            onClick = {
                navController.navigate("FeedBack")
            },
            colors = ButtonDefaults.buttonColors(mainColorOrrange),
            shape = RectangleShape,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Chọn món ngay",
                style = MaterialTheme.typography.titleSmall.copy(color = Color.White)
            )
        }
    }
}

//@Composable
//fun MyAppCart(navController: NavHostController = rememberNavController()) {
//    //val navController = rememberNavController()
//    NavHost(
//        navController = navController,
//        startDestination = "Cart",
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        composable(route = "Cart") {
//            CartScreen(navController)
//        }
//
//        composable(route = "Order") {
//            OrderScreen(navController)
//        }
//        composable("Order/{address}") { backStackEntry ->
//            val address = backStackEntry.arguments?.getString("address") ?: ""
//            OrderScreen(navController, address)
//        }
//        composable(route = "Address") {
//            AddressScreen(navController)
//        }
//        composable(route = "OrderSuccess") {
//            OrderSuccess(navController)
//        }
//    }
//}

