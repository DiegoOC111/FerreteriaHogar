package com.example.ferreteriahogar.data.jokes

import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApiService {
    @GET("joke/Any")
    suspend fun getJoke(
        @Query("lang") lang: String = "es",
        @Query("blacklistFlags") blacklist: String = "nsfw,sexist,explicit"
    ): JokeResponse
}