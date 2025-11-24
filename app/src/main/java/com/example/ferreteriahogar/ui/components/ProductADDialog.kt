package com.example.ferreteriahogar.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ferreteriahogar.data.ProductAD
@Composable
fun ProductADDialog(
    code: String,
    name: String,
    description: String,
    isNew: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (code: String, name: String, description: String) -> Unit
) {
    var editedCode by remember { mutableStateOf(code) }
    var editedName by remember { mutableStateOf(name) }
    var editedDescription by remember { mutableStateOf(description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isNew) "Nuevo Producto" else "Editar Producto") },
        text = {
            Column {

                OutlinedTextField(
                    value = editedCode,
                    onValueChange = { editedCode = it },
                    label = { Text("Código") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = editedName,
                    onValueChange = { editedName = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )

                OutlinedTextField(
                    value = editedDescription,
                    onValueChange = { editedDescription = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirm(editedCode, editedName, editedDescription)
            }) { Text("Guardar") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
