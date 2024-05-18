package com.example.fastshippinglogin.View.cartview

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.fastshippinglogin.Model.Product
import com.example.fastshippinglogin.ui.theme.mainColorOrrange
import com.example.fastshippinglogin.ui.theme.mainColorWhite
import com.example.fastshippinglogin.ui.theme.mainColorBlue




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenTest() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Giỏ hàng") },
                navigationIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    mainColorBlue, // Màu nền của TopAppBar
                    contentColorFor(mainColorWhite) // Màu của nội dung (text, icon) trong TopAppBar
                )
            )
        },
        bottomBar = {
            BottomBarTest()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(color = Color.Gray)
                //.verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
            ){
                Text(
                    text = "Madam Nguyễn",
                    modifier = Modifier.padding(20 .dp)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                shape = RectangleShape
            ) {
                Cart()
            }
            Card(
                modifier = Modifier.padding(top = 5 .dp),
                shape = RectangleShape
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
                            fontSize = 18 .sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "155,000 đ",
                            modifier = Modifier.padding(10 .dp),
                            fontSize = 18 .sp,
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
                            fontSize = 18 .sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "1000 đ",
                            modifier = Modifier.padding(end = 10 .dp, bottom = 10 .dp),
                            fontSize = 18 .sp,
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
                            fontSize = 18 .sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "155,000 đ",
                            modifier = Modifier.padding(end = 10 .dp, bottom = 10 .dp),
                            fontSize = 18 .sp,
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
                            fontSize = 18 .sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "155,000 đ",
                            modifier = Modifier.padding(10 .dp),
                            fontSize = 18 .sp,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Cart(){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        CartItemTest()
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        CartItemTest()
    }
}

@Composable
fun CartItemTest(
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.img),
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
            Text(text = "Piza hải sản")
            Text(text = "Giá: 1000 VND")
            QuantityButtonTest()
        }

        IconButton(onClick = {  }) {
            Icon(Icons.Default.Delete, contentDescription = "Xóa")
        }
    }
}

@Composable
fun QuantityButtonTest() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
        }) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Giảm số lượng")
        }
        Text(text = "50", fontSize = 16.sp, modifier = Modifier.padding(horizontal = 5.dp))
        IconButton(onClick = {
        }) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Tăng số lượng")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarTest() {

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
            Row {
                Text(
                    text = "Số lượng:",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.DarkGray),
                )
                Text(
                    text = "1000",
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
                    text = "1000 VNĐ",
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
@Preview(showBackground = true)
@Composable
fun PreviewCartTest() {

    CartScreenTest()
}

