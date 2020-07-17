package com.michaeludjiawan.githubfinder.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.michaeludjiawan.githubfinder.BuildConfig
import com.michaeludjiawan.githubfinder.data.api.ApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { createOkHttpClient() }
    single { createGson() }

    single { createWebService<ApiService>(get(), get()) }
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .build()
}

fun createGson(): Gson {
    return GsonBuilder()
        .serializeNulls()
        .create()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, gson: Gson): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    return retrofit.create(T::class.java)
}
