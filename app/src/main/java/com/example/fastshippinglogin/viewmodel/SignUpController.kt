package com.example.fastshippinglogin.viewmodel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

fun signUp(
    auth: FirebaseAuth,
    emailUser: String,
    password: String,
    firstName: String,
    lastName: String,
    onSignedIn: (FirebaseUser) -> Unit
) {
    auth.createUserWithEmailAndPassword(emailUser, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val userProfile = hashMapOf(
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "emailUser" to emailUser
                )

                val firestore = FirebaseFirestore.getInstance()
                firestore.collection("users")
                    .document(user!!.uid)
                    .set(userProfile)
                    .addOnSuccessListener {
                        onSignedIn(user)
                    }
                    .addOnFailureListener {
                        //handle exception

                    }
            } else {
                // Handle sign-up failure
            }
        }
}