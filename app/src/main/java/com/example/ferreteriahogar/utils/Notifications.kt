package com.example.ferreteriahogar.utils

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object Notifier {

    private const val CHANNEL_ID = "JOKE_CHANNEL"
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(context: Context, text: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Chistes",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentTitle("Chiste del día 😄")
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .build()

        NotificationManagerCompat.from(context)
            .notify((1000..9999).random(), notification)
    }
}