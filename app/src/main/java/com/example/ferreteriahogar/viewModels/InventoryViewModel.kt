package com.example.ferreteriahogar.viewModels

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ferreteriahogar.data.Detalle_Hoja
import com.example.ferreteriahogar.data.Inventory
import com.example.ferreteriahogar.data.InventoryProduct
import com.example.ferreteriahogar.data.InventoryProductId
import com.example.ferreteriahogar.utils.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
class InventoryViewModel : ViewModel() {

    var selectedInventory = mutableStateOf<Inventory?>(null)
        private set

    val detallesTemp = mutableStateListOf<Detalle_Hoja>()  // Productos temporales
    val detalles = mutableStateListOf<Detalle_Hoja>()      // Productos confirmados en inventario

    fun selectInventory(inventory: Inventory) {
        selectedInventory.value = inventory
        detalles.clear()
    }

    fun addProduct(detalle: Detalle_Hoja) {
        val index = detallesTemp.indexOfFirst { it.codigo == detalle.codigo }
        if (index != -1) {
            val old = detallesTemp[index]
            detallesTemp[index] = old.copy(cantidad = old.cantidad + detalle.cantidad)
        } else {
            detallesTemp.add(detalle)
        }
    }

    fun updateCantidadTemp(index: Int, cantidad: Int) {
        detallesTemp[index] = detallesTemp[index].copy(cantidad = cantidad)
    }

    fun removeTemp(index: Int) {
        detallesTemp.removeAt(index)
    }

    /**
     * Aplica los cambios al inventario y prepara los objetos InventoryProduct para enviar a la API
     */
    fun applyChanges(context: Context) {
        selectedInventory.value?.let { inventory ->
            val productsToSend = detallesTemp.map { detalle ->
                InventoryProduct(
                    id = InventoryProductId(
                        inventoryCode = inventory.code,
                        productCode = detalle.codigo
                    ),
                    stock = detalle.cantidad,
                    minStock = 1
                )
            }

            // Actualizar lista local
            detallesTemp.forEach { temp ->
                val index = detalles.indexOfFirst { it.codigo == temp.codigo }
                if (index != -1) {
                    val old = detalles[index]
                    detalles[index] = old.copy(cantidad = old.cantidad + temp.cantidad)
                } else {
                    detalles.add(temp)
                }
            }
            detallesTemp.clear()

            // Llamada a la API
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val api = RetrofitClient.create(context)
                    val inv = selectedInventory.value// Aquí obtienes tu ApiService
                    productsToSend.forEach { product ->
                        api.addInventoryProductByScan(inv?.code ?: "", productCode = product.id.productCode,product.stock)
                    }
                    println("Productos enviados correctamente al inventario ${inventory.code}")
                } catch (e: Exception) {
                    e.printStackTrace()
                    println("Error al enviar productos: ${e.message}")
                }
            }
        }
    }
}
