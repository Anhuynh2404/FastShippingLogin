package com.example.fastshippinglogin.View.orderView

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTestScreen(navController: NavHostController) {
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
                    IconButton(onClick = { /* Reload action */ }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Tải lại")
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            ShippingInfoSection()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Vui lòng điền đầy đủ thông tin để chúng tôi có thể giao hàng cho bạn một cách nhanh chóng.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            PaymentMethodSection()
            Spacer(modifier = Modifier.height(16.dp))
            VoucherSection()
            Spacer(modifier = Modifier.height(16.dp))
            PaymentDetailSection()
        }
    }
}

@Composable
fun ShippingInfoSection() {
    Column {
        Text(
            text = "Thông tin giao hàng",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = { /* Handle address change */ },
                label = { Text(text = "Địa chỉ đặt hàng") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { /* Open Google Maps */ }) {
                Text(text = "Chọn vị trí")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val phonePrefixes = listOf("+84", "+1", "+44")
            var selectedPrefix by remember { mutableStateOf(phonePrefixes[0]) }
            var phoneNumber by remember { mutableStateOf("") }

            DropdownMenu(
                expanded = false, // Handle expand state
                onDismissRequest = { /* Handle dismiss */ }
            ) {
                phonePrefixes.forEach { prefix ->
                    DropdownMenuItem(onClick = { selectedPrefix = prefix }) {
                        Text(text = prefix)
                    }
                }
            }
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text(text = "Số điện thoại") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = "",
            onValueChange = { /* Handle special request change */ },
            label = { Text(text = "Yêu cầu đặc biệt") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PaymentMethodSection() {
    Column {
        Text(
            text = "Phương thức thanh toán",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        val paymentMethods = listOf("Tiền mặt", "Chuyển khoản")
        var selectedMethod by remember { mutableStateOf(paymentMethods[0]) }

        paymentMethods.forEach { method ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = method == selectedMethod,
                    onClick = { selectedMethod = method }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = method)
            }
        }
    }
}

@Composable
fun VoucherSection() {
    Column {
        Text(
            text = "Chọn voucher giảm giá",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Implement voucher selection here
    }
}

@Composable
fun PaymentDetailSection() {
    Column {
        Text(
            text = "Chi tiết thanh toán",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Replace with actual values
        val totalItemsPrice = 100000
        val shippingFee = 20000
        val discount = 5000
        val voucherDiscount = 10000
        val totalPayment = totalItemsPrice + shippingFee - discount - voucherDiscount

        Text(text = "Tổng tiền hàng: ${totalItemsPrice} VND")
        Text(text = "Phí vận chuyển: ${shippingFee} VND")
        Text(text = "Giảm giá phí vận chuyển: ${discount} VND")
        Text(text = "Giảm giá voucher: ${voucherDiscount} VND")
        Text(
            text = "Tổng thanh toán: ${totalPayment} VND",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    BottomAppBar(
        content = {
            Button(
                onClick = { /* Handle order submission */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Giao hàng ngay")
            }
        }
    )
}
