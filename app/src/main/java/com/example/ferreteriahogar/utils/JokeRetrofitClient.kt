package com.example.ferreteriahogar.utils

import com.example.ferreteriahogar.data.jokes.JokeApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JokeRetrofitClient {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: JokeApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://v2.jokeapi.dev/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JokeApiService::class.java)
    }

}