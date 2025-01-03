package com.example.seagull_project

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

object RetrofitClient {

    private const val BASE_URL = "http://192.168.0.103:8080/"  // Замените на ваш IP

    // Настройка логирования запросов и ответов
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY  // Логирование тела запроса и ответа
    }

    // Настройка OkHttp клиента с логированием
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)  // Добавляем логирование
        .build()

    // Создание Retrofit экземпляра с добавлением lenient Gson конвертера
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()  // Включаем lenient режим для Gson
                    .create()
            )
        )
        .client(client)  // Используем настроенный клиент с логированием
        .build()

    // Создание API-сервиса, с которым мы будем работать
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}

