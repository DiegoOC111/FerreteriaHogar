package com.example.ferreteriahogar.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ferreteriahogar.R
import com.example.ferreteriahogar.ui.Routes
import com.example.ferreteriahogar.ui.components.InventorySelect
import com.example.ferreteriahogar.ui.components.NavBar
import com.example.ferreteriahogar.ui.theme.VERD_FUER
import com.example.ferreteriahogar.viewModels.InventoryViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Inventory(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: InventoryViewModel
) {
    val context = LocalContext.current
    val titleNavBar = "Inventario"

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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            colorResource(id = R.color.VERD_FONDO)
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            NavBar(navController, titleNavBar)
            Spacer(Modifier.height(20.dp))

            Text(
                text = "Inventario",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                ),
                modifier = Modifier.padding(end = 240.dp, bottom = 6.dp)
            )

            // InventorySelect ahora recibe context
            InventorySelect(
                context = context,
                selectedInventory = viewModel.selectedInventory.value,
                onInventorySelected = {
                    viewModel.selectInventory(it)
                }
            )

            Spacer(Modifier.height(40.dp))

            Text(
                text = "Detalles del Inventario",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                ),
                modifier = Modifier.padding(end = 134.dp, bottom = 6.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFCFE0B3)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .background(
                            color = Color(0xFFCFE0B3),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(vertical = 25.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    viewModel.selectedInventory.value?.let { inv ->
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            DetailRow(label = "ID:", value = inv.code)
                            DetailRow(label = "NOMBRE:", value = inv.name)
                            DetailRow(label = "FUNCIONARIO:", value = inv.user.username)

                            // Estado con color
                            Row(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 15.dp)
                            ) {
                                val colorEstado = if (inv.status == "ACTIVE") Color(0xFF4CAF50) else Color(0xFFF44336)
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(colorEstado, shape = CircleShape)
                                        .border(1.dp, Color.DarkGray.copy(alpha = 0.4f), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (inv.status == "ACTIVE") "Activo" else "Inactivo",
                                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.W500, color = Color.DarkGray)
                                )
                            }
                            val cantidadProductos = inv.items.sumOf { it.stock }
                            DetailRow(label = "CANT. PRODUCTOS:", value = "${cantidadProductos ?: 0}")
                        }
                    } ?: run {
                        Text(
                            text = "Selecciona un inventario para ver los detalles",
                            textAlign = TextAlign.Center,
                            color = Color.DarkGray,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(90.dp))
        }

        Button(
            onClick = {
                if (viewModel.selectedInventory.value?.status == "ACTIVE") {
                    navController.navigate(Routes.Hoja_Inventario)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = VERD_FUER, contentColor = Color(0xFF3C3A3A))
        ) {
            Text(
                text = "Iniciar inventario",
                style = TextStyle(fontSize = 21.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun DetailRow(label: String, value: String?) {
    Column {
        Text(
            text = label,
            modifier = Modifier.padding(bottom = 0.dp),
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W400, color = Color.DarkGray)
        )
        Text(
            text = value ?: "-",
            modifier = Modifier.padding(bottom = 15.dp),
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.W500, color = Color.DarkGray)
        )
    }
}
