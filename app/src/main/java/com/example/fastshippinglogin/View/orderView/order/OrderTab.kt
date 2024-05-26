package com.example.fastshippinglogin.View.orderView.order

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fastshippinglogin.Model.Order
//import com.example.fastshippinglogin.View.CupcakeScreen
//import com.example.fastshippinglogin.View.OrderAppBar
import com.example.fastshippinglogin.ui.theme.LightPrimaryColor
import com.example.fastshippinglogin.ui.theme.PrimaryColor
import com.example.fastshippinglogin.ui.theme.Purple700
import com.example.fastshippinglogin.viewmodel.cart.CartViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OrderTab(
    navController: NavHostController = rememberNavController(),
    user: FirebaseUser,
    cartViewModel: CartViewModel = viewModel()
) {
    val orderByStatus = remember { mutableStateOf<List<Order>>(emptyList()) }
    val pageState = rememberPagerState(pageCount = { 4 })

    LaunchedEffect(key1 = pageState.currentPage) {
        when (pageState.currentPage) {
            0 -> cartViewModel.getOrderbyStatus(user.uid, "Chờ xác nhận", { orders ->
                orderByStatus.value = orders
            }, { e -> /* Handle error */ })
            1 -> cartViewModel.getOrderbyStatus(user.uid, "Chờ lấy hàng", { orders ->
                orderByStatus.value = orders
            }, { e -> /* Handle error */ })
            2 -> cartViewModel.getOrderbyStatus(user.uid, "Chờ giao hàng", { orders ->
                orderByStatus.value = orders
            }, { e -> /* Handle error */ })
            3 -> cartViewModel.getOrderbyStatus(user.uid, "Đã nhận hàng", { orders ->
                orderByStatus.value = orders
            }, { e -> /* Handle error */ })
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Đơn mua") },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            val coroutineScope = rememberCoroutineScope()
            TabRow(
                selectedTabIndex = pageState.currentPage,
                containerColor = PrimaryColor,
                contentColor = LightPrimaryColor,
                divider = {},
                indicator = { tabPositions ->
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
                        text = "Chờ xác nhận",
                        modifier = Modifier.padding(10.dp)
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
                    Text(
                        text = "Chờ lấy hàng",
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Tab(
                    selected = pageState.currentPage == 2,
                    onClick = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(2)
                        }
                    }
                ) {
                    Text(
                        text = "Chờ giao hàng",
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Tab(
                    selected = pageState.currentPage == 3,
                    onClick = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(3)
                        }
                    }
                ) {
                    Text(
                        text = "Đã nhận hàng",
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            HorizontalPager(state = pageState, userScrollEnabled = false) { page ->
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    orderByStatus.value.forEach { order ->
                        Text(text = "Order Id: ${order.items}")
                        Text(text = "User Id: ${order.userId}")
                        // Add more details as needed
                    }
                }
            }
        }
    }
}





