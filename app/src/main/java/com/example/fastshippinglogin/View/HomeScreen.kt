package com.example.fastshippinglogin.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fastshippinglogin.ui.theme.FastShippingLoginTheme
import com.example.fastshippinglogin.R
@Composable
fun MainContent(){
    Box(Modifier.verticalScroll(rememberScrollState())) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .offset(0.dp, (-30).dp),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Header Background",
            contentScale = ContentScale.FillWidth
        )
        Column {
            AppBar()
            Content()
        }

    }
}

@Composable
fun AppBar(){
    Row(
        Modifier
            .padding(16.dp)
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
        ) {
        TextField(
            value = "",
            onValueChange = {},
            label = { Text(text = "Search Food, Vegetable, etc.", fontSize = 12.sp) },
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search") },
//            colors = TextFieldDefaults.textFieldColors(
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent
//            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = { }) {
            Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "", tint = Color.White)
        }
        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "", tint = Color.White)
        }
    }
}

@Composable
fun Content() {
    Column() {
      Header()
        Spacer(modifier = Modifier.height(16.dp))
      Promotions()
        Spacer(modifier = Modifier.height(16.dp))
     CategorySection()
        Spacer(modifier = Modifier.height(16.dp))
    //    BestSellerSection()
    }
}

@Composable
fun Header(){
    Card(
        Modifier
            .height(64.dp)
            .padding(horizontal = 16.dp),
        //elevation = 4.dp,
        shape = RoundedCornerShape(16 .dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){
            QrButton()
            VerticalDivider()
            //Tiền
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable { }
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "", tint = Color(0xFF6FCF97))
                Column {
                    Text(text = "$120", fontWeight = FontWeight.Bold, fontSize = 16 .sp)
                    Text(text = "Top up", color = MaterialTheme.colorScheme.primary, fontSize = 12 .sp)
                }
            }
        //Xu
            VerticalDivider()
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable { }
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                Column {
                    Text(text = "$10", fontWeight = FontWeight.Bold, fontSize = 16 .sp)
                    Text(text = "Point", color = Color.LightGray, fontSize = 12 .sp)
                }
            }
        }
    }
}

@Composable
fun QrButton(){
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
fun VerticalDivider() {
    Divider(
        color = Color(0xFFF1F1F1),
        modifier = Modifier
            .width(1.dp)
            .height(32.dp)
    )
}

@Composable
fun Promotions(){
    LazyRow(
        Modifier.height(160 .dp),
        contentPadding = PaddingValues(horizontal = 16 .dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        item {
            PromotionItemGreeting(
                imagePainter = painterResource(id = R.drawable.ic_launcher_background),
                title = "Xin chào ngày mới,",
                name = "An Huỳnh",
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun PromotionItemGreeting(
    title: String = "",
    name: String = "",
    backgroundColor: Color = Color.Transparent,
    imagePainter: Painter
){
    Card(
        Modifier
            .width(300.dp)
            .background(backgroundColor),
        shape = RoundedCornerShape(8. dp),

    ) {
        Row {
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center
            ){
                Text(text = title, fontSize = 16.sp, color = Color.White)
                Text(text = name, fontSize = 20 .sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Image(
                painter = imagePainter,
                contentDescription = "",
                Modifier
                    .fillMaxHeight()
                    .weight(1f),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
        }
    }
}
@Composable
fun PromotionItem(
    title: String = "",
    subtitle : String ="",
    header: String ="",
    backgroundColor: Color = Color.Transparent,
    imgPainter: Painter
){
    Card(
        Modifier
            .width(300.dp)
            .background(backgroundColor),
        shape = RoundedCornerShape(8 .dp)
    ){
        Row{
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = title, fontSize = 16.sp, color = Color.White)
                Text(text = subtitle, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = header, fontSize = 28.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Image(
                painter = imgPainter,
                contentDescription ="",
                Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center

            )
        }
    }
}

@Composable
fun CategorySection() {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Category", style = MaterialTheme.typography.labelMedium)
            TextButton(onClick = {}) {
                Text(text = "More", color = MaterialTheme.colorScheme.primary)
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryButton(
                text = "Ô tô",
                icon = painterResource(id = R.drawable.ic_launcher_foreground),
                backgroundColor = Color(0xffFEF4E7)
            )
        }
    }
}

@Composable
fun CategoryButton(
    text: String = "",
    icon: Painter,
    backgroundColor: Color
) {
    Column(
        Modifier
            .width(72.dp)
            .clickable { }
    ) {
        Box(
            Modifier
                .size(72.dp)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(18.dp)
        ) {
            Image(painter = icon, contentDescription = "", modifier = Modifier.fillMaxSize())
        }
        Text(text = text, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 12.sp)
    }
}