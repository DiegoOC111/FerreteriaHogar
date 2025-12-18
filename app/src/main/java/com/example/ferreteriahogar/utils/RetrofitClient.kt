package com.example.ferreteriahogar.utils


import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    fun create(context: Context): ApiService {

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            //.baseUrl("http://192.168.1.160:8081/")
            .baseUrl("http://ec2-50-17-165-201.compute-1.amazonaws.com:8081/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}