package com.example.seagull_project

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.103:8080/"  // Замените на ваш IP

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())  // можно добавить логирование для запросов
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
