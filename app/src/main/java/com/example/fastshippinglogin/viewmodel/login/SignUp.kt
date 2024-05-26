package com.example.fastshippinglogin.viewmodel.login

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

fun signUp(
    auth: FirebaseAuth,
    emailUser: String,
    password: String,
    firstName: String,
    lastName: String,
    onSignedIn: (FirebaseUser) -> Unit,
    onError: (String) -> Unit,
    navController: NavHostController
) {
    auth.createUserWithEmailAndPassword(emailUser, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val user = auth.currentUser
            val userProfile = hashMapOf(
                "firstName" to firstName,
                "lastName" to lastName,
                "emailUser" to emailUser
            )
            val  firestore = FirebaseFirestore.getInstance()
            firestore.collection("users")
                .document(user!!.uid)
                .set(userProfile)
                .addOnSuccessListener {
                    onSignedIn(user)
                }
                .addOnFailureListener {
                    onError("Lỗi khi lưu thông tin người dùng!")
                }
        } else {
            onError("Đăng ký thất bại. Vui lòng kiểm tra lại thông tin!")
        }
    }
}