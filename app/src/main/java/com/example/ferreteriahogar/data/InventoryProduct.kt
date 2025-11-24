package com.example.ferreteriahogar.data

data class InventoryProduct(
    val id: InventoryProductId,
    val stock: Int,
    val minStock: Int
)
