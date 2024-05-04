package com.example.fastshippinglogin.View

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fastshippinglogin.Controller.signIn
import com.example.fastshippinglogin.Controller.signUp
import com.example.fastshippinglogin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun AuthScreen(onSignedIn: (FirebaseUser) -> Unit) {
    val auth: FirebaseAuth by lazy { Firebase.auth }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isSignIn by remember { mutableStateOf(true) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    // State variables for error message
    var myErrorMessage by remember { mutableStateOf<String?>(null) }

    val imagePainter: Painter = painterResource(id = R.drawable.ic_launcher_background)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.bg_main_login),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_login),
                contentDescription = "Img_logo_Login",
                modifier = Modifier.size(200.dp)
            )
            if(isSignIn){
                Text(
                    text = "Đăng nhập",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Đăng nhập với tài khoán của bạn ",
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            }else{
                Text(
                    text = "Đăng ký",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }


            if (!isSignIn) {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = firstName,
                    onValueChange = {
                        firstName = it
                    },
                    label = { Text(text = "Họ") },
                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(320 .dp),
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = lastName,
                    onValueChange = {
                        lastName = it
                    },
                    label = { Text(text = "Tên ") },
                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(320 .dp),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = { Text(text = "Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                visualTransformation = if (isSignIn) VisualTransformation.None else VisualTransformation.None,
                modifier = Modifier.width(320 .dp),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text(text = "Mật khẩu") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { isPasswordVisible = !isPasswordVisible }
                    ) {
                        val icon =
                            if (isPasswordVisible) Icons.Default.Lock else Icons.Default.Search
                        Icon(
                            imageVector = icon,
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                },
                modifier = Modifier.width(320 .dp),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (myErrorMessage != null) {
                Text(
                    text = myErrorMessage!!,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 90.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    Log.i("infor", "UserName: $email Password: $password")
                    if (email.isBlank() || password.isBlank()) {
                        myErrorMessage = "Nhập đầy đủ thông tin để tiếp tục!"
                    } else {
                        if (isSignIn) {
                            signIn(auth, email, password,
                                onSignedIn = { signedInUser ->
                                    onSignedIn(signedInUser)
                                },
                                onSignInError = { errorMessage ->
                                    // Show toast message on sign-in error
                                    myErrorMessage = errorMessage
                                }
                            )
                        } else {
                            signUp(
                                auth,
                                email,
                                password,
                                firstName,
                                lastName
                            ) { signedInUser ->
                                onSignedIn(signedInUser)
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp)
            ) {
                Text(
                    text = if (isSignIn) "Đăng nhập" else "Đăng ký",
                    fontSize = 18.sp,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(8.dp),
            ) {
                ClickableText(
                    text = AnnotatedString(buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Blue)) {
                            append(
                                if (isSignIn) {
                                    "Bạn không có tài khoản? Đăng ký"
                                } else {
                                    "Bạn đã có tài khoản? Đăng nhập"
                                }
                            )
                        }
                    }.toString()),
                    onClick = {
                        myErrorMessage = null
                        email = ""
                        password = ""
                        isSignIn = !isSignIn
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isSignIn) "Hoặc đăng nhập với" else "Hoặc đăng ký với",
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                    }
                        .weight(1f)
                        .padding(start = 100 .dp, end = 40 .dp),
                )
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier.clickable {

                    }
                        .weight(1f)
                        .padding(start = 40 .dp, end = 100 .dp),
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuthOrMainScreen() {
    AuthScreen(
        onSignedIn = {
        }
    )
}