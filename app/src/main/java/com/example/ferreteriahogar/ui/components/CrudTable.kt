package com.example.ferreteriahogar.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CrudTable(
    title: String,
    items: List<Map<String, String>>,
    onEdit: (Map<String, String>) -> Unit,
    onDelete: (Map<String, String>) -> Unit,
    onAdd: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = title, modifier = Modifier.padding(bottom = 12.dp))

        LazyColumn {
            items(items) { item ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item.values.joinToString(" | "))

                    Row {
                        Button(onClick = { onEdit(item) }, modifier = Modifier.padding(end = 4.dp)) {
                            Text("Editar")
                        }
                        Button(onClick = { onDelete(item) }) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onAdd() }) {
            Text("+ Agregar")
        }
    }
}