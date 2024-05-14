package com.example.fastshippinglogin.View

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fastshippinglogin.R
import com.example.fastshippinglogin.ui.theme.LightPrimaryColor
import com.example.fastshippinglogin.ui.theme.PrimaryColor
import com.example.fastshippinglogin.ui.theme.Purple700
import com.example.fastshippinglogin.viewmodel.OrderViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.fastshippinglogin.Model.Order


enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Flavor(title = R.string.setting),
    Pickup(title = R.string.privacy_policy),
    Summary(title = R.string.language),
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {

    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.contact)
                    )
                }
            }
        }
    )
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListScreen(viewModel: OrderViewModel = viewModel()) {
    val orders by viewModel.orders.observeAsState(emptyList())

    Scaffold(
        content = {
            LazyColumn(
            ){
                items(orders) { order ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        //elevation = 8.dp
                    ){
                        androidx.compose.material.Text(
                            text = order.toString(),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* navigate to add order screen */ }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Order")
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrderScreen(viewModel: OrderViewModel = viewModel()) {
    var order by remember { mutableStateOf(Order()) }

    Scaffold(
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 100.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = order.name,
                        onValueChange = { order = order.copy(name = it) },
                        label = { androidx.compose.material.Text("Order Name") }
                    )

                    // Add more fields as needed
                    Button(onClick = { viewModel.addOrder(order) }) {
                        androidx.compose.material.Text("Submit")
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewOder() {

}