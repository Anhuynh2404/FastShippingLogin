package com.example.fastshippinglogin.viewmodel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

fun signIn(
    auth: FirebaseAuth,
    emailUser: String,
    password: String,
    onSignedIn: (FirebaseUser) -> Unit,
    onSignInError: (String) -> Unit // Callback for sign-in error
) {
    auth.signInWithEmailAndPassword(emailUser, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                onSignedIn(user!!)
            } else {
                onSignInError("Invalid email or password")
            }
        }
}