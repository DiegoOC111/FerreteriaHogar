package com.example.ferreteriahogar.data

data class InventoryAD(
    val code: String = "",
    val name: String = "",
    val status: String = "",
    val id: Long? = null
)
fun InventoryAD.toInventory(): Inventory {
    return Inventory(
        code = this.code,
        name = this.name,
        status = this.status,
        user = User(id = this.id ?: 0),
        items = emptyList() // No se editan aquí
    )
}