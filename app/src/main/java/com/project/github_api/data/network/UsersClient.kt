package com.project.github_api.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UsersClient {
    private const val BASE_URL = "https://api.github.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstance = retrofit.create(UsersService::class.java)
}