package com.example.fastshippinglogin.View.orderView

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.fastshippinglogin.Model.CartItem
import com.example.fastshippinglogin.Model.Order
import com.example.fastshippinglogin.Model.OrderItem
import com.example.fastshippinglogin.Model.Product
import com.example.fastshippinglogin.R
import com.example.fastshippinglogin.data.PaymentMethod
import com.example.fastshippinglogin.ui.theme.Poppins
import com.example.fastshippinglogin.ui.theme.SecondaryColor
import com.example.fastshippinglogin.ui.theme.mainColorOrrange
import com.example.fastshippinglogin.ui.theme.mainColorWhite
import com.example.fastshippinglogin.viewmodel.cart.CartViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(navController: NavHostController,address: String = "",cartViewModel: CartViewModel = viewModel()) {
    var orderPlaced by remember { mutableStateOf(false) }
    var paymentMethod by remember { mutableStateOf(PaymentMethod.CASH) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Đặt hàng") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle reload action */ }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Tải lại")
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(navController,cartViewModel, address)
        }
    ) { innerPadding ->
        val cartItems by cartViewModel.cartItems.observeAsState(emptyList())
        val products by cartViewModel.products.observeAsState(emptyMap())
        val totalOrderAmount = cartItems.sumOf { it.quantity * (products[it.productId]?.moneyProduct?.toInt() ?: 0) }
        val shippingFee = cartViewModel.getShippingFee()
        val discount = cartViewModel.getDiscount()
        val totalPaymentAmount = totalOrderAmount + shippingFee - discount

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.LightGray)
        ) {
            ShippingInfoSection(
                navController,
                address,
                onMethodSelected = {method->
                    cartViewModel.setPhone(method)
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            PaymentMethodSection { method ->
                paymentMethod = method
                cartViewModel.setPaymentMethod(method)
            }
            Spacer(modifier = Modifier.height(5.dp))
            PaymentDetailSection(
                cartItems = cartItems,
                products = products,
                totalOrderAmount = totalOrderAmount,
                shippingFee = shippingFee,
                discount = discount,
                totalPaymentAmount = totalPaymentAmount
            )
        }
    }
}

@Composable
fun ShippingInfoSection(navController: NavHostController, address: String,onMethodSelected: (String) -> Unit) {
    //val phone = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: ""
    var phoneNumber by remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(mainColorWhite),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(mainColorWhite)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp)
        ) {
            Text(
                text = "Thông tin giao hàng",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = mainColorOrrange

            )
            Spacer(modifier = Modifier.height(8.dp))

            AddressCard(navController,address)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                    onMethodSelected(it) },
                label = { Text(text = "Số điện thoại") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = "",
                onValueChange = { /* Handle special request change */ },
                label = { Text(text = "Yêu cầu đặc biệt") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressCard(navController: NavHostController, address: String){
    Column {
        Card(
            onClick = {
                navController.navigate("address")
            },
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.padding(vertical = 18.dp, horizontal = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                if (address.isEmpty()){
                    androidx.compose.material.Text(
                        text = "Vui lòng chọn địa chỉ giao hàng",
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                }else{
                    androidx.compose.material.Text(
                        text = "$address",
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                }
                androidx.compose.material.Icon(
                    painter = painterResource(id = R.drawable.ic_right_arrow),
                    contentDescription = "",
                    modifier = Modifier.size(16.dp)
                )
            }

        }
    }
}
@Composable
fun PaymentMethodSection(onMethodSelected: (PaymentMethod) -> Unit) {
    var selectedMethod by remember { mutableStateOf(PaymentMethod.CASH) }

    val paymentMethods = listOf(PaymentMethod.CASH, PaymentMethod.BANK_TRANSFER)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(mainColorWhite),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(mainColorWhite)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Phương thức thanh toán",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = mainColorOrrange
            )
            Spacer(modifier = Modifier.height(8.dp))
            paymentMethods.forEach { method ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = method == selectedMethod,
                        onClick = {
                            selectedMethod = method
                            onMethodSelected(method)
                        }
                    )
                    Text(text = when (method) {
                        PaymentMethod.CASH -> "Tiền mặt"
                        PaymentMethod.BANK_TRANSFER -> "Chuyển khoản"
                    })
                }
            }
        }
    }
}


