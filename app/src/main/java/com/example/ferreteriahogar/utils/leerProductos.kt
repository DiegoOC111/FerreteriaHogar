package com.example.ferreteriahogar.utils

import android.content.Context
import com.example.ferreteriahogar.data.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun leerProductos(context: Context): List<Product> {
    val jsonString = context.assets.open("Productos.json").bufferedReader().use { it.readText() }
    val listType = object : TypeToken<List<Product>>() {}.type
    return Gson().fromJson(jsonString, listType)
}
