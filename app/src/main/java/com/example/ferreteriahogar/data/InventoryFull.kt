package com.example.ferreteriahogar.data

data class InventoryFull(
    val code: String,
    val name: String,
    val description: String?,
    val products: List<Product>
)
