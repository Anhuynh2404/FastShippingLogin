package com.example.fastshippinglogin.viewmodel.login

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

fun signIn(
    auth: FirebaseAuth,
    emailUser: String,
    password: String,
    onSignedIn: (FirebaseUser) -> Unit,
    onError: (String) -> Unit,
    navController: NavHostController
) {
    auth.signInWithEmailAndPassword(emailUser, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val user = auth.currentUser
            onSignedIn(user!!)
        } else {
            onError("Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin!")
        }
    }
}