package com.example.ferreteriahogar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ferreteriahogar.R
import com.example.ferreteriahogar.data.Globals
import com.example.ferreteriahogar.ui.Routes
import com.example.ferreteriahogar.ui.components.NavBar
import com.example.ferreteriahogar.ui.theme.VERD_FUER

@Composable
fun AdminScreen(
    paddingValues: PaddingValues,
    navController: NavController
) {
    val titleNavBar = ""
    val user = Globals.ActiveUser
    val userRole = user?.role ?: ""
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        colorResource(id = R.color.VERD_FONDO)
                    )
                )
            )
    )

    NavBar(navController, titleNavBar)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(90.dp))

        Text(
            text = "Panel Administrador",
            style = TextStyle(
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold
            )
        )

        Spacer(Modifier.height(30.dp))
        Button(
            onClick = { navController.navigate(Routes.AdminUsuarios) },
            enabled = userRole == "UADMIN" || userRole == "ADMIN",
            modifier = Modifier.fillMaxWidth().padding(horizontal = 35.dp).height(52.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = VERD_FUER)
        ) {
            Text("Administrar Usuarios", fontSize = 21.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(20.dp))

        // BOTÓN PRODUCTOS: Habilitado solo para "ADMIN"
        Button(
            onClick = { navController.navigate(Routes.AdminProductos) },
            enabled = userRole == "ADMIN",
            modifier = Modifier.fillMaxWidth().padding(horizontal = 35.dp).height(52.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = VERD_FUER)
        ) {
            Text("Administrar Productos", fontSize = 21.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(20.dp))

        // BOTÓN INVENTARIO: Habilitado para "IADMIN" y "ADMIN"
        Button(
            onClick = { navController.navigate(Routes.AdminInventariosScreen) },
            enabled = userRole == "IADMIN" || userRole == "ADMIN",
            modifier = Modifier.fillMaxWidth().padding(horizontal = 35.dp).height(52.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = VERD_FUER)
        ) {
            Text("Administrar Inventario", fontSize = 21.sp, fontWeight = FontWeight.Bold)
        }
    }
}


