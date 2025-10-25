package com.example.ferreteriahogar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ferreteriahogar.R
import com.example.ferreteriahogar.data.Globals
import com.example.ferreteriahogar.data.Inventory
import com.example.ferreteriahogar.data.inventories

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventorySelect(
    selectedInventory: Inventory?,
    onInventorySelected: (Inventory) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val inventarios = inventories

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 31.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.VERD_FUER)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ){
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedInventory?.nombre ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Seleccionar inventario...",) },
                    shape = RoundedCornerShape(20.dp),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .width(350.dp)
                        .padding(horizontal = 0.dp,),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor =  Color.Transparent,
                        unfocusedIndicatorColor =  Color.Transparent,
                        focusedContainerColor =  colorResource(id = R.color.VERD_FONDO),
                        unfocusedContainerColor =  colorResource(id = R.color.VERD_FONDO),
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                    )
                )



                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .menuAnchor()
                        .width(250.dp)
                        .background(Color(0xFFF9F9F9))
                ) {
                    inventarios.forEach { inventario ->
                        DropdownMenuItem(
                            text = { inventario.nombre?.let { Text(it) } },
                            onClick = {
                                onInventorySelected(inventario)
                                Globals.inventory = inventario
                                expanded = false
                            },

                            )
                    }
                }
            }
        }
    }




}

