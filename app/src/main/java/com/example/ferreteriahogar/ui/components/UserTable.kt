package com.example.ferreteriahogar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ferreteriahogar.data.UserAD

@Composable
fun UsersTable(
    users: List<UserAD>,
    onEdit: (UserAD) -> Unit,
    onDelete: (UserAD) -> Unit,
    onAdd: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text("Usuario", Modifier.weight(1f))
            Text("Rol", Modifier.weight(1f))
            Text("Acciones", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Filas
        users.forEach { user ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .background(Color(0xFFF5F5F5))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(user.username, Modifier.weight(1f))
                Text(user.role, Modifier.weight(1f))
                Row(
                    Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = { onEdit(user) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { onDelete(user) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón de agregar

    }
}