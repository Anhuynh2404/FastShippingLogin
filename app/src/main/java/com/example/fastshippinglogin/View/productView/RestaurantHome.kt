package com.example.fastshippinglogin.View.productView

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import coil.compose.rememberImagePainter
import com.example.fastshippinglogin.Model.Restaurant
import com.example.fastshippinglogin.viewmodel.restaurant.RestaurantViewModel

@Composable
fun RestaurantScreen(navController: NavHostController) {
    val tabs = listOf("Tất cả", "Món Việt", "Món Hàn","BBQ")
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Restaurants") },
                backgroundColor = MaterialTheme.colors.primaryVariant
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                TabRow(
                    selectedTabIndex = selectedTab,
                    backgroundColor = MaterialTheme.colors.primaryVariant
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
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
    ) { padding ->
        if (restaurants.isNotEmpty()) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
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
fun RestaurantItem(navController: NavHostController,restaurant: Restaurant ) {
    Log.d("RestaurantItem", "Displaying restaurant: $restaurant")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("restaurantDetail/${restaurant.id}") },
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(data = restaurant.imageUrlRestaurant),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(restaurant.nameRestaurant.ifEmpty { "Lỗi" }, fontSize = 20.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(restaurant.addressRestaurant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(restaurant.descriptionRestaurant.orEmpty(), maxLines = 1, overflow = TextOverflow.Ellipsis)
//                Row {
//                    Text("${restaurant.rating} stars", color = Color.Gray)
//                }
            }
        }
    }
}

//@Composable
//fun RestaurantDetailScreen(navController: NavHostController, restaurantId: String) {
//    val restaurant = getRestaurantById(restaurantId)
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(restaurant.nameRestaurant) },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                },
//                backgroundColor = MaterialTheme.colors.primary
//            )
//        },
//        content = { paddingValues ->
//            Column(
//                modifier = Modifier
//                    .padding(paddingValues)
//                    .padding(16.dp)
//            ) {
//                Image(
//                    painter = rememberImagePainter(data = restaurant.imageUrlRestaurant),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Text(restaurant.nameRestaurant, fontSize = 24.sp)
//                Text(restaurant.addressRestaurant, fontSize = 16.sp, color = Color.Gray)
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(restaurant.descriptionRestaurant.orEmpty())
//                Spacer(modifier = Modifier.height(8.dp))
//                //Text("${restaurant.rating} stars")
//            }
//        }
//    )
//}



@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mainScreen") {

    }
}



