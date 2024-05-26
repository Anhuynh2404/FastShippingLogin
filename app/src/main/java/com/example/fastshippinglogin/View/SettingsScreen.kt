package com.example.fastshippinglogin.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fastshippinglogin.Model.User
import com.example.fastshippinglogin.ui.theme.Poppins
import com.example.fastshippinglogin.ui.theme.PrimaryColor
import com.example.fastshippinglogin.ui.theme.SecondaryColor
import com.example.fastshippinglogin.ui.theme.Shapes
import com.example.fastshippinglogin.R
import com.example.fastshippinglogin.ui.theme.LightPrimaryColor
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


@ExperimentalMaterialApi
@Composable
fun SettingsScreen(
    navController: NavHostController = rememberNavController(),
    user: User,
) {
    val eUser by remember { mutableStateOf(user) }
    val email = eUser.emailUser
    val imgUrl = eUser.imageUrl
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    androidx.compose.material.Text(
                        text = "Cài đặt",
                        fontFamily = Poppins,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                },
            )
        }
    ) {
        Box(Modifier.verticalScroll(rememberScrollState()).padding(it)) {
            Column() {
                ProfileCardUI(navController = navController, email)
                GeneralOptionsUI()
                SupportOptionsUI()
            }
        }
    }
}

@Composable
fun ProfileCardUI(
    navController: NavHostController,
    email : String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp),
        backgroundColor = Color.White,
        elevation = 0.dp,
        shape = Shapes.large
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                androidx.compose.material.Text(
                    text = "Kiểm tra tài khoản",
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )

                androidx.compose.material.Text(
                    text = email,
                    fontFamily = Poppins,
                    color = Color.Gray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                    Button(
                        modifier = Modifier.padding(top = 10.dp),
                        onClick = {
                            navController.navigate("ViewProfile")
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = PrimaryColor
                        ),
                        contentPadding = PaddingValues(horizontal = 30.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 2.dp
                        ),
                        shape = Shapes.medium
                    ) {
                        androidx.compose.material.Text(
                            text = "Xem chi tiết",
                            fontFamily = Poppins,
                            color = SecondaryColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "",
                    modifier = Modifier.height(120.dp)
                )
            }
    }
}


@ExperimentalMaterialApi
@Composable
fun GeneralOptionsUI() {
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        androidx.compose.material.Text(
            text = "Chung",
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        GeneralSettingItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.notification),
            onClick = {
            }
        )
        GeneralSettingItem(
            icon = R.drawable.img_login,
            mainText = stringResource(id = R.string.offer),
            onClick = {}
        )
        GeneralSettingItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.address),
            onClick = {}
        )
        GeneralSettingItem(
            icon = R.drawable.img_login,
            mainText = stringResource(id = R.string.order_detail),
            onClick = {}
        )
        GeneralSettingItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.favorite_store),
            onClick = {}
        )
        GeneralSettingItem(
            icon = R.drawable.img_login,
            mainText = stringResource(id = R.string.recently_viewed),
            onClick = {}
        )
//        GeneralSettingItem()
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
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = Shapes.medium)
                        .background(LightPrimaryColor)
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

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

//                    androidx.compose.material.Text(
//                        text = subText,
//                        fontFamily = Poppins,
//                        color = Color.Gray,
//                        fontSize = 10.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier.offset(y = (-4).dp)
//                    )
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

@ExperimentalMaterialApi
@Composable
fun SupportOptionsUI() {
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        androidx.compose.material.Text(
            text = "Hỗ trợ",
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        SupportItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.contact),
            onClick = {}
        )
        SupportItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.privacy_policy),
            onClick = {}
        )
        SupportItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.language),
            onClick = {}
        )
        SupportItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.setting),
            onClick = {}
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun SupportItem(icon: Int, mainText: String, onClick: () -> Unit) {
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = Shapes.medium)
                        .background(LightPrimaryColor)
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                androidx.compose.material.Text(
                    text = mainText,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )

        }
    }
}

//@Composable
//fun NavigateToMainScreen(user: FirebaseUser?, onSignOut: () -> Unit) {
//    MainScreen(user = user, onSignOut = onSignOut)
//}


@Composable
fun MainScreen(user: FirebaseUser?, onSignOut: () -> Unit) {
    val userProfile = remember { mutableStateOf<User?>(null) }

    if (user != null) {
        LaunchedEffect(user.uid) {
            val firestore = FirebaseFirestore.getInstance()
            val userDocRef = firestore.collection("users").document(user.uid)
            userDocRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val firstName = document.getString("firstName")
                        val lastName = document.getString("lastName")

                        userProfile.value = User(user.uid,firstName, lastName, user.email ?: "")
                    } else {
                        // Handle the case where the document doesn't exist
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        userProfile.value?.let {
            Text("Welcome, ${it.firstName} ${it.lastName}!")
        }

        Spacer(modifier = Modifier.height(16.dp))

        androidx.compose.material3.Button(
            onClick = {
                onSignOut()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Sign Out")
        }
    }
}