package com.example.fastshippinglogin.View.orderView.address

import android.app.Activity
import android.content.Intent
import androidx.navigation.NavGraphBuilder
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fastshippinglogin.ui.theme.mainColorOrrange
import com.example.fastshippinglogin.ui.theme.mainColorWhite
import com.example.fastshippinglogin.viewmodel.address.AddressViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(navController: NavHostController/*, viewModel: AddressViewModel = viewModel()*/) {
    val context = LocalContext.current
    var address by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { androidx.compose.material3.Text(text = "Đặt hàng") },
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

        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ){
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
                        text = "Nhập địa chỉ của bạn",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Địa chỉ") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = {
                        },
                        shape = RectangleShape,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Chọn vị trí trên bản đồ")
                    }
                }

            }
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 5.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(mainColorWhite),
                shape = RectangleShape,
                colors = CardDefaults.cardColors(mainColorWhite)
            ) {
                Button(
                    onClick = {
                        navController.navigate("Order/${address}")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape
                ) {
                    Text("Xác nhận")
                }
            }
        }

    }
}