@Composable
fun PaymentDetailSection(
    cartItems: List<CartItem>,
    products: Map<String, Product>,
    totalOrderAmount: Int,
    shippingFee: Int,
    discount: Int,
    totalPaymentAmount: Int
) {
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(mainColorWhite)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Chi tiết đơn hàng",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = mainColorOrrange
            )
            cartItems.forEach { item ->
                val product = products[item.productId]
                product?.let {
                    OrderItem(
                        nameFood = it.nameProduct,
                        quantity = item.quantity,
                        price = "${item.quantity * it.moneyProduct.toInt()} đ"
                    )
                }
            }
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 3.dp)
            )
            PaymentSummaryRow("Tổng đơn hàng", "$totalOrderAmount đ")
            PaymentSummaryRow("Phí vận chuyển", "$shippingFee đ")
            PaymentSummaryRow("Giảm giá phí vận chuyển", "$discount đ")
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            PaymentSummaryRow(
                "Tổng tiền thanh toán",
                "$totalPaymentAmount đ",
                isTotal = true
            )
        }
    }
}

@Composable
fun OrderItem(nameFood: String, quantity: Int, price: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$nameFood X$quantity",
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            fontSize = 15.sp,
            color = Color.Black
        )
        Text(
            text = price,
            modifier = Modifier.padding(10.dp),
            fontSize = 14.sp
        )
    }
}

@Composable
fun PaymentSummaryRow(label: String, amount: String, isTotal: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            fontSize = 14.sp,
            color = if (isTotal) mainColorOrrange else Color.Gray,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = amount,
            modifier = Modifier.padding(10.dp),
            fontSize = 14.sp,
            color = if (isTotal) mainColorOrrange else Color.Gray,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomBar(navController: NavHostController ,cartViewModel: CartViewModel, address: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(mainColorWhite)
            .padding(16.dp),
    ) {
        Button(
            onClick = {
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                val cartItems = cartViewModel.cartItems.value ?: emptyList()
                val products = cartViewModel.products.value ?: emptyMap()
                val totalOrderAmount = cartItems.sumOf { it.quantity * (products[it.productId]?.moneyProduct?.toInt() ?: 0) }
                val shippingFee = cartViewModel.getShippingFee()
                val discount = cartViewModel.getDiscount()
                val totalPaymentAmount = totalOrderAmount + shippingFee - discount
                val statusOrder = "Chờ xác nhận"
                val paymentMethod = cartViewModel.getPaymentMethod()
                val phone = cartViewModel.getPhone()

                val order = Order(
                    userId = userId,
                    items = cartItems.map {
                          OrderItem(
                              productId = it.productId,
                              quantity = it.quantity,
                              price = products[it.productId]?.moneyProduct?.toInt() ?: 0
                          )
                    },
                    totalOrderAmount = totalOrderAmount,
                    shippingFee = shippingFee,
                    discount = discount,
                    totalPaymentAmount = totalPaymentAmount,
                    statusOrder = statusOrder,
                    address = address,
                    phone = phone,
                    paymentMethod = paymentMethod
                )
                cartViewModel.placeOrder(order, onSuccess = {
                    if (paymentMethod == PaymentMethod.CASH.toString()) {
                      navController.navigate("OrderSuccess")
                    } else  {
                       // navController.navigate("OrderSuccess")
                        Toast.makeText(context, "Đang bảo trì, vui lòng chọn phương thức đặt hàng khác", Toast.LENGTH_SHORT).show()
                    }
                }, onFailure = {
                    Toast.makeText(context, "Đặt hàng thất bại: ${it.message}", Toast.LENGTH_SHORT).show()
                })
            },
            colors = ButtonDefaults.buttonColors(mainColorOrrange),
            shape = RectangleShape,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Đặt hàng",
                style = MaterialTheme.typography.titleSmall.copy(color = Color.White)
            )
        }
    }
}



