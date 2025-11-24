package com.example.ferreteriahogar.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.ferreteriahogar.data.InventoryAD
import com.example.ferreteriahogar.data.ProductAD
import com.example.ferreteriahogar.R

@Composable
fun InventoryADDialog(
    initial: InventoryAD,
    onDismiss: () -> Unit,
    onConfirm: (InventoryAD) -> Unit
) {
    var code by remember { mutableStateOf(initial.code) }
    var name by remember { mutableStateOf(initial.name) }
    var status by remember { mutableStateOf(initial.status) }
    var idUser by remember { mutableStateOf(initial.id?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Inventario") },
        text = {
            Column {

                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Código") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = status,
                    onValueChange = { status = it },
                    label = { Text("Estado") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = idUser,
                    onValueChange = { idUser = it },
                    label = { Text("ID Usuario a cargo") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        InventoryAD(
                            code = code,
                            name = name,
                            status = status,
                            id = idUser.toLongOrNull()
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.VERD_FUER)
                )
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}