package com.example.fastshippinglogin.View

import android.os.Build
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.fastshippinglogin.Model.User
import com.example.fastshippinglogin.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun MainContent(user: FirebaseUser){
    val userProfile = remember { mutableStateOf<User?>(null) }
    LaunchedEffect(user.uid) {
        val firestore = FirebaseFirestore.getInstance()
        val userDocRef = firestore.collection("users").document(user.uid)

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val firstName = document.getString("firstName")
                    val lastName = document.getString("lastName")

                    userProfile.value = User(firstName, lastName, user.email ?: "")
                } else {
                    // Handle the case where the document doesn't exist
                }
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }
    Box(Modifier.verticalScroll(rememberScrollState())) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .offset(0.dp, (-30).dp),
            painter = painterResource(id = R.drawable.bg_main),
            contentDescription = "Header Background",
            contentScale = ContentScale.FillWidth
        )
        Column {
            AppBar()
            userProfile.value?.let {
                val firstName = it.firstName
                if (firstName != null) {
                    Content(
                        firstName = firstName
                    )
                }
            }
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
            label = { Text(text = "Tìm kiếm trên ứng dụng", fontSize = 12.sp) },
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
fun Content(firstName: String) {
    Column() {
      Header()
        Spacer(modifier = Modifier.height(16.dp))
        Promotions(firstName)
        Spacer(modifier = Modifier.height(16.dp))
        ServiceSection()
        Spacer(modifier = Modifier.height(16.dp))
        BestSellerSection()
    }
}

@Composable
fun Header(){
    Card(
        Modifier
            .height(64.dp)
            .padding(horizontal = 16.dp)
            .background(Color(0xFF3700B3)),
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
                Icon(painter = painterResource(id = R.drawable.ic_money), contentDescription = "", tint = Color(0xFF6FCF97))
                Column {
                    Text(text = "120.000 VND", fontSize = 14 .sp, color = Color.White)
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
                Icon(painter = painterResource(id = R.drawable.ic_coin), contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                Column {
                    Text(text = "100", fontSize = 14 .sp, color = Color.White,)
                    Text(text = "Xu", color = Color.LightGray, fontSize = 12 .sp)
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
            painter = painterResource(id = R.drawable.ic_scan),
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
fun Promotions(firstName:String){
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    LazyRow(
        Modifier.height(160 .dp),
        contentPadding = PaddingValues(horizontal = 16 .dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        item {
            PromotionItemGreeting(
                title = "Xin chào ngày mới,",
                name = firstName,
                backgroundColor = Color.Magenta,
                bg = rememberAsyncImagePainter(R.drawable.greeting, imageLoader)
            )
        }
        item {
            PromotionItemGreeting(
                title = "",
                name = "",
                backgroundColor = Color.Magenta,
                bg = painterResource(id = R.drawable.nhahang1)
            )
        }
    }
}

@Composable
fun PromotionItemGreeting(
    title: String = "",
    name: String = "",
    backgroundColor: Color,
    bg : Painter
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
                painter = bg,
                contentDescription = "",
                Modifier
                    .fillMaxSize()
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
fun ServiceSection() {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Danh mục", style = MaterialTheme.typography.labelMedium)
            TextButton(onClick = {}) {
                Text(text = "Tất cả", color = MaterialTheme.colorScheme.primary)
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryButton(
                text = "Ô tô",
                icon = painterResource(id = R.drawable.oto),
                backgroundColor = Color(0xffFEF4E7)
            )
            CategoryButton(
                text = "Xe máy",
                icon = painterResource(id = R.drawable.motobike),
                backgroundColor = Color(0xffFEF4E7)
            )
            CategoryButton(
                text = "Đồ ăn",
                icon = painterResource(id = R.drawable.food),
                backgroundColor = Color(0xffFEF4E7)
            )
            CategoryButton(
                text = "Giao hàng",
                icon = painterResource(id = R.drawable.diliver),
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
                .padding(5.dp)
        ) {
            Image(painter = icon, contentDescription = "", modifier = Modifier.fillMaxSize())
        }
        Text(text = text, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 12.sp)
    }
}

@Composable
fun BestSellerSection() {
    Column() {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Tối nay ăn gì?", style = MaterialTheme.typography.labelLarge)
            TextButton(onClick = {}) {
                Text(text = "Xem thêm", color = MaterialTheme.colorScheme.primary)
            }
        }

        BestSellerItems()
    }
}
@Composable
fun BestSellerItems() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BestSellerItem(
                imagePainter = painterResource(id = R.drawable.img),
                title = "Bánh xèo bà ba",
                price = "20.000/3",
            )
        }
        item {
            BestSellerItem(
                imagePainter = painterResource(id = R.drawable.chickken),
                title = "Gà rán AH",
                price = "35.000",
            )
        }
        item {
            BestSellerItem(
                imagePainter = painterResource(id = R.drawable.comtam),
                title = "Cơm Tấm Hội An",
                price = "25.000",
            )
        }
    }
}

@Composable
fun BestSellerItem(
    title: String = "",
    price: String = "",
    discountPercent: Int = 0,
    imagePainter: Painter
) {
    Card(
        Modifier
            .width(160.dp)
    ) {
        Column(
            Modifier
                .padding(bottom = 32.dp)
        ) {
            Image(
                painter = imagePainter, contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Row {
                    Text(
                        "$${price}",
                        textDecoration = if (discountPercent > 0)
                            TextDecoration.LineThrough
                        else
                            TextDecoration.None,
                        color = if (discountPercent > 0) Color.Gray else Color.Black
                    )
                    if (discountPercent > 0) {
                        Text(text = "[$discountPercent%]", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}