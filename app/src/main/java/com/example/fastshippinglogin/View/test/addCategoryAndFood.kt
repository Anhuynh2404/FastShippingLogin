//package com.example.fastshippinglogin.View.test
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.DropdownMenuItem
//import androidx.compose.material.Text
//import androidx.compose.material.TextField
//import androidx.compose.material3.Button
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.fastshippinglogin.Model.Category
//
//
//@Composable
//fun AddCategoryScreen(
//    onCategoryAdded: (String) -> Unit,
//    onBack: () -> Unit
//) {
//    var categoryName by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.Center
//    ) {
//        TextField(
//            value = categoryName,
//            onValueChange = { categoryName = it },
//            modifier = Modifier.fillMaxWidth(),
//            label = { Text("Category Name") }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = {
//                onCategoryAdded(categoryName)
//            },
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Text("Add Category")
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = onBack,
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Text("Back")
//        }
//    }
//}
//
//@Composable
//fun AddFoodScreen(
//    categories: List<Category>,
//    onFoodAdded: (String, List<String>) -> Unit,
//    onBack: () -> Unit
//) {
//    var foodName by remember { mutableStateOf("") }
//    var selectedCategories by remember { mutableStateOf(emptyList<String>()) }
//
//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.Center
//    ) {
//        TextField(
//            value = foodName,
//            onValueChange = { foodName = it },
//            modifier = Modifier.fillMaxWidth(),
//            label = { Text("Food Name") }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        DropdownMenu(
//            expanded = false,
//            onDismissRequest = { },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            categories.forEach { category ->
//                DropdownMenuItem(onClick = {
//                    if (!selectedCategories.contains(category.id)) {
//                        selectedCategories = selectedCategories + category.id
//                    }
//                }) {
//                    Text(category.name)
//                }
//            }
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = {
//                onFoodAdded(foodName, selectedCategories)
//            },
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Text("Add Food")
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = onBack,
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Text("Back")
//        }
//    }
//}
