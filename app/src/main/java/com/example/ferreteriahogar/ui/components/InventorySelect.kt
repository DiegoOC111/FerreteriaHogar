package com.example.ferreteriahogar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ferreteriahogar.R
import com.example.ferreteriahogar.data.Inventory
import com.example.ferreteriahogar.utils.RetrofitClient
import kotlinx.coroutines.launch
import androidx.compose.runtime.saveable.rememberSaveable
import android.content.Context

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventorySelect(
    context: Context,
    selectedInventory: Inventory?,
    onInventorySelected: (Inventory) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var inventories by remember { mutableStateOf(listOf<Inventory>()) }
    var isLoading by remember { mutableStateOf(true) }
    var loadError by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    // Traer inventarios desde la API al iniciar
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val api = RetrofitClient.create(context)
                val response = api.getInventories() // <- Asegúrate de que tu API tenga este endpoint
                inventories = response
                loadError = null
            } catch (e: Exception) {
                loadError = "Error al cargar inventarios: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

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
            if (isLoading) {
                CircularProgressIndicator()
            } else if (loadError != null) {
                Text(text = loadError ?: "Error desconocido", color = Color.Red)
            } else {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        value = selectedInventory?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Seleccionar inventario...") },
                        shape = RoundedCornerShape(20.dp),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .width(350.dp),
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
                        inventories.forEach { inventario ->
                            DropdownMenuItem(
                                text = { Text(inventario.name ?: "") },
                                onClick = {
                                    onInventorySelected(inventario)
                                    expanded = false
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}