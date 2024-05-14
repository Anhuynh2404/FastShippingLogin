package com.example.fastshippinglogin.Model
data class Restaurant(
    val id: String = "",
    val nameRestaurant: String = "",
    val addressRestaurant: String = "",
    val descriptionRestaurant: String? = null,
    val imageUrlRestaurant: String? = null,
    val stateRestaurant: Boolean = true
) {
    constructor():this("")
//    companion object {
//        fun fromMap(map: Map<String, Any>, id: String): Restaurant {
//            return Restaurant(
//                id = id,
//                name = map["name"] as? String ?: "",
//                address = map["address"] as? String ?: "",
//                description = map["description"] as? String,
//                imageUrl = map["imageUrl"] as? String,
//                state = map["state"] as? Boolean ?: true
//            )
//        }
//    }
}


//fun getRestaurantsByCategory(category: String): List<Restaurant> {
//    // Sample data
//    return listOf(
//        Restaurant("1", "Restaurant 1", "Address 1", "Description 1", ),
//        Restaurant("2", "Restaurant 2", "Address 2", "Description 2", ),
//        Restaurant("3", "Restaurant 3", "Address 3", "Description 3", )
//    )
//}
