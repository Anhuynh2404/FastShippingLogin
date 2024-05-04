package com.example.fastshippinglogin.Controller

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignedIn: (FirebaseUser) -> Unit,
    onSignInError: (String) -> Unit // Callback for sign-in error
) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                onSignedIn(user!!)
            } else {
                // Handle sign-in failure
                onSignInError("Invalid email or password")
            }
        }
}