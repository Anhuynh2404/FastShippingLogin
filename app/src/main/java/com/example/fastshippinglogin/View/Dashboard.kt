package com.example.fastshippinglogin.View

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fastshippinglogin.Model.User
import com.example.fastshippinglogin.R
import com.example.fastshippinglogin.ui.theme.LightPrimaryColor
import com.example.fastshippinglogin.ui.theme.Poppins
import com.example.fastshippinglogin.ui.theme.PrimaryColor
import com.example.fastshippinglogin.ui.theme.Purple700
import com.example.fastshippinglogin.ui.theme.SecondaryColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileHome(
    navController: NavHostController = rememberNavController(),
    user: User,
){
    val eUser by remember { mutableStateOf(user) }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val firstN = eUser.firstName
    val lastN = eUser.lastName
    val address = eUser.address
//    val currentScreen = CupcakeScreen.valueOf(
//        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
//    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    androidx.compose.material.Text(
                        text = "Thông tin cá nhân",
                        fontFamily = Poppins,
                        color = LightPrimaryColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                        },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ProfileHeader(firstN.toString(),lastN.toString(), address.toString())
            Spacer(modifier = Modifier.height(30 .dp))
            ProfileOption(navController = navController)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileHeader(
    firstName:String, lastName: String, address: String
) {
    var text by rememberSaveable {
        mutableStateOf("")
    }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clickable { }
            )
            Column(
                modifier = Modifier
                    .height(100.dp)
                    .padding(start = 10.dp),
//                verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "$lastName $firstName",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                //    modifier = Modifier.
                )
                Text(
                    text = address?:"",
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 14.dp)
                )
            }
        }
    }
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ProfileTab(
) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
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

                },
                modifier = Modifier.fillMaxWidth()
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

                    }
                }
            }
        }
    }

@ExperimentalMaterialApi
@Composable
private fun ProfileOption(
    navController: NavHostController
) {
    val auth: FirebaseAuth by lazy { Firebase.auth }
    var user by remember { mutableStateOf(auth.currentUser) }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        GeneralSettingItem(
            icon = R.drawable.ic_launcher_background,
            mainText = "Chỉnh sửa thông tin",
            onClick = {
                navController.navigate("ModifyInfor")
            }
        )
        GeneralSettingItem(
            icon = R.drawable.img_login,
            mainText = "Thay đổi mật khẩu",
            onClick = {
                navController.navigate("ChangePassword")
            }
        )
        GeneralSettingItem(
            icon = R.drawable.ic_launcher_background,
            mainText = "Phương thức thanh toán",
            onClick = {
                navController.navigate("Payment")
            }
        )
        GeneralSettingItem(
            icon = R.drawable.img_login,
            mainText = "",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(30 .dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    auth.signOut()
                    user = null
                    navController.navigate("Logout")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material.Text("Đăng xuất")
        }
        Button(
            onClick = {
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material.Text("Xóa tài khoản")
        }
    }
}
@ExperimentalMaterialApi
@Composable
private fun GeneralSettingItem(icon: Int, mainText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.offset(y = (2).dp)
                ) {
                    androidx.compose.material.Text(
                        text = mainText,
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )

        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
   // ProfileHome()
}