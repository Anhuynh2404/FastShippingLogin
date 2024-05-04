package com.example.fastshippinglogin.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fastshippinglogin.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardHome(){
    var text by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color(android.graphics.Color.parseColor("#ede7f8")))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
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
                    .padding(start = 14.dp)
                    .weight(0.7f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "An Huynh",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22 .sp
                )
                Text(
                    text = "Ok Ok",
                    color = Color.Black,
                    fontSize = 18 .sp,
                    modifier = Modifier.padding(top = 14 .dp)
                )
            }
        }
        TextField(
            value = text,
            onValueChange ={
                text = it
            },
            label = {
                Text(
                    text = "Searching for..."
                )
            },
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .height(45.dp)
                        .width(45.dp)
                        .background(
                            Color(android.graphics.Color.parseColor("#fe5b52"))
                        )
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription ="",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(end = 6.dp)
                )
            },
            shape = RoundedCornerShape(10 .dp),
           //
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .background(Color.White, CircleShape)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ){
            Column(
                Modifier
                    .weight(0.5f)
                    .padding(end = 12.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .height(65.dp)
                        .width(75.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#fe5b52")),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = ""
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(40.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#Dad8ff")),
                            shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                   Text(
                       text = "Hello",
                       fontSize = 14 .sp,
                       fontWeight = FontWeight.Bold,
                       color = Color(android.graphics.Color.parseColor("#fe5b52")),
                   )
                }
            }
        }
    }
}

data class BottomMenuItem(
    val lable: String,
    val icon : Painter
)

@Composable
private fun PrepareBottomMenu(): List<BottomMenuItem>{
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()

    bottomMenuItemList.add(
        BottomMenuItem(lable = "Trang chủ", icon = painterResource(id = R.drawable.ic_launcher_background))
    )
    bottomMenuItemList.add(
        BottomMenuItem(lable = "Sản Phẩm", icon = painterResource(id = R.drawable.ic_launcher_background))
    )
    bottomMenuItemList.add(
        BottomMenuItem(lable = "Trò chuyện", icon = painterResource(id = R.drawable.ic_launcher_background))
    )
    bottomMenuItemList.add(
        BottomMenuItem(lable = "Cài đặt", icon = painterResource(id = R.drawable.ic_launcher_background))
    )
    return bottomMenuItemList
}

@Composable
fun MyBottomBar(){
    val bottomMenuItemList = PrepareBottomMenu()
    val contentForToast = LocalContext.current.applicationContext
    val selectItem by remember {
        mutableStateOf("Trang chủ")
    }

    BottomAppBar(
        modifier = Modifier.background(Color.White)
    ) {
        bottomMenuItemList.forEachIndexed { index, bottomMenuItem ->
            if (index == 2){
                BottomNavigationItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { /*TODO*/ },
                    enabled = false
                )
            }
        }
    }
}