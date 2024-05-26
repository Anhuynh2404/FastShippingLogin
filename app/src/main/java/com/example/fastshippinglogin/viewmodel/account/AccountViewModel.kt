package com.example.fastshippinglogin.viewmodel.account
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastshippinglogin.Model.CartItem
import com.example.fastshippinglogin.Model.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _user = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val user: StateFlow<FirebaseUser?> = _user

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private var selectedPhone: String = ""
    private var selectedAddress: String = ""
    private var selectedFirstName: String = ""
    private var selectedLastName: String = ""
    init {
        loadUser()
    }
    fun signUp(firstName: String, lastName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.let { user ->
                        val userProfile = hashMapOf(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "emailUser" to email
                        )
                        db.collection("users").document(user.uid).set(userProfile)
                            .addOnSuccessListener {
                                _user.value = user
                            }
                            .addOnFailureListener { e ->
                                _errorMessage.value = "${e.message}"
                            }
                    }
                } else {
                    _errorMessage.value = "Đăng ký thất bại"
                }
            }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.value = auth.currentUser
                } else {
                    _errorMessage.value = "Đăng nhập thất bại: ${task.exception?.message}"
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _user.value = null
    }
    fun loadUser() {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            db.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val user = document.toObject(User::class.java)
                        user?.let {
                            _currentUser.value = it
                        }
                    } else {
                        _errorMessage.value = "No such document"
                    }
                }
                .addOnFailureListener { exception ->
                    _errorMessage.value = "Error getting documents: ${exception.message}"
                }
        }
    }

    fun placeProfile(uid: String,user: User, onSuccess:()->Unit, onFailure: (Exception)->Unit){
        val userProfile = hashMapOf(
            "firstName" to user.firstName,
            "lastName" to user.lastName,
            "emailUser" to user.emailUser,
            "phone" to user.phone,
            "address" to user.address,
            "imageUrl" to user.imageUrl
        )
        db.collection("users").document(uid)
            .update(userProfile as Map<String, Any>)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun getUserById(userId: String, onSuccess: (List<User>) -> Unit, onFailure: (Exception) -> Unit){
        db.collection("users")
            .whereEqualTo("id", userId)
            .get()
            .addOnSuccessListener { documents->
                val users = documents.map {document->
                    User(
                        id = document.getString("id")!!,
                        firstName = document.getString("firstName")!!,
                        lastName = document.getString("firstName")!!,
                        emailUser = document.getString("emailUser")!! ,
                        phone = document.getString("phone")!!,
                        address  = document.getString("address")!!,
                        imageUrl = document.getString("imageUrl")

                    )
                }
                onSuccess(users)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun updateProfileInFirestore(uid: String, user: User) {
        val userProfile = hashMapOf(
            "firstName" to user.firstName,
            "lastName" to user.lastName,
            "email" to user.emailUser,
            "phone" to user.phone,
            "address" to user.address,
            "imageUrl" to user.imageUrl
        )

        FirebaseFirestore.getInstance().collection("users")
            .document(uid)
            .update(userProfile as Map<String, Any>)
            .addOnSuccessListener {
                loadUser()
            }
            .addOnFailureListener {
                // Handle failure
            }
    }
    fun changePassword(currentPassword: String, newPassword: String) {
        val user = auth.currentUser
        if (user != null) {
            val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
            user.reauthenticate(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.updatePassword(newPassword).addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            _errorMessage.value = "Error updating password: ${task.exception?.message}"
                        }
                    }
                } else {
                    _errorMessage.value = "Reauthentication failed: ${task.exception?.message}"
                }
            }
        }
    }

    fun deleteAccount() {
        val user = auth.currentUser
        user?.let {
            db.collection("users").document(it.uid).delete().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    it.delete().addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            _errorMessage.value = "Error deleting account: ${task.exception?.message}"
                        }
                    }
                } else {
                    _errorMessage.value = "Error deleting user data: ${task.exception?.message}"
                }
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
    fun setErrorMessage(message: String) {
        _errorMessage.value = message
    }

    fun getPhone(): String {
        return selectedPhone
    }
    fun getAddress(): String {
        return selectedAddress
    }
    fun getFirstname(): String {
        return selectedFirstName
    }

    fun getLastname(): String {
        return selectedLastName
    }
    fun setPhone(method: String) {
        selectedPhone = method
    }

    fun setLastname(method: String) {
        selectedLastName = method
    }

    fun setAddress(method: String) {
        selectedAddress = method
    }

    fun setFirstname(method: String) {
        selectedFirstName = method
    }

}
