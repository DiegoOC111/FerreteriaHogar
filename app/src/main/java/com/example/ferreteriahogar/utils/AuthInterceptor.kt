package com.example.ferreteriahogar.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import com.example.ferreteriahogar.utils.TokenStore

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = TokenStore.getToken(context)

        val request = if (token != null) {
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }

        return chain.proceed(request)
    }
}