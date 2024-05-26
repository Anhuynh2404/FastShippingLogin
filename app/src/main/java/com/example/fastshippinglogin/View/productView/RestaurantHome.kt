package com.example.fastshippinglogin.View.productView

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fastshippinglogin.Model.Restaurant
import com.example.fastshippinglogin.R
import com.example.fastshippinglogin.ui.theme.Poppins
import com.example.fastshippinglogin.ui.theme.mainColorOrrange
import com.example.fastshippinglogin.ui.theme.mainColorWhite
import com.example.fastshippinglogin.viewmodel.restaurant.RestaurantViewModel

@Composable
fun RestaurantScreen(navController: NavHostController) {
    val tabs = listOf("Tất cả", "Món Việt", "Món Hàn", "BBQ")
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Nhà hàng", fontFamily = Poppins, fontSize = 20.sp) },
                actions = {
                    IconButton(onClick = { /* Handle reload action */ }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Tải lại")
                    }
                    IconButton(onClick = { /* Handle search action */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Tìm kiếm")
                    }
                    IconButton(onClick = { navController.navigate("Cart") }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Giỏ hàng")
                    }
                },
                backgroundColor = mainColorWhite
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                TabRow(selectedTabIndex = selectedTab, backgroundColor = mainColorOrrange) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title, fontFamily = Poppins, color = mainColorWhite) },
                            selected = selectedTab == index,
                            onClick = { selectedTab = index }
                        )
                    }
                }
                when (selectedTab) {
                    0 -> RestaurantList(navController)
                }
            }
        }
    )
}

@Composable
fun RestaurantList(navController: NavHostController, restaurantViewModel: RestaurantViewModel = viewModel()) {
    val restaurants by restaurantViewModel.restaurants.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    FilterButtonRow()
                },
                backgroundColor = mainColorWhite
            )
        }
    ) { padding ->
        if (restaurants.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(padding)
            ) {
                items(restaurants) { restaurant ->
                    RestaurantItem(navController, restaurant)
                }
            }
        } else {
            Text(text = "Loading...", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun FilterButtonRow() {
    Row(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(start = 5.dp)) {
        FilterButton("Ưu đãi")
        FilterButton("Khuyến mãi")
        FilterButton("Mới nhất")
    }
}

@Composable
fun FilterButton(text: String) {
    Button(
        onClick = { /* TODO: Handle filter */ },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(Color.LightGray),
        modifier = Modifier.padding(start = 5.dp)
    ) {
        Row {
            Image(painter = painterResource(id = R.drawable.home_icon), contentDescription = "", modifier = Modifier.size(19.dp))
            Text(text = text, color = Color.DarkGray, fontSize = 15.sp)
        }
    }
}
@Composable
fun RestaurantItem(navController: NavHostController, restaurant: Restaurant) {
    Log.d("RestaurantItem", "Displaying restaurant: $restaurant")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("restaurantDetail/${restaurant.id}") },
        shape = RectangleShape
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.nhahang1),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RectangleShape)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Row {
                    Text(restaurant.nameRestaurant.ifEmpty { "Lỗi" }, fontSize = 15.sp, maxLines = 1, fontFamily = Poppins)
                    Card(backgroundColor = mainColorOrrange, modifier = Modifier.padding(start = 5.dp)) {
                        Text("Ok", fontStyle = FontStyle.Italic, modifier = Modifier.padding(2.dp), color = mainColorWhite)
                    }
                }
                Text(restaurant.addressRestaurant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(restaurant.descriptionRestaurant.orEmpty(), maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "View Details",
                tint = mainColorOrrange,
                modifier = Modifier.padding(top = 15.dp)
            )
        }
    }
}


