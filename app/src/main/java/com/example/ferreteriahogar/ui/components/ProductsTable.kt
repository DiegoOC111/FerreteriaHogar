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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ferreteriahogar.data.ProductAD
import com.example.ferreteriahogar.data.UserAD
@Composable
fun ProductsTable(
    products: List<ProductAD>,
    onEdit: (ProductAD) -> Unit,
    onDelete: (ProductAD) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

        // ----- Encabezados -----
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Código",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Nombre",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Descripcion",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Acciones",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ----- Lista de productos -----
        products.forEach { product ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(product.code, modifier = Modifier.weight(1f))
                Text(product.name, modifier = Modifier.weight(1f))
                Text(product.description, modifier = Modifier.weight(1f))
                Row(
                    Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = { onEdit(product) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { onDelete(product) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                    }
                }
            }

            Divider()
        }
    }
}