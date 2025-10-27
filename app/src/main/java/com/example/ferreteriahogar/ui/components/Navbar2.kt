package com.example.ferreteriahogar.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ferreteriahogar.ui.Routes
import com.example.ferreteriahogar.viewModels.InventoryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar2(
    navController: NavController,
    titleNavBar : String,
    onSendClick: () -> Unit,
    viewModel: InventoryViewModel
    ){

    BackHandler {
        if (viewModel.detallesTemp.isNotEmpty()) {
            viewModel.detallesTemp.clear()
        }
        navController.popBackStack()
    }

    TopAppBar(
        modifier = Modifier.padding(horizontal = 10.dp),

        navigationIcon = {
            IconButton(onClick = {
                viewModel.detallesTemp.clear()
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(28.dp)
                )
            }
        },

        title = {
            Text(
                text = titleNavBar,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().
                padding(start = 26.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        },

        actions = {

            TextButton(onClick = onSendClick) {
                Text(
                    text = "Enviar",
                    color = Color(0xFF007AFF),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black,
            navigationIconContentColor = Color.Black
        )
    )
}