package com.example.fastshippinglogin.View.productView.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.fastshippinglogin.Model.Product
import com.example.fastshippinglogin.viewmodel.restaurant.RestaurantViewModel

@Composable
fun MenuScreen(navController: NavHostController ,restaurantId: String, restaurantViewModel: RestaurantViewModel = viewModel()) {
    LaunchedEffect(restaurantId) {
        restaurantViewModel.fetchProductsByRestaurantId(restaurantId)
    }

    val products by restaurantViewModel.products.collectAsState()
    val categories by restaurantViewModel.categories.collectAsState()

    // Group products by category
    val groupedProducts = products.groupBy { it.id_Category }

    Column(modifier = Modifier.fillMaxSize()) {
        groupedProducts.forEach { (category, products) ->
                val categoryName = categories[category]?.nameCategory ?: "Unknown Category"
            Text(
                text = categoryName,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )
            products.forEach { product ->
                ProductItem(navController, product = product)
            }
        }
    }
}

@Composable
fun ProductItem(navController: NavHostController, product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("productDetail/${product.id}") }, // Navigate to product detail screen
        elevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(data = product.imgProduct),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(product.nameProduct, fontSize = 20.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(product.contentProduct.orEmpty(), maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("Giá: ${product.moneyProduct} VND", maxLines = 1, overflow = TextOverflow.Ellipsis)
            }

            IconButton(onClick = {
                navController.navigate("productDetail/${product.id}") // Navigate to product detail screen
            }) {
                Icon(imageVector = Icons.Default.Info, contentDescription = "View Details", tint = Color(0xFFFFA500))
            }
        }
    }
}

