package com.example.fastshippinglogin.View.productView

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fastshippinglogin.Model.Restaurant
import com.example.fastshippinglogin.View.productView.product.MenuScreen
import com.example.fastshippinglogin.viewmodel.restaurant.RestaurantViewModel
import kotlin.math.log


@Composable
fun RestaurantDetailScreen(
    navController: NavHostController,
    resID : String,
    restaurantViewModel: RestaurantViewModel = viewModel()
) {
    LaunchedEffect(resID) {
        restaurantViewModel.getRestaurantById(resID)
    }

    val restaurant by restaurantViewModel.restaurant.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Chi tiết nhà hàng") },
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
                    IconButton(onClick = { /* Handle share action */ }) {
                        Icon(Icons.Filled.Share, contentDescription = "Chia sẻ")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Slide hình ảnh của cửa hàng
            ImageSlider()

            // Thông tin nhà hàng
            restaurant?.let {
                RestaurantInfo(it)
            } ?: run {
                Text(text = "Loading...", modifier = Modifier.padding(16.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Các nút chức năng
            FunctionButtons()
            Spacer(modifier = Modifier.height(8.dp))
            // TabRow
            RestaurantTabRow(navController,resID)
        }
    }
}

@Composable
fun ImageSlider() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(5) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = "Ảnh nhà hàng",
                modifier = Modifier
                    .size(200.dp)
                    .padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun RestaurantInfo(restaurant: Restaurant?) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = restaurant!!.nameRestaurant,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.Yellow)
            Text(text = "4.5", modifier = Modifier.padding(start = 4.dp))
        }
        Text(text = "Địa chỉ: ${restaurant.addressRestaurant}", modifier = Modifier.padding(vertical = 4.dp))
        Text(text = "Giờ giao hàng:  AM -  PM", modifier = Modifier.padding(vertical = 4.dp))
        Text(text = "Phương thức thanh toán: ", modifier = Modifier.padding(vertical = 4.dp))
        Text(text = "Mô tả: ${restaurant.descriptionRestaurant.orEmpty()} ", modifier = Modifier.padding(vertical = 4.dp))
    }
}

@Composable
fun FunctionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButton(icon = Icons.Default.Email, text = "Phản hồi", onClick = { /* Tư vấn */ })
        ActionButton(icon = Icons.Default.Call, text = "Liên hệ", onClick = { /* Gọi điện */ })
        ActionButton(icon = Icons.Default.Create, text = "Đánh giá", onClick = { /* Gọi Grab */ })
        ActionButton(icon = Icons.Default.Place, text = "Vị trí", onClick = { /* In hình */ })

    }
}

@Composable
fun ActionButton(icon: ImageVector, text: String, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = text, tint = Color(0xFFFFA500))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = text, fontSize = 12.sp, color = Color.Black)
        }
    }
}

@Composable
fun RestaurantTabRow(
    navController: NavHostController,
    id :String
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Menu", "Thông tin", "Hình ảnh", "Review")

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = MaterialTheme.colors.primaryVariant
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = { Text(tab) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTabIndex) {
                0 -> MenuScreen(navController , restaurantId = id)
                1 -> InfoContent()
                2 -> ImagesContent()
                3 -> ReviewContent()
            }
        }
    }
}



@Composable
fun InfoContent() {
    Text(text = "Thông tin content")
}

@Composable
fun ImagesContent() {
    Text(text = "Hình ảnh content")
}

@Composable
fun ReviewContent() {
    Text(text = "Review content")
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    RestaurantDetailScreen()
//}
