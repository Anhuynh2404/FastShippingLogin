//package com.example.fastshippinglogin.View
//
//import android.util.Patterns
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.text.ClickableText
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.paint
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.SpanStyle
//import androidx.compose.ui.text.buildAnnotatedString
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.text.withStyle
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import com.google.firebase.ktx.Firebase
//import com.example.fastshippinglogin.R
//import com.example.fastshippinglogin.viewmodel.login.signIn
//import com.example.fastshippinglogin.viewmodel.login.signUp
//import com.google.firebase.auth.ktx.auth
//
//@Composable
//fun AuthScreen(onSignedIn: (FirebaseUser) -> Unit, navController: NavHostController) {
//    val auth: FirebaseAuth by lazy { Firebase.auth }
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var firstName by remember { mutableStateOf("") }
//    var lastName by remember { mutableStateOf("") }
//    var isSignIn by remember { mutableStateOf(true) }
//    var isPasswordVisible by remember { mutableStateOf(false) }
//    var myErrorMessage by remember { mutableStateOf<String?>(null) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//            .paint(
//                painterResource(id = R.drawable.bg_main_login),
//                contentScale = ContentScale.FillBounds
//            )
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.img_login),
//                contentDescription = "Img_logo_Login",
//                modifier = Modifier.size(150.dp)
//            )
//            Text(
//                text = if (isSignIn) "Đăng nhập" else "Đăng ký",
//                fontWeight = FontWeight.Bold,
//                fontSize = 30.sp
//            )
//            Spacer(modifier = Modifier.height(5.dp))
//            if (!isSignIn) {
//                OutlinedTextField(
//                    value = firstName,
//                    onValueChange = { firstName = it },
//                    label = { Text(text = "Họ") },
//                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                    modifier = Modifier.width(320.dp),
//                    maxLines = 1
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                OutlinedTextField(
//                    value = lastName,
//                    onValueChange = { lastName = it },
//                    label = { Text(text = "Tên") },
//                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                    modifier = Modifier.width(320.dp),
//                    maxLines = 1
//                )
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//            OutlinedTextField(
//                value = email,
//                onValueChange = { email = it },
//                label = { Text(text = "Email") },
//                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                visualTransformation = VisualTransformation.None,
//                modifier = Modifier.width(320.dp),
//                maxLines = 1
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text(text = "Mật khẩu") },
//                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//                trailingIcon = {
//                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
//                        val icon = if (isPasswordVisible) Icons.Default.Check else Icons.Default.Clear
//                        Icon(imageVector = icon, contentDescription = "Toggle Password Visibility")
//                    }
//                },
//                modifier = Modifier.width(320.dp),
//                maxLines = 1
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            if (myErrorMessage != null) {
//                Text(
//                    text = myErrorMessage!!,
//                    color = Color.Red,
//                    modifier = Modifier.fillMaxWidth().padding(start = 90.dp)
//                )
//            }
//            Spacer(modifier = Modifier.height(10.dp))
//            Button(
//                onClick = {
//                    myErrorMessage = validateInputs(email, password, isSignIn, firstName, lastName)
//                    if (myErrorMessage == null) {
//                        if (isSignIn) {
//                            signIn(auth, email, password, onSignedIn = { signedInUser ->
//                                onSignedIn(signedInUser)
//                            }, onError = { myErrorMessage = it },navController)
//                        } else {
//                            signUp(auth, email, password, firstName, lastName, onSignedIn = { signedInUser ->
//                                onSignedIn(signedInUser)
//                            }, onError = { myErrorMessage = it },navController)
//                        }
//                    }
//                },
//                modifier = Modifier.fillMaxWidth().padding(start = 40.dp, end = 40.dp)
//            ) {
//                Text(text = if (isSignIn) "Đăng nhập" else "Đăng ký", fontSize = 18.sp)
//            }
//            Box(
//                modifier = Modifier.fillMaxWidth().height(50.dp).padding(8.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                ClickableText(
//                    text = buildAnnotatedString {
//                        withStyle(style = SpanStyle(color = Color.Blue)) {
//                            append(if (isSignIn) "Bạn không có tài khoản? Đăng ký" else "Bạn đã có tài khoản? Đăng nhập")
//                        }
//                    },
//                    onClick = {
//                        myErrorMessage = null
//                        email = ""
//                        password = ""
//                        isSignIn = !isSignIn
//                    }
//                )
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = if (isSignIn) "Hoặc đăng nhập với" else "Hoặc đăng ký với", color = Color.DarkGray)
//            Spacer(modifier = Modifier.height(25.dp))
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
//                Image(
//                    painter = painterResource(id = R.drawable.facebook),
//                    contentDescription = null,
//                    modifier = Modifier.clickable { /*signInWithProvider(auth, FacebookAuthProvider.PROVIDER_ID, onSignedIn, onError = { myErrorMessage = it })*/ }
//                        .weight(1f).padding(start = 80.dp, end = 40.dp)
//                )
//                Image(
//                    painter = painterResource(id = R.drawable.google),
//                    contentDescription = null,
//                    modifier = Modifier.clickable { /*signInWithProvider(auth, GoogleAuthProvider.PROVIDER_ID, onSignedIn, onError = { myErrorMessage = it })*/ }
//                        .weight(1f).padding(start = 40.dp, end = 80.dp)
//                )
//            }
//        }
//    }
//}
//
//fun validateInputs(email: String, password: String, isSignIn: Boolean, firstName: String, lastName: String): String? {
//    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//        return "Email không đúng định dạng!"
//    }
//    if (password.length < 8 || !password.any { it.isDigit() } || !password.any { it.isLetter() }) {
//        return "Mật khẩu phải có ít nhất 8 ký tự, bao gồm cả chữ cái và số!"
//    }
//    if (!isSignIn && (firstName.isBlank() || lastName.isBlank())) {
//        return "Vui lòng nhập đầy đủ họ và tên!"
//    }
//    return null
//}
//
//
////
////fun signUp(auth: FirebaseAuth, email: String, password: String, firstName: String, lastName: String, onSignedIn: (FirebaseUser) -> Unit, onError: (String) -> Unit) {
////    auth.createUserWithEmailAndPassword(email, password)
////        .addOnCompleteListener { task ->
////            if (task.isSuccessful) {
////                auth.currentUser?.let { user ->
////                    val userProfile = hashMapOf("firstName" to firstName, "lastName" to lastName, "emailUser" to email)
////                    FirebaseFirestore.getInstance().collection("users").document(user.uid).set(userProfile)
////                        .addOnSuccessListener { onSignedIn(user) }
////                        .addOnFailureListener { onError("Lỗi khi lưu thông tin người dùng!") }
////                }
////            } else {
////                onError("Đăng ký thất bại. Vui lòng kiểm tra lại thông tin!")
////            }
////        }
////}
//
