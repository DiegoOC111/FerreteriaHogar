package com.example.ferreteriahogar.data
data class Inventory(
    val code: String = "",
    val name: String = "",
    val status: String = "",
    val user: User,
    val items: List<InventoryProduct>
)
