package com.example.ferreteriahogar.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ferreteriahogar.data.ProductAD
import com.example.ferreteriahogar.ui.components.NavBar
import com.example.ferreteriahogar.ui.components.ProductADDialog
import com.example.ferreteriahogar.ui.components.ProductsTable
import com.example.ferreteriahogar.utils.RetrofitClient
import kotlinx.coroutines.launch
import com.example.ferreteriahogar.R
@Composable
fun ProductADScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    context: Context
) {
    val api = RetrofitClient.create(context)
    val scope = rememberCoroutineScope()
    val titleNavBar = ""
    var products by remember { mutableStateOf(listOf<ProductAD>()) }
    var editingProduct by remember { mutableStateOf<ProductAD?>(null) }
    var isNewProduct by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LoadingOverlay(isLoading = isLoading) {

        // Cargar productos
        LaunchedEffect(Unit) {
            try {
                isLoading = true
                products = api.getProducts()  // ajustar a tu endpoint real
            } catch (_: Exception) {
                errorMessage = "Error al cargar productos"
            }
            isLoading = false
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            colorResource(id = R.color.VERD_FONDO)
                        )
                    )
                )
        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                NavBar(navController, titleNavBar)

                ProductsTable(
                    products = products,
                    onEdit = { p ->
                        editingProduct = p
                        isNewProduct = false
                    },
                    onDelete = { p ->
                        scope.launch {
                            try {
                                isLoading = true
                                api.deleteProduct(p.code)  // ahora eliminar por CODE
                                products = products.filter { it.code != p.code }
                            } catch (_: Exception) {
                                errorMessage = "Error al eliminar producto"
                            }
                            isLoading = false
                        }
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        editingProduct = ProductAD()
                        isNewProduct = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.VERD_FUER)
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agregar +")
                }
            }

            // ------------ DIALOGO ------------
            editingProduct?.let { product ->

                ProductADDialog(
                    code = product.code,
                    name = product.name,
                    description = product.description,
                    isNew = isNewProduct,
                    onDismiss = { editingProduct = null },
                    onConfirm = { newCode, newName, newDescription ->

                        // cerrar diálogo inmediatamente (igual que Usuarios)
                        editingProduct = null

                        scope.launch {
                            try {
                                isLoading = true

                                if (isNewProduct) {
                                    api.createProduct(
                                        ProductAD(
                                            code = newCode,
                                            name = newName,
                                            description = newDescription
                                        )
                                    )
                                } else {
                                    api.updateProduct(
                                        ProductAD(
                                            code = newCode,
                                            name = newName,
                                            description = newDescription
                                        )
                                    )
                                }

                                products = api.getProducts()

                            } catch (_: Exception) {
                                errorMessage = "Error al guardar"
                            }
                            isLoading = false
                        }
                    }
                )
            }
        }
    }
}