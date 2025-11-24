package com.example.ferreteriahogar.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object TokenStore {

    private const val FILE_NAME = "secure_token"
    private const val TOKEN_KEY = "jwt_token"

    // Crear MasterKey seguro (reemplaza MasterKeys)
    private fun getMasterKey(context: Context): MasterKey {
        return MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private fun prefs(context: Context) =
        EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            getMasterKey(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun saveToken(context: Context, token: String) {
        prefs(context).edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(context: Context): String? =
        prefs(context).getString(TOKEN_KEY, null)

    fun clear(context: Context) {
        prefs(context).edit().clear().apply()
    }
}
