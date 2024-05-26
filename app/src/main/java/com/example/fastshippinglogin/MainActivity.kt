package com.example.fastshippinglogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fastshippinglogin.View.account.AuthScreen
import com.example.fastshippinglogin.viewmodel.account.AccountViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            AppContent(auth = auth)
        }
    }
}

@Composable
fun AppContent(auth: FirebaseAuth) {
   val accountViewModel: AccountViewModel = viewModel()
    val user by accountViewModel.user.collectAsState()
    val navController: NavHostController = rememberNavController()
    LaunchedEffect(user) {
        if (user == null) {
            navController.navigate("auth") {
                popUpTo("home") { inclusive = true }
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = if (user == null) "auth" else "home"
    ) {
        composable("auth") {
            AuthScreen(navController = navController, accountViewModel = accountViewModel)
        }
        composable("home") {
            user?.let {
                OrderFoodApp(user = it)
            }
        }
    }
}

//class MainActivity : ComponentActivity() {
//    private val auth: FirebaseAuth by lazy { Firebase.auth }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        FirebaseApp.initializeApp(this)
//        setContent {
//            AppContent(auth = auth)
//        }
//    }
//
//
//    @Composable
//    fun AppContent(auth: FirebaseAuth) {
//        var showSplashScreen by remember { mutableStateOf(true) }
//        var user by remember { mutableStateOf(auth.currentUser) }
//        LaunchedEffect(showSplashScreen) {
//            delay(2000)
//            showSplashScreen = false
//        }
//        Crossfade(targetState = showSplashScreen, label = "") { isSplashScreenVisible ->
//            if (isSplashScreenVisible) {
//                SplashScreen {
//                    showSplashScreen = false
//                }
//            } else {
//                OrderFoodApp(user!!)
//            }
//        }
//    }
//
//    @Composable
//    fun SplashScreen(navigateToAuthOrMainScreen: () -> Unit) {
//        var rotationState by remember { mutableFloatStateOf(0f) }
//
//        LaunchedEffect(true) {
//            delay(2000)
//            navigateToAuthOrMainScreen()
//        }
//
//        LaunchedEffect(rotationState) {
//            while (true) {
//                delay(16) // Adjust the delay to control the rotation speed
//                rotationState += 1f
//            }
//        }
//        val scale by animateFloatAsState(
//            targetValue = 1f,
//            animationSpec = TweenSpec(durationMillis = 500), label = ""
//        )
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.logo),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(150.dp)
//                    .clip(CircleShape)
//                    .scale(scale)
//                    .rotate(rotationState) // Apply the rotation effect
//            )
//        }
//    }
//
////    @Composable
////    fun AuthOrMainScreen(auth: FirebaseAuth) {
////        val navController: NavHostController = rememberNavController()
////        var user by remember { mutableStateOf(auth.currentUser) }
////
////        if (user == null) {
////            AuthScreen(
//////                onSignedIn = { signedInUser ->
//////                    user = signedInUser
//////                }
////                onSignedIn = { signedInUser ->
////                    user = signedInUser
////                    navController.navigate("Home")
////                    },
////                    navController = navController
////            )
////        } else {
////            OrderFoodApp(user!!)
////
////        }
////    }
//}