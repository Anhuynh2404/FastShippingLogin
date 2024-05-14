package com.example.fastshippinglogin.View

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fastshippinglogin.Model.Food
import com.example.fastshippinglogin.viewmodel.addToCart
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodApp() {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    if (currentUser != null) {
        val userId = currentUser.uid

        var selectedFood by remember { mutableStateOf<Food?>(null) }

        Scaffold(
            topBar = { TopAppBar(title = { Text("Giỏ hàng") }) }
        ) {paddingValue ->
            Column(
                modifier = Modifier.padding(paddingValue)
            ) {
                if (selectedFood != null) {
                    Text("Selected Food: ${selectedFood?.name}")
                    Button(onClick = { addToCart(selectedFood!!, userId) }) {
                        Text("Add to Cart")
                    }
                } else {
                    Text("No food selected")
                }
                Spacer(modifier = Modifier.height(16.dp))
                FoodList(onFoodSelected = { food -> selectedFood = food })
            }
        }
    } else {
        Text("Please sign in to use the app")
        // Gọi hàm đăng nhập ở đây
    }
}

@Composable
fun FoodList(onFoodSelected: (Food) -> Unit) {
    val foods = listOf(
        Food("Food 1","hahahah","Món Hàn",24000.0,"Món Hàn"),
        Food("Food 1","hahahah","Món Hàn",24000.0,"Món Hàn"),
        Food("Food 1","hahahah","Món Hàn",24000.0,"Món Hàn"),
    )

    LazyColumn {
        items(foods) { food ->
            FoodItem(food = food, onFoodSelected = onFoodSelected)
        }
    }
}

@Composable
fun FoodItem(food: Food, onFoodSelected: (Food) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onFoodSelected(food) }
    ) {
        Text("Name: ${food.name}")
        Text("Price: ${food.price}")
    }
}
