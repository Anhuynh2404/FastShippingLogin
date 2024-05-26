package com.example.fastshippinglogin.View.settingview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fastshippinglogin.Model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun MainScreennn(user: FirebaseUser, onSignOut: () -> Unit) {
    val userProfile = remember { mutableStateOf<User?>(null) }

    // Fetch user profile from Firestore
    LaunchedEffect(user.uid) {
        val firestore = FirebaseFirestore.getInstance()
        val userDocRef = firestore.collection("users").document(user.uid)

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val firstName = document.getString("firstName")
                    val lastName = document.getString("lastName")

                    userProfile.value = User(user.uid,firstName, lastName, user.email ?: "")
                } else {
                    // Handle the case where the document doesn't exist
                }
            }
            .addOnFailureListener { e ->
                // Handle failure

            }
    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            userProfile.value?.let {
                Text("Welcome, ${it.firstName} ${it.lastName}!")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onSignOut()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Sign Out")
            }
        }
}