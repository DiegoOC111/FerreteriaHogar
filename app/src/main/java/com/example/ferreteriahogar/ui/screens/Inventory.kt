package com.example.ferreteriahogar.ui.screens

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ferreteriahogar.R
import com.example.ferreteriahogar.data.Globals
import com.example.ferreteriahogar.data.Inventory
import com.example.ferreteriahogar.ui.Routes
import com.example.ferreteriahogar.ui.components.BackIconButton
import com.example.ferreteriahogar.ui.components.InventorySelect
import com.example.ferreteriahogar.ui.components.NavBar
import com.example.ferreteriahogar.ui.theme.VERD_FUER
import com.example.ferreteriahogar.ui.theme.VERD_MEDIO
import com.example.ferreteriahogar.viewModels.InventoryViewModel

@Composable
fun Inventory(paddingValues: PaddingValues, navController: NavController, viewModel: InventoryViewModel) {
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

            InventorySelect(
                selectedInventory = viewModel.selectedInventory.value,
                onInventorySelected = {
                    viewModel.selectInventory(it)
                })

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
                    if (viewModel.selectedInventory.value != null){
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {

                            Column {
                                Text(
                                    text = "ID:",
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.W400,
                                        color = Color.DarkGray
                                    )
                                )
                                Text(
                                    text = "${viewModel.selectedInventory.value!!.codigo}",
                                    modifier = Modifier.padding(bottom = 15.dp),
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.W500,
                                        color = Color.DarkGray
                                    )
                                )
                            }

                            Column {
                                Text(
                                    text = "NOMBRE:",
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.W400,
                                        color = Color.DarkGray
                                    )
                                )
                                Text(
                                    text = "${viewModel.selectedInventory.value!!.nombre}",
                                    modifier = Modifier.padding(bottom = 15.dp),
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.W500,
                                        color = Color.DarkGray
                                    )
                                )
                            }

                            Column {
                                Text(
                                    text = "FUNCIONARIO:",
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.W400,
                                        color = Color.DarkGray
                                    )
                                )
                                Text(
                                    text = "${viewModel.selectedInventory.value!!.funcionario}",
                                    modifier = Modifier.padding(bottom = 15.dp),
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.W500,
                                        color = Color.DarkGray
                                    )
                                )
                            }
                            Column {
                                Text(
                                    text = "ESTADO:",
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.W400,
                                        color = Color.DarkGray
                                    )
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(bottom = 15.dp)
                                ) {
                                    val colorEstado = if (viewModel.selectedInventory.value!!.activo == true)
                                        Color(0xFF4CAF50)
                                    else
                                        Color(0xFFF44336)

                                    Box(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .background(colorEstado, shape = CircleShape)
                                            .border(
                                                1.dp,
                                                Color.DarkGray.copy(alpha = 0.4f),
                                                CircleShape
                                            )
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = if (viewModel.selectedInventory.value!!.activo == true) "Activo" else "Inactivo",
                                        style = TextStyle(
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.W500,
                                            color = Color.DarkGray
                                        )
                                    )
                                }
                            }
                            Column {
                                Text(
                                    text = "CANT. PRODUCTOS:",
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.W400,
                                        color = Color.DarkGray
                                    )
                                )
                                Text(
                                    text = "${viewModel.selectedInventory.value?.cantProd ?: 0}",
                                    modifier = Modifier.padding(bottom = 15.dp),
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.W500,
                                        color = Color.DarkGray
                                    )
                                )

                            }


                        }
                    } else {
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
                try {
                    if (viewModel.selectedInventory.value?.activo == true) {
                        navController.navigate(Routes.Hoja_Inventario)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 0.dp)
                .height(60.dp),
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = VERD_FUER,
                contentColor = Color(0xFF3C3A3A)
            )
        ) {
            Text(
                text = "Iniciar inventario",
                style = TextStyle(fontSize = 21.sp),
                fontWeight = FontWeight.Bold
            )
        }


    }


}
