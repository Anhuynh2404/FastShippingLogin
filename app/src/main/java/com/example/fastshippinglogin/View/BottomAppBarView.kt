package com.example.fastshippinglogin.View

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fastshippinglogin.Model.BottomMenuItem
import com.example.fastshippinglogin.R

@Composable
private fun prepareBottomMenu(): List<BottomMenuItem>{
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()

    bottomMenuItemList.add(
        BottomMenuItem(
            label = "Home",
            icon = painterResource(id = R.drawable.home_icon)
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "FeedBack",
            icon = painterResource(id = R.drawable.feed_back_icon)
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "Order",
            icon = painterResource(id = R.drawable.order_icon)
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "Profile",
            icon = painterResource(id = R.drawable.profile_icon)
        )
    )

    return bottomMenuItemList
}

@Composable
fun MyBottomAppBar(navController: NavController){
    val bottomMenuItemsList = prepareBottomMenu()
    val contextForToast = LocalContext.current.applicationContext
    var selectedItem by remember {
        mutableStateOf("Home")
    }

    BottomAppBar(
        modifier = Modifier.background(Color.White)
    ) {
        bottomMenuItemsList.forEachIndexed { index, bottomMenuItem ->
            if(index == 2){
                BottomNavigationItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { /*TODO*/ },
                    enabled = false
                )
            }

            BottomNavigationItem(
                selected = (selectedItem == bottomMenuItem.label),
                onClick = {
                          selectedItem = bottomMenuItem.label
                        Toast.makeText(contextForToast,bottomMenuItem.label, Toast.LENGTH_LONG
                        ).show()
                    when (bottomMenuItem.label) {
                        "Home" -> navController.navigate("Home")
                        "FeedBack" -> navController.navigate("FeedBack")
                        "Order" -> navController.navigate("Order")
                        "Profile" -> navController.navigate("Profile")
                    }
                },
                icon = {
                    Icon(
                        painter = bottomMenuItem.icon,
                        contentDescription = bottomMenuItem.label,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                },
                alwaysShowLabel = true,
                enabled = true
            )
        }
    }
}

//@Preview
//@Composable
//fun MyUI(){
//    val scaffoldState = rememberScaffoldState()
//
//    Scaffold (
//        bottomBar = { MyBottomAppBar()},
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { /*TODO*/ },
//                contentColor = Color.White,
////                modifier = Modifier.background(Color.Blue)
//                ) {
//                Icon(painter = painterResource(id = R.drawable.google), contentDescription = "product",
//                    modifier = Modifier
//                        .height(30.dp)
//                        .width(30.dp))
//            }
//        },
//       floatingActionButtonPosition = FabPosition.Center,
//        modifier = Modifier.padding(bottom = 0 .dp),
//       // contentWindowInsets = WindowInsets(right = 50 .dp),
//
//
//    ){
//        Column (
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues = it),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ){
//
//        }
//    }
//}
