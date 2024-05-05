package com.example.fastshippinglogin

import androidx.annotation.StringRes
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fastshippinglogin.View.MainContent
import com.example.fastshippinglogin.View.SettingsScreen
import com.google.firebase.auth.FirebaseUser
import com.example.fastshippinglogin.View.MyBottomAppBar

enum class AppScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Home(title = R.string.home_screen),
    Cart(title = R.string.cart_screen),
    Feedback(title = R.string.feedback_screen),
    Order(title = R.string.order_screen),
    Profile(title = R.string.profile_screen),
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderFoodApp(
//    viewModel: OrderViewModel = viewModel(),
    user: FirebaseUser,
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Start.name
    )


    Scaffold (
        bottomBar = { MyBottomAppBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                contentColor = Color.White,
                modifier = Modifier
                    .absoluteOffset(y = (65).dp)
                ) {
                Icon(painter = painterResource(id = R.drawable.order_icon), contentDescription = "product",
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp))
            }
        },
       floatingActionButtonPosition = FabPosition.Center,
        modifier = Modifier.padding(bottom = 0 .dp),
       // contentWindowInsets = WindowInsets(right = 50 .dp),
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Home",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            composable(route = "Home") { // Chỉ định route cho ScreenA
                MainContent(
                    user = user!!
                )
            }
            composable(route = "FeedBack") { // Chỉ định route cho ScreenB
               // ScreenB()
            }
            composable(route = "Order") { // Chỉ định route cho ScreenA
               // ScreenC()
            }
            composable(route = "Profile") { // Chỉ định route cho ScreenB
                SettingsScreen()
            }
        }
    }
}