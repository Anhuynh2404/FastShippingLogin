import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.fastshippinglogin.View.components.ImageFromUri
import com.example.fastshippinglogin.viewmodel.add.addCategoryToFirebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun AddCategoryScreen(
    //onCategoryAdded: () -> Unit,
   // onBack: () -> Unit
) {
    var categoryName by remember { mutableStateOf("") }
    var categoryImageUri by remember { mutableStateOf<Uri?>(null) }
    var isCategoryAdded by remember { mutableStateOf(false) } // Biến trạng thái để theo dõi trạng thái của công việc
    val context = LocalContext.current
    val getImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { uri ->
                categoryImageUri = uri
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = categoryName,
            onValueChange = { categoryName = it },
            label = { Text("Category Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
       // ImageFromUri(uri = categoryImageUri ?: "")
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "image/*"
                }
                getImageLauncher.launch(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (categoryName.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        addCategoryToFirebase(categoryName, categoryImageUri)
                        isCategoryAdded = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Category")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
//        LaunchedEffect(isCategoryAdded) {
//            if (isCategoryAdded) {
//                onCategoryAdded()
//            }
//        }
    }
}


