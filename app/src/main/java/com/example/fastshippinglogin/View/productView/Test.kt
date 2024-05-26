package com.example.fastshippinglogin.View.productView

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fastshippinglogin.Model.Restaurant
import com.example.fastshippinglogin.View.productView.product.MenuScreen
import com.example.fastshippinglogin.ui.theme.mainColorWhite
import com.example.fastshippinglogin.viewmodel.restaurant.RestaurantViewModel
import kotlin.math.log
import com.example.fastshippinglogin.R
import com.example.fastshippinglogin.ui.theme.Poppins
import com.example.fastshippinglogin.ui.theme.mainColorOrrange
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun RestaurantDetailScreenT(

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Chi tiết nhà hàng") },
                navigationIcon = {
                    IconButton(onClick = {
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle favorite action */ }) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Yêu thích")
                    }
                    IconButton(onClick = { /* Handle share action */ }) {
                        Icon(Icons.Filled.Share, contentDescription = "Chia sẻ")
                    }
                },
                backgroundColor = mainColorWhite
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ImageSliderT()
            // Thông tin nhà hàng
            RestaurantInfoT()
            Spacer(modifier = Modifier.height(8.dp))
            // Các nút chức năng
            FunctionButtonsT()
            Spacer(modifier = Modifier.height(8.dp))
            // TabRow
            RestaurantTabRowT()
        }
    }
}

@Composable
fun ImageSliderT() {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            coroutineScope.launch {
                val currentIndex = listState.firstVisibleItemIndex
                val nextIndex = if (currentIndex < 4) currentIndex + 1 else 0
                listState.animateScrollToItem(nextIndex)
            }
            delay(3000) // Delay time in milliseconds
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(5) {
            Image(
                painter = painterResource(id = R.drawable.nhahang1),
                contentDescription = "Ảnh nhà hàng",
                modifier = Modifier
                    .size(200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun RestaurantInfoT() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "nameRestaurant",
            fontSize = 24.sp,
            fontFamily = Poppins
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.Yellow)
            Text(text = "4.5", modifier = Modifier.padding(start = 4.dp))
            Text(text = "(50 lượt đánh giá)", modifier = Modifier.padding(start = 4.dp), color = Color.Gray)
        }
        Text(text = "Địa chỉ: {restaurant.addressRestaurant}", modifier = Modifier.padding(vertical = 4.dp), fontSize = 16 .sp)
        Text(text = "Giờ giao hàng:  AM -  PM", modifier = Modifier.padding(vertical = 4.dp), fontSize = 16 .sp)
        Text(text = "Phương thức thanh toán: ", modifier = Modifier.padding(vertical = 4.dp), fontSize = 16 .sp)
        Text(text = "Mô tả: {restaurant.descriptionRestaurant.orEmpty()} ", modifier = Modifier.padding(vertical = 4.dp), fontSize = 16 .sp)
    }
}

@Composable
fun FunctionButtonsT() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButtonT(icon = Icons.Default.Email, text = "Phản hồi", onClick = { /* Tư vấn */ })
        ActionButtonT(icon = Icons.Default.Call, text = "Liên hệ", onClick = { /* Gọi điện */ })
        ActionButtonT(icon = Icons.Default.Create, text = "Đánh giá", onClick = { /* Gọi Grab */ })
        ActionButtonT(icon = Icons.Default.Place, text = "Vị trí", onClick = { /* In hình */ })

    }
}

@Composable
fun ActionButtonT(icon: ImageVector, text: String, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = text, tint = Color(0xFFFFA500), modifier = Modifier.size(40 .dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = text, fontSize = 12.sp, color = Color.Black)
        }
    }
}

@Composable
fun RestaurantTabRowT(
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Menu", "Thông tin", "Hình ảnh", "Review")

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = mainColorOrrange
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = { Text(tab, color = mainColorWhite) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTabIndex) {

            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RestaurantDetailScreenT()
}
