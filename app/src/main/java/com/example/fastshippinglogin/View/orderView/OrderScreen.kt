//package com.example.fastshippinglogin.View.orderView
//import android.annotation.SuppressLint
//import androidx.annotation.StringRes
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.DropdownMenuItem
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Refresh
//import androidx.compose.material3.BottomAppBar
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Tab
//import androidx.compose.material3.TabRow
//import androidx.compose.material3.TabRowDefaults
//import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.dimensionResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun OrderScreen(navController: NavHostController) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = "Đặt hàng") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Quay lại")
//                    }
//                },
//                actions = {
//                    IconButton(onClick = { /* Reload action */ }) {
//                        Icon(Icons.Filled.Refresh, contentDescription = "Reload")
//                    }
//                }
//            )
//        },
//        bottomBar = {
//            BottomAppBar(
//             //   backgroundColor = MaterialTheme.colorScheme.primaryContainer,
//                content = {
//                    Button(
//                        onClick = { /* Handle order */ },
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
//                    ) {
//                        Text(text = "Giao hàng ngay", color = Color.White)
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .padding(innerPadding)
//                .padding(16.dp)
//                .verticalScroll(rememberScrollState())
//        ) {
//            ShippingInfoSection()
//            PaymentMethodSection()
//        }
//    }
//}
//
//@Composable
//fun ShippingInfoSection() {
//    Column {
//        Text(text = "Thông tin giao hàng", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 16.dp))
//
//        // Địa chỉ đặt hàng và chọn vị trí trên GG Map
//        TextField(
//            value = "",
//            onValueChange = {},
//            label = { Text(text = "Địa chỉ đặt hàng") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = { /* Open Google Map to choose location */ }, modifier = Modifier.fillMaxWidth()) {
//            Text(text = "Chọn vị trí trên Google Map")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Số điện thoại
//        Row {
//            val phonePrefixes = listOf("+84", "+1", "+44")
//            var selectedPrefix by remember { mutableStateOf(phonePrefixes.first()) }
//            var phoneNumber by remember { mutableStateOf("") }
//
//            DropdownMenu(
//                expanded = false,
//                onDismissRequest = { /* TODO */ },
//                content = {
//                    phonePrefixes.forEach { prefix ->
//                        DropdownMenuItem(onClick = { selectedPrefix = prefix }) {
//                            Text(text = prefix)
//                        }
//                    }
//                }
//            )
//
//            TextField(
//                value = phoneNumber,
//                onValueChange = { phoneNumber = it },
//                label = { Text(text = "Số điện thoại") },
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Yêu cầu bổ sung
//        TextField(
//            value = "",
//            onValueChange = {},
//            label = { Text(text = "Yêu cầu bổ sung") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Thông báo cho người dùng
//        Text(
//            text = "Vui lòng kiểm tra thông tin giao hàng và chọn phương thức thanh toán phù hợp.",
//            color = Color.Red,
//            style = MaterialTheme.typography.bodyMedium,
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//}
//
//@Composable
//fun PaymentMethodSection() {
//    val paymentMethods = listOf("Tiền mặt", "Chuyển khoản")
//    var selectedMethod by remember { mutableStateOf(paymentMethods.first()) }
//
//    Column {
//        Text(text = "Phương thức thanh toán", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 16.dp))
//
//        TabRow(
//            selectedTabIndex = paymentMethods.indexOf(selectedMethod)
//        ) {
//            paymentMethods.forEachIndexed { index, method ->
//                Tab(
//                    selected = selectedMethod == method,
//                    onClick = { selectedMethod = method },
//                    text = { Text(text = method) }
//                )
//            }
//        }
//
//        when (selectedMethod) {
//            "Tiền mặt" -> CashPaymentSection()
//            "Chuyển khoản" -> BankTransferSection()
//        }
//    }
//}
//
//@Composable
//fun CashPaymentSection() {
//    Column {
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Giảm giá, Điểm, Voucher
//        TextField(
//            value = "",
//            onValueChange = {},
//            label = { Text(text = "Giảm giá") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        TextField(
//            value = "",
//            onValueChange = {},
//            label = { Text(text = "Điểm") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        TextField(
//            value = "",
//            onValueChange = {},
//            label = { Text(text = "Voucher") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Chi tiết đơn hàng
//        Text(text = "Chi tiết đơn hàng", style = MaterialTheme.typography.titleMedium)
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(text = "Giá sản phẩm: 1,000,000 VND")
//        Text(text = "Phí ship: 30,000 VND")
//        Text(text = "Thời gian giao hàng dự kiến: 30 phút")
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Tổng tiền thanh toán
//        Text(
//            text = "Tổng tiền thanh toán: 1,030,000 VND",
//            style = MaterialTheme.typography.titleMedium.copy(color = Color.Red)
//        )
//    }
//}
//
//@Composable
//fun BankTransferSection() {
//    Column {
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Liên kết tài khoản ngân hàng
//        Button(onClick = { /* Link bank account */ }, modifier = Modifier.fillMaxWidth()) {
//            Text(text = "Liên kết tài khoản ngân hàng")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Chi tiết đơn hàng
//        Text(text = "Chi tiết đơn hàng", style = MaterialTheme.typography.titleMedium)
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(text = "Giá sản phẩm: 1,000,000 VND")
//        Text(text = "Phí ship: 30,000 VND")
//        Text(text = "Thời gian giao hàng dự kiến: 30 phút")
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Tổng tiền thanh toán
//        Text(
//            text = "Tổng tiền thanh toán: 1,030,000 VND",
//            style = MaterialTheme.typography.titleMedium.copy(color = Color.Red)
//        )
//    }
//}
