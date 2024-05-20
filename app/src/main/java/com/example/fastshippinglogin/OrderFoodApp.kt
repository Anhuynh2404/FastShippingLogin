package com.example.fastshippinglogin

import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fastshippinglogin.Model.User
import com.example.fastshippinglogin.View.AuthScreen
import com.example.fastshippinglogin.View.MainContent
import com.example.fastshippinglogin.View.SettingsScreen
import com.google.firebase.auth.FirebaseUser
import com.example.fastshippinglogin.View.MyBottomAppBar
import com.example.fastshippinglogin.View.ProfileHome
import com.example.fastshippinglogin.View.cartview.CartScreen
import com.example.fastshippinglogin.View.components.OrderTab
import com.example.fastshippinglogin.View.orderView.OrderScreen
import com.example.fastshippinglogin.View.orderView.address.AddressScreen
import com.example.fastshippinglogin.View.orderView.ordersuccess.OrderSuccess
import com.example.fastshippinglogin.View.settingview.account.EditProfileScreen
import com.example.fastshippinglogin.View.productView.RestaurantDetailScreen
import com.example.fastshippinglogin.View.productView.RestaurantScreen
import com.example.fastshippinglogin.View.productView.product.ProductDetailScreen
import com.example.fastshippinglogin.ui.theme.mainColorOrrange
import com.example.fastshippinglogin.viewmodel.account.updateProfileInFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private val auth: FirebaseAuth by lazy { Firebase.auth }
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderFoodApp(
    user: FirebaseUser,
    navController: NavHostController = rememberNavController()
) {
    var users by remember { mutableStateOf(auth.currentUser) }
    val userProfile = remember { mutableStateOf<User?>(null) }
    val backStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(user.uid) {
        val firestore = FirebaseFirestore.getInstance()
        val userDocRef = firestore.collection("users").document(user.uid)

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val firstName = document.getString("firstName")
                    val lastName = document.getString("lastName")
                    val phone = document.getString("phone")
                    val address = document.getString("address")
                    userProfile.value = User(firstName, lastName, user.email ?: "", phone, address)
                } else {
                    // Handle the case where the document doesn't exist
                }
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }

    val shouldShowBottomBar = backStackEntry?.destination?.route != "Order" &&
            backStackEntry?.destination?.route != "Order/{address}" &&
            backStackEntry?.destination?.route != "Address" &&
            backStackEntry?.destination?.route != "OrderSuccess" &&
            backStackEntry?.destination?.route != "restaurantDetail/{restaurantId}" &&
            backStackEntry?.destination?.route != "productDetail/{productId}"

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                MyBottomAppBar(navController)
            }else{

            }
        },
        floatingActionButton = {
            if (shouldShowBottomBar) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("Cart")
                    },
                    contentColor = Color.White,
                    modifier = Modifier
                        .absoluteOffset(y = (80).dp),
                    containerColor = mainColorOrrange
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.order_icon),
                        contentDescription = "product",
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        modifier = Modifier.padding(bottom = 0.dp),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Home",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = "Home") {
                MainContent(
                    user = user
                )
            }
            composable(route = "FeedBack") {
                RestaurantScreen(navController)
            }
            composable(route = "Cart") {
                CartScreen(navController)
            }
            composable(route = "Order") {
                OrderScreen(navController)
            }
            composable("Order/{address}") { backStackEntry ->
                val address = backStackEntry.arguments?.getString("address") ?: ""
                OrderScreen(navController, address)
            }
            composable(route = "Address") {
                AddressScreen(navController)
            }
            composable(route = "OrderSuccess") {
                OrderSuccess(navController)
            }

            composable(route = "OrderState") {
                OrderTab(navController)
            }

            composable(route = "Profile") {
                userProfile.value?.let { users ->
                    SettingsScreen(navController, users)
                }
            }
            composable(route = "ViewProfile") {
                userProfile.value?.let { users ->
                    ProfileHome(navController, users)
                }
            }
            composable(route = "Logout") {
                AuthScreen(
                    onSignedIn = { signedInUser ->
                        users = signedInUser
                    }
                )
            }
            composable(route = "ModifyInfor") {
                userProfile.value?.let { users ->
                    EditProfileScreen(navController, user = users) { updatedUser ->
                        updateProfileInFirestore(user.uid, updatedUser)
                    }
                }
            }
            composable(route = "SaveAccount") {
                userProfile.value?.let { users ->
                    ProfileHome(navController, users)
                }
            }
            composable("restaurantDetail/{restaurantId}") { backStackEntry ->
                RestaurantDetailScreen(navController, backStackEntry.arguments?.getString("restaurantId") ?: "")
            }
            composable("productDetail/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                ProductDetailScreen(navController, productId)
            }
        }
    }
}

//@Composable
//fun MyAppCart(navController: NavHostController = rememberNavController()) {
//    NavHost(
//        navController = navController,
//        startDestination = "Cart",
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        composable(route = "Cart") {
//            CartScreen(navController)
//        }
//        composable(route = "Order") {
//            OrderScreen(navController)
//        }
//        composable("Order/{address}") { backStackEntry ->
//            val address = backStackEntry.arguments?.getString("address") ?: ""
//            OrderScreen(navController, address)
//        }
//        composable(route = "Address") {
//            AddressScreen(navController)
//        }
//        composable(route = "OrderSuccess") {
//            OrderSuccess(navController)
//        }
//    }
//}
