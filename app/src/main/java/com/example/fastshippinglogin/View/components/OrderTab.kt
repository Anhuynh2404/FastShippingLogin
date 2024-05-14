package com.example.fastshippinglogin.View.components

import AddCategoryScreen
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fastshippinglogin.Model.User
import com.example.fastshippinglogin.View.CupcakeScreen
import com.example.fastshippinglogin.View.OrderAppBar
import com.example.fastshippinglogin.View.settingview.account.EditProfileScreen
import com.example.fastshippinglogin.ui.theme.LightPrimaryColor
import com.example.fastshippinglogin.ui.theme.PrimaryColor
import com.example.fastshippinglogin.ui.theme.Purple700
import com.example.fastshippinglogin.viewmodel.account.updateProfileInFirestore
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderTab(
    navController: NavHostController = rememberNavController(),
    user: FirebaseUser
) {
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    )


    Scaffold(
        topBar = {
            OrderAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { padingValue ->
        Column(
            modifier = Modifier.padding(padingValue)
        ) {
            val pageState = rememberPagerState (
                pageCount = {2}
            )
            val coroutineScope = rememberCoroutineScope()
            TabRow(
                selectedTabIndex = pageState.currentPage,
                containerColor = PrimaryColor,
                contentColor = LightPrimaryColor,
                divider = {},
                indicator = {tabPositions->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pageState.currentPage]),
                        height = 3.dp,
                        color = Purple700
                    )

                }
            ) {
                Tab(
                    selected = pageState.currentPage == 0,
                    onClick = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(0)
                        }
                    }
                ) {
                    Text(
                        text = "Tất cả",
                        modifier = Modifier.padding(10 .dp)
                    )
                }

                Tab(
                    selected = pageState.currentPage == 1,
                    onClick = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(1)
                        }
                    }
                ) {
                    Text(text = "Món Hàn")
                }


            }

            HorizontalPager(state = pageState,
                userScrollEnabled = false
            ) {page->

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    when(page){
                        0 -> AddCategoryScreen()
                    }
                }
            }
        }
    }
}

