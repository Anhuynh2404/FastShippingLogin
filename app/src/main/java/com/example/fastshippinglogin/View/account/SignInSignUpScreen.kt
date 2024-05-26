package com.example.fastshippinglogin.View.account

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fastshippinglogin.R
import com.example.fastshippinglogin.ui.theme.mainColorOrrange
import com.example.fastshippinglogin.ui.theme.mainColorWhite
import com.example.fastshippinglogin.viewmodel.account.AccountViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private val auth: FirebaseAuth by lazy { Firebase.auth }

@Composable
fun AuthScreen(
    navController: NavHostController,
    accountViewModel: AccountViewModel = viewModel()
) {
    var users by remember { mutableStateOf(auth.currentUser) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var isSignUp by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var firstNameError by remember { mutableStateOf<String?>(null) }
    var lastNameError by remember { mutableStateOf<String?>(null) }

    val user by accountViewModel.user.collectAsState()
    val errorMessage by accountViewModel.errorMessage.collectAsState()

    LaunchedEffect(user) {
        user?.let {
            navController.navigate("home") {
                popUpTo("auth") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.White),
                startY = 0f,
                endY = 1000f
            ))
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_main_login),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_login),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(100.dp)
                    .graphicsLayer {
                        scaleX = 1.5f
                        scaleY = 1.5f
                    }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (isSignUp) "Đăng ký" else "Đăng nhập",
                style = MaterialTheme.typography.h5,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (isSignUp) {
                TextField(
                    value = firstName,
                    onValueChange = {
                        firstName = it
                        firstNameError =  null
                    },
                    label = { Text("First Name", color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(mainColorWhite, shape = RoundedCornerShape(8.dp)),
                    isError = firstNameError != null,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )
                if (firstNameError != null) {
                    Text(
                        firstNameError!!,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = lastName,
                    onValueChange = {
                        lastName = it
                        lastNameError =  null
                    },
                    label = { Text("Last Name", color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(mainColorWhite, shape = RoundedCornerShape(8.dp)),
                    isError = lastNameError != null,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )
                if (lastNameError != null) {
                    Text(
                        lastNameError!!,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            TextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError =  if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) "Email không đúng định dạng!" else null
                },
                label = { Text("Email", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(mainColorWhite, shape = RoundedCornerShape(8.dp)),
                isError = emailError != null,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )
            if (emailError != null) {
                Text(
                    emailError!!,
                    color = mainColorOrrange,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = if (it.length < 8 || !it.any { it.isDigit() } || !it.any { it.isLetter() }) "Mật khẩu phải có ít nhất 8 ký tự, bao gồm cả chữ cái và số!" else null
                },
                label = { Text("Password", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(mainColorWhite, shape = RoundedCornerShape(8.dp)),
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError != null,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )
            if (passwordError != null) {
                Text(
                    passwordError!!,
                    color = mainColorOrrange,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    var hasError = false
                    if (isSignUp) {
                        accountViewModel.clearErrorMessage()
                        if (firstName.isBlank()) {
                            firstNameError = "Vui lòng nhập đầy đủ họ và tên!"
                            hasError = true
                        }
                        if (lastName.isBlank()) {
                            lastNameError = "Vui lòng nhập đầy đủ họ và tên!"
                            hasError = true
                        }
                        if (email.isBlank()) {
                            emailError = "Vui lòng nhập Email!"
                            hasError = true
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            emailError = "Email không đúng định dạng!"
                            hasError = true
                        }
                        if (password.isBlank()) {
                            passwordError = "Vui lòng nhập Mật khẩu!"
                            hasError = true
                        } else if (password.length < 8 || !password.any { it.isDigit() } || !password.any { it.isLetter() }) {
                            passwordError = "Mật khẩu phải có ít nhất 8 ký tự, bao gồm cả chữ cái và số!"
                            hasError = true
                        }

                    } else {
                        accountViewModel.clearErrorMessage()
                        if (email.isBlank()) {
                            emailError = "Vui lòng nhập Email!"
                            hasError = true
                        }
                        if (password.isBlank()) {
                            passwordError = "Vui lòng nhập Mật khẩu!"
                            hasError = true
                        }
                    }
                    if (!hasError) {
                        if (isSignUp) {
                            accountViewModel.signUp(firstName, lastName, email, password)
                        } else {
                            accountViewModel.signIn(email, password)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFF914D)
                )
            ) {
                Text(if (isSignUp) "Đăng ký" else "Đăng nhập", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = {
                isSignUp = !isSignUp
                // Clear errors when switching modes
                firstNameError = null
                lastNameError = null
                emailError = null
                passwordError = null
            }) {
                Text(if (isSignUp) "Bạn đã có tài khoản? Đăng nhập" else "Bạn chưa có tài khoản? Đăng ký", color = Color.Black)
            }
            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = Color.Red)
                accountViewModel.clearErrorMessage()
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* TODO: Handle Facebook sign in */ },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = Color.Transparent
                    ),
                    elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = "Facebook",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Facebook", color = Color.Black)
                }
                Button(
                    onClick = { /* TODO: Handle Google sign in */ },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = Color.Transparent
                    ),
                    elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Google", color = Color.Black)
                }
            }
        }
    }
}
