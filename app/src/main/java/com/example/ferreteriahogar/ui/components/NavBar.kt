package com.example.ferreteriahogar.ui.components



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
import androidx.navigation.NavController
import com.example.ferreteriahogar.ui.Routes

@Composable
fun OverflowMenu(
    isExpanded : Boolean,
    onItemClick :(String)-> Unit,
    onDismiss :()->Unit
){
    val opciones=listOf("Popeame un chiste","Cerrar Sesion")
    DropdownMenu(expanded = isExpanded, onDismissRequest = onDismiss, containerColor = Color.White) {
        opciones.forEach {opcion->
            DropdownMenuItem(text={
                Text(text=opcion,)
            }, onClick = {
                onItemClick(opcion)
                onDismiss()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(navController: NavController,
           titleNavBar : String){
    var isMenuOpened by remember { mutableStateOf(false) }
    TopAppBar(
        modifier = Modifier.padding(horizontal = 10.dp),

        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
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
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        },



        actions = {
            IconButton(onClick = { isMenuOpened = true }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Opciones del menú",
                    tint = Color.DarkGray
                )
            }

            OverflowMenu(
                isExpanded = isMenuOpened,
                onItemClick = { opcion ->
                    when (opcion) {
                        "Popeame un chiste" -> println()
                        "Cerrar Sesion" -> navController.navigate(Routes.Login)
                    }
                    isMenuOpened = false
                },
                onDismiss = { isMenuOpened = false }
            )
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black,
            navigationIconContentColor = Color.Black
        )
    )
}