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
import com.example.ferreteriahogar.data.Inventory
import com.example.ferreteriahogar.ui.components.BackIconButton
import com.example.ferreteriahogar.ui.components.InventorySelect
import com.example.ferreteriahogar.ui.components.NavBar
import com.example.ferreteriahogar.ui.theme.VERD_FUER
import com.example.ferreteriahogar.ui.theme.VERD_MEDIO

@Composable
fun Inventory(paddingValues: PaddingValues, navController: NavController){
    val titleNavBar = "Inventario"
    var selectedInventory by remember { mutableStateOf<Inventory?>(null) }

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
            modifier = Modifier.padding(end=240.dp, bottom = 6.dp)
        )

        InventorySelect(selectedInventory = selectedInventory, onInventorySelected = {selectedInventory = it})

        Spacer(Modifier.height(40.dp))

        Text(
            text = "Detalles del Inventario",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            ),
            modifier = Modifier.padding(end=134.dp, bottom = 6.dp)
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
        ){
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
            ){
                if (selectedInventory != null) {
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
                                text = "${selectedInventory!!.codigo}",
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
                                text = "${selectedInventory!!.nombre}",
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
                                text = "${selectedInventory!!.funcionario}",
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
                                val colorEstado = if (selectedInventory?.activo == true)
                                    Color(0xFF4CAF50)
                                else
                                    Color(0xFFF44336)

                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(colorEstado, shape = CircleShape)
                                        .border(1.dp, Color.DarkGray.copy(alpha = 0.4f), CircleShape)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = if (selectedInventory?.activo == true) "Activo" else "Inactivo",
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.W500,
                                        color = Color.DarkGray
                                    )
                                )
                            }
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

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
                .height(52.dp),
            shape = RoundedCornerShape(25.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            ),
            colors = ButtonDefaults.buttonColors(
                VERD_FUER,
                Color(0xFF3C3A3A)
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

@Preview(showBackground = true)
@Composable
fun PreviewInventory() {
    Inventory(
        paddingValues = PaddingValues(),
        navController = rememberNavController()
    )
}
