package com.example.ferreteriahogar.data.jokes

data class JokeResponse (
    val error: Boolean,
    val category: String,
    val type: String,
    val joke: String?,
    val setup: String?,
    val delivery: String?,
    val safe: Boolean,
    val lang: String
)