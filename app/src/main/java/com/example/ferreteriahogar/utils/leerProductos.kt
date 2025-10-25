package com.example.ferreteriahogar.utils

import android.content.Context
import com.example.ferreteriahogar.data.Producto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun leerProductos(context: Context): List<Producto> {
    val jsonString = context.assets.open("Productos.json").bufferedReader().use { it.readText() }
    val listType = object : TypeToken<List<Producto>>() {}.type
    return Gson().fromJson(jsonString, listType)
}
