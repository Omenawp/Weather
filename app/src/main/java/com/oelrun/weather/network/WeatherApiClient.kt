package com.oelrun.weather.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object WeatherApiClient {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private const val API_KEY = "40faf9fbad5d8bbd5f92b1de957b95c4"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .setClient()
        .addJsonConverter()
        .build()

    @Suppress("EXPERIMENTAL_API_USAGE")
    private fun Retrofit.Builder.addJsonConverter() = apply {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        this.addConverterFactory(json.asConverterFactory(contentType))
    }

    private fun OkHttpClient.Builder.addApiKeyInterceptor() = apply {
        val interceptor = Interceptor { chain ->
            var request = chain.request()
            val url = request.url.newBuilder()
                .addQueryParameter("appid", API_KEY)
                .addQueryParameter("units", "metric")
                .addQueryParameter("lang", "ru")
                .build()

            request = request.newBuilder().url(url).build()

            chain.proceed(request)
        }
        this.addInterceptor(interceptor)
    }

    private fun Retrofit.Builder.setClient() = apply {
        val okHttpClient = OkHttpClient.Builder()
            .addApiKeyInterceptor()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        this.client(okHttpClient)
    }

    val service: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}