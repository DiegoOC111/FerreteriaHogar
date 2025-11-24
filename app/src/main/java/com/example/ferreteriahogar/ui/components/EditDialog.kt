package com.example.ferreteriahogar.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditDialog(
    username: String,
    role: String,
    password: String,
    isNewUser: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (username: String, password: String, role: String) -> Unit
) {
    var editedUsername by remember { mutableStateOf(username) }
    var editedRole by remember { mutableStateOf(role) }
    var editedPassword by remember { mutableStateOf(password) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(if (isNewUser) "Agregar Usuario" else "Editar Usuario") },
        text = {
            Column {
                OutlinedTextField(
                    value = editedUsername,
                    onValueChange = { editedUsername = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                OutlinedTextField(
                    value = editedRole,
                    onValueChange = { editedRole = it },
                    label = { Text("Role") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                if (isNewUser) {
                    OutlinedTextField(
                        value = editedPassword,
                        onValueChange = { editedPassword = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    )
                } else {
                    Text("Password: ****** (no editable)", modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(editedUsername, editedPassword, editedRole) }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}