package com.example.fastshippinglogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fastshippinglogin.ui.theme.FastShippingLoginTheme
import com.google.firebase.auth.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fastshippinglogin.Model.User
import com.example.fastshippinglogin.View.AuthScreen
import com.example.fastshippinglogin.View.DashboardHome
import com.example.fastshippinglogin.View.MainContent

class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           AppContent(auth = auth)
//            MainTheme {
//                DashboardHome()
//            }
        }
    }

    @Composable
    fun MainTheme(content: @Composable () -> Unit){
        FastShippingLoginTheme {
            Surface(color = MaterialTheme.colorScheme.background) {
                content()
            }
        }
    }
    @Composable
    fun AppContent(auth: FirebaseAuth) {
        var showSplashScreen by remember { mutableStateOf(true) }

        LaunchedEffect(showSplashScreen) {
            delay(2000)
            showSplashScreen = false
        }
        Crossfade(targetState = showSplashScreen, label = "") { isSplashScreenVisible ->
            if (isSplashScreenVisible) {
                SplashScreen {
                    showSplashScreen = false
                }
            } else {
               AuthOrMainScreen(auth)
            }
        }
    }

    @Composable
    fun SplashScreen(navigateToAuthOrMainScreen: () -> Unit) {
        var rotationState by remember { mutableFloatStateOf(0f) }

        LaunchedEffect(true) {
            delay(2000)
            navigateToAuthOrMainScreen()
        }

        LaunchedEffect(rotationState) {
            while (true) {
                delay(16) // Adjust the delay to control the rotation speed
                rotationState += 1f
            }
        }
        val scale by animateFloatAsState(
            targetValue = 1f,
            animationSpec = TweenSpec(durationMillis = 500), label = ""
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .scale(scale)
                    .rotate(rotationState) // Apply the rotation effect
            )
        }
    }
    //End SplashScreen

    @Composable
    fun AuthOrMainScreen(auth: FirebaseAuth) {
        var user by remember { mutableStateOf(auth.currentUser) }

        if (user == null) {
            AuthScreen(
                onSignedIn = { signedInUser ->
                    user = signedInUser
                }
            )
        } else {
//            MainScreen(
//                user = user!!,  // Pass the user information to MainScreen
//                onSignOut = {
//                    auth.signOut()
//                    user = null
//                }
//            )

//            MainTheme {
//                MainContent(
//                    user = user!!
//                )
//            }
            AuthScreen(
                onSignedIn = { signedInUser ->
                    user = signedInUser
                }
            )
        }
    }

    @Composable
    fun MainScreen(user: FirebaseUser, onSignOut: () -> Unit) {
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

                        userProfile.value = User(firstName, lastName, user.email ?: "")
                    } else {
                        // Handle the case where the document doesn't exist
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure

                }
        }
//        MainTheme {
//            MainContent()
//        }
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            userProfile.value?.let {
//                Text("Welcome, ${it.firstName} ${it.lastName}!")
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = {
//                    onSignOut()
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//            ) {
//                Text("Sign Out")
//            }
//        }
    }





    @Preview(showBackground = true)
    @Composable
    fun PreviewAuthOrMainScreen() {
        AuthScreen(
            onSignedIn = {
            }
        )
    }
}