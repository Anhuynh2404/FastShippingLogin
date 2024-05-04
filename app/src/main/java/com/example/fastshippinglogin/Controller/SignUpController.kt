package com.example.fastshippinglogin.Controller

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    firstName: String,
    lastName: String,
    onSignedIn: (FirebaseUser) -> Unit
) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser

                // Create a user profile in Firestore
                val userProfile = hashMapOf(
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "email" to email
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