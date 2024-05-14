package com.example.fastshippinglogin.View.settingview.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.fastshippinglogin.Model.User
import com.example.fastshippinglogin.R
import com.example.fastshippinglogin.Model.UserProfile
import com.example.fastshippinglogin.viewmodel.signUp

@Composable
fun EditProfileScreen(
    navController: NavHostController = rememberNavController(),
    user: User,
    onProfileUpdated: (User) -> Unit,
) {
    var editedUser by remember { mutableStateOf(user) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }

    val imageModifier = Modifier
        .size(100.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colors.primaryVariant)

    val textFieldModifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onProfileUpdated(editedUser)
                        navController.navigate("SaveAccount")
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Button(
                        onClick = {
                            launcher.launch("image/*")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Pick Image")
                    }
                }

                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        imageUri?.let { uri ->
                            Image(
                                painter = rememberImagePainter(uri),
                                contentDescription = null,
                                modifier = imageModifier
                            )
                        } ?: Image(
                            painter = rememberImagePainter(R.drawable.ic_launcher_background),
                            contentDescription = null,
                            modifier = imageModifier
                        )
                    }
                }

                item { Divider() }

                item {
                    TextField(
                        value = editedUser.firstName.orEmpty(),
                        onValueChange = { editedUser = editedUser.copy(firstName = it) },
                        label = { Text("First Name") },
                        modifier = textFieldModifier
                    )
                }

                item {
                    TextField(
                        value = editedUser.lastName.orEmpty(),
                        onValueChange = { editedUser = editedUser.copy(lastName = it) },
                        label = { Text("Last Name") },
                        modifier = textFieldModifier
                    )
                }

                item {
                    TextField(
                        value = editedUser.email,
                        onValueChange = { editedUser = editedUser.copy(email = it) },
                        label = { Text("Email") },
                        readOnly = true,
                        modifier = textFieldModifier
                    )
                }

                item {
                    TextField(
                        value = editedUser.phone.orEmpty(),
                        onValueChange = { editedUser = editedUser.copy(phone = it) },
                        label = { Text("Phone") },
                        modifier = textFieldModifier
                    )
                }

                item {
                    TextField(
                        value = editedUser.address.orEmpty(),
                        onValueChange = { editedUser = editedUser.copy(address = it) },
                        label = { Text("Address") },
                        modifier = textFieldModifier
                    )
                }

                item { Divider() }
            }
        }
    )
}



