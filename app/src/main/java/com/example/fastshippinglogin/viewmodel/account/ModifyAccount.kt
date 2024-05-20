package com.example.fastshippinglogin.viewmodel.account

import android.content.Context
import android.net.Uri
import com.example.fastshippinglogin.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.ColumnScope
import com.google.firebase.ktx.Firebase
import java.util.UUID


private val auth = FirebaseAuth.getInstance()
private val firestore = FirebaseFirestore.getInstance()
//fun uploadImageToFirebaseStorage(uri: Uri, onSuccess: (String) -> Unit) {
//    val storageReference = FirebaseStorage.getInstance().reference
//    val imageReference = storageReference.child("images/${UUID.randomUUID()}.jpg")
//
//    imageReference.putFile(uri)
//        .addOnSuccessListener {
//            imageReference.downloadUrl.addOnSuccessListener { uri ->
//                onSuccess(uri.toString())
//            }
//        }
//        .addOnFailureListener {
//            // Handle failure
//        }
//}
fun updateProfileInFirestore(uid: String, user: User) {
    val userProfile = hashMapOf(
        "firstName" to user.firstName,
        "lastName" to user.lastName,
        "email" to user.emailUser,
        "phone" to user.phone,
        "address" to user.address,
        "imageUrl" to user.imageUrl
    )

    firestore.collection("users")
        .document(uid)
        .update(userProfile as Map<String, Any>)
        .addOnSuccessListener {

        }
        .addOnFailureListener {
            // Handle failure
        }
}