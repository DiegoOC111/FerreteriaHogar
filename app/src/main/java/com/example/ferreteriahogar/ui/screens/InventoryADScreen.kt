package com.example.ferreteriahogar.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // Importante para el scroll
import androidx.compose.foundation.verticalScroll // Importante para el scroll
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

    // Scroll state para la lista
    val scrollState = rememberScrollState()

    // Cargar inventarios al inicio
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

                // --- ZONA DE SCROLL ---
                // Usamos weight(1f) para que ocupe todo el espacio disponible
                // y verticalScroll para permitir deslizar si la tabla es muy larga.
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                ) {
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
                                    // Actualizamos la lista localmente
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

                        // CORRECCIÓN LÓGICA:
                        // 1. Guardamos el estado "es nuevo" en una variable temporal
                        val creatingNew = isNew

                        // 2. Limpiamos el estado de la UI
                        editing = null
                        isNew = false

                        scope.launch {
                            try {
                                isLoading = true
                                val inventory = dto

                                // 3. Usamos la variable temporal para decidir
                                if (creatingNew) {
                                    api.createInventory(inventory)
                                } else {
                                    api.updateInventory(inventory)
                                }

                                // Recargamos la lista
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