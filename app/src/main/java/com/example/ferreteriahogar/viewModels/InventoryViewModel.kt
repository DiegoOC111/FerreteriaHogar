package com.example.ferreteriahogar.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ferreteriahogar.data.Detalle_Hoja
import com.example.ferreteriahogar.data.Inventory

class InventoryViewModel : ViewModel() {

    var selectedInventory = mutableStateOf<Inventory?>(null)
        private set
    val detallesTemp = mutableStateListOf<Detalle_Hoja>()

    val detalles = mutableStateListOf<Detalle_Hoja>()

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

    fun applyChanges() {
        detallesTemp.forEach { temp ->
            val index = detalles.indexOfFirst { it.codigo == temp.codigo }
            if (index != -1) {
                val old = detalles[index]
                detalles[index] = old.copy(cantidad = old.cantidad + temp.cantidad)
            } else {
                detalles.add(temp)
            }
        }

        updateCount()
        detallesTemp.clear()
    }

    private fun updateCount() {
        selectedInventory.value?.cantProd = detalles.sumOf { it.cantidad }
    }
}
