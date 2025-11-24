package com.example.ferreteriahogar.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.ferreteriahogar.data.Inventory
import com.example.ferreteriahogar.data.InventoryAD
import com.example.ferreteriahogar.R
@Composable
fun InventoryTable(
    inventories: List<Inventory>,
    onEdit: (Inventory) -> Unit,
    onDelete: (Inventory) -> Unit,
    onAdd: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {

        // Botón agregar arriba (igual que UsersTable)
        Button(
            onClick = onAdd,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.VERD_FUER)
            )
        ) {
            Text("Agregar +")
        }

        inventories.forEach { inv ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(Modifier.padding(16.dp)) {

                    Text("Código: ${inv.code}")
                    Text("Nombre: ${inv.name}")
                    Text("Estado: ${inv.status}")
                    Text("Usuario a cargo: ${inv.user.username}")

                    Divider(Modifier.padding(vertical = 8.dp))

                    Row {

                        Button(
                            onClick = { onEdit(inv) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.VERD_FUER)
                            )
                        ) {
                            Text("Editar")
                        }

                        Spacer(Modifier.width(10.dp))

                        Button(
                            onClick = { onDelete(inv) },
                            colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}