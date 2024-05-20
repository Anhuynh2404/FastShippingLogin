package com.example.fastshippinglogin.View.orderView.ordersuccess

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.example.fastshippinglogin.R
import com.example.fastshippinglogin.ui.theme.Poppins
import com.example.fastshippinglogin.ui.theme.mainColorBlue
import com.example.fastshippinglogin.ui.theme.mainColorOrrange
import com.example.fastshippinglogin.ui.theme.mainColorWhite

@Composable
fun OrderSuccess(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.checked),
            contentDescription = "",
            Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(10 .dp))
        Text(
            text = "Đặt hàng thành công!",
            modifier = Modifier.padding(20.dp),
            fontSize = 22.sp,
            color = mainColorOrrange,
            textAlign = TextAlign.Center,
            fontFamily = Poppins
        )
        Row {
            Button(
                onClick = {
                    navController.navigate("OrderState")
                },
                colors = ButtonDefaults.buttonColors(Color.Gray),
                shape = RectangleShape,
                modifier = Modifier.padding(vertical = 20 .dp, horizontal = 16 .dp).weight(1f)
            ) {
                Text(
                    text = "Xem chi tiết",
                    style = MaterialTheme.typography.titleSmall.copy(color = Color.White)
                )
            }
            Button(
                onClick = {
                    navController.navigate("FeedBack")
                },
                colors = ButtonDefaults.buttonColors(mainColorOrrange),
                shape = RectangleShape,
                modifier = Modifier.padding(vertical = 20 .dp, horizontal = 16 .dp).weight(1f)
            ) {
                Text(
                    text = "Tiếp tục mua",
                    style = MaterialTheme.typography.titleSmall.copy(color = Color.White)
                )
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun PreviewT(){
//    OrderSuccess()
//}