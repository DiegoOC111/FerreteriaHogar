package com.example.ferreteriahogar.utils

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object JokeHelper {
    fun fetchAndNotifyJoke(context: Context) {
        CoroutineScope(Dispatchers.IO).launch @androidx.annotation.RequiresPermission(android.Manifest.permission.POST_NOTIFICATIONS) {
            try {
                val joke = JokeRetrofitClient.api.getJoke()

                val text = when (joke.type) {
                    "single" -> joke.joke ?: ""
                    "twopart" -> "${joke.setup}\n${joke.delivery}"
                    else -> "Chiste desconocido"
                }

                Notifier.showNotification(context, text)

            } catch (e: Exception) {
                Notifier.showNotification(context, "Error al obtener chiste 😢")
            }
        }
    }
}