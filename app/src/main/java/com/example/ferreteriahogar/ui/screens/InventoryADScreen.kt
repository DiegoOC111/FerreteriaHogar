package com.example.ferreteriahogar.ui.screens


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ferreteriahogar.R
import com.example.ferreteriahogar.data.Inventory
import com.example.ferreteriahogar.data.InventoryAD
import com.example.ferreteriahogar.data.toInventory
import com.example.ferreteriahogar.ui.components.InventoryADDialog
import com.example.ferreteriahogar.ui.components.InventoryTable
import com.example.ferreteriahogar.ui.components.NavBar
import com.example.ferreteriahogar.utils.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun AdminInventariosScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    context: Context
) {
    val api = RetrofitClient.create(context)
    val scope = rememberCoroutineScope()
    val titleNavBar = ""

    var inventories by remember { mutableStateOf(listOf<Inventory>()) }
    var editing by remember { mutableStateOf<Inventory?>(null) }
    var isNew by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // cargar inventarios
    LaunchedEffect(Unit) {
        try {
            isLoading = true
            inventories = api.getInventories()
        } catch (e: Exception) {
            errorMessage = "Error al cargar inventarios"
        }
        isLoading = false
    }

    LoadingOverlay(isLoading = isLoading) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.White,
                            colorResource(id = R.color.VERD_FONDO)
                        )
                    )
                )
        ) {

            Column(Modifier.fillMaxSize()) {

                NavBar(navController, titleNavBar)

                InventoryTable(
                    inventories = inventories,
                    onEdit = {
                        editing = it
                        isNew = false
                    },
                    onDelete = { inv ->
                        scope.launch {
                            try {
                                isLoading = true
                                api.deleteInventory(inv.code)
                                inventories = inventories.filter { i -> i.code != inv.code }
                            } catch (e: Exception) {
                                errorMessage = "Error al eliminar"
                            }
                            isLoading = false
                        }
                    },
                    onAdd = {
                        editing = null
                        isNew = true
                    }
                )

                Spacer(Modifier.weight(1f))

                // Botón agregar
                Button(
                    onClick = {
                        editing = null
                        isNew = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.VERD_FUER)
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Agregar +")
                }
            }

            // DIALOGO
            if (isNew || editing != null) {

                InventoryADDialog(
                    initial = editing?.let {
                        InventoryAD(
                            code = it.code,
                            name = it.name,
                            status = it.status,
                            id = it.user.id
                        )
                    } ?: InventoryAD(),
                    onDismiss = { editing = null; isNew = false },
                    onConfirm = { dto ->

                        editing = null
                        isNew = false

                        scope.launch {
                            try {
                                isLoading = true

                                val inventory = dto

                                if (isNew) {
                                    api.createInventory(inventory)
                                } else {
                                    api.updateInventory(inventory)
                                }

                                inventories = api.getInventories()
                            } catch (e: Exception) {
                                errorMessage = "Error al guardar: " + e.message
                            }
                            isLoading = false
                        }
                    }
                )
            }
        }
    }
}