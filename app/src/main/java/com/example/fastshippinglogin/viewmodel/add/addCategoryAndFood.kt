package com.example.fastshippinglogin.viewmodel.add

import android.net.Uri
import com.example.fastshippinglogin.Model.Category
import com.example.fastshippinglogin.Model.Food
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Thêm một category mới vào Firebase
suspend fun addCategoryToFirebase(categoryName: String, categoryImageUri: Uri?) {
    val firestore = FirebaseFirestore.getInstance()
    val categoriesCollection = firestore.collection("categories")
    val category = hashMapOf(
        "name" to categoryName,
        "imageUri" to categoryImageUri.toString()
    )
    categoriesCollection.add(category)
}

// Thêm một food mới vào Firebase và liên kết với các danh mục phù hợp
suspend fun addFoodToFirebase(foodName: String, categoryIds: List<String>) {
    val firestore = FirebaseFirestore.getInstance()
    val foodsCollection = firestore.collection("foods")

    // Thêm thông tin về food vào bảng foods
    val food = hashMapOf(
        "name" to foodName
        // Các trường khác nếu cần thiết
    )
    val foodDocumentRef = foodsCollection.add(food).await()

    // Tạo các bản ghi trong bảng trung gian foodCategoryMap để liên kết food với các categories tương ứng
    val foodId = foodDocumentRef.id
    val foodCategoryMapCollection = firestore.collection("foodCategoryMap")
    categoryIds.forEach { categoryId ->
        val foodCategoryMap = hashMapOf(
            "foodId" to foodId,
            "categoryId" to categoryId
        )
        foodCategoryMapCollection.add(foodCategoryMap)
    }
}

// Truy vấn danh sách các category từ Firebase
//suspend fun loadCategoriesFromFirebase(): List<Category> {
//    val firestore = FirebaseFirestore.getInstance()
//    val categories = mutableListOf<Category>()
//
//    val snapshot = firestore.collection("categories").get().await()
//    for (document in snapshot.documents) {
//        val categoryId = document.get
//        val categoryName = document.getString("name")
//        if (categoryName != null) {
//            categories.add(Category(categoryId, categoryName))
//        }
//    }
//
//    return categories
//}

// Truy vấn danh sách các food từ Firebase và liên kết chúng với các categories tương ứng
suspend fun loadFoodsFromFirebase(): List<Food> {
    val firestore = FirebaseFirestore.getInstance()
    val foods = mutableListOf<Food>()

    val snapshot = firestore.collection("foods").get().await()
    for (document in snapshot.documents) {
        val foodId = document.id
        val foodName = document.getString("name")
        val foodImageUrl = document.getString("imgUrl")
        val foodPrice = document.getDouble("price")
        val foodDescription = document.getString("description")


        val foodCategoriesSnapshot = firestore.collection("foodCategoryMap")
            .whereEqualTo("foodId", foodId)
            .get().await()
        val categoryIds = mutableListOf<String>()
        for (categoryDocument in foodCategoriesSnapshot.documents) {
            val categoryId = categoryDocument.getString("categoryId")
            if (categoryId != null) {
                categoryIds.add(categoryId)
            }
        }

        if (foodName != null) {
            foods.add(Food(foodId, foodName,foodImageUrl,foodPrice,foodDescription))
        }
    }

    return foods
}
