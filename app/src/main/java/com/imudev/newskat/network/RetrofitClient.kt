package com.imudev.newskat.network

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


object RetrofitClient {

    private var retrofit: Retrofit? = null
    private val TAG = "RetrofitClient"

    fun getInstance(baseURL: String, context: Context): ApiRepository? {
        if (retrofit == null) {
            retrofit = provideRetrofit(
                provideGson(),
                provideOkHttpClient(provideCache(context), context),
                baseURL
            )
        } else if (!retrofit?.baseUrl()?.equals(baseURL)!!) {
            retrofit = provideRetrofit(
                provideGson(),
                provideOkHttpClient(provideCache(context), context),
                baseURL
            )
        }
        return retrofit!!.create(ApiRepository::class.java)
    }

    private fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    private fun provideOkHttpClient(cache: Cache?, context: Context): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(OfflineCacheInterceptor(context))
        httpClient.cache(cache)
        return httpClient.build()
    }

    private fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    private fun provideCache(context: Context): Cache? {
        var cache: Cache? = null
        try {
            cache = Cache(File(context.getCacheDir(), "http-cache"), 10 * 1024 * 1024) // 10 MB
        } catch (e: Exception) {
            Log.e(TAG, "Could not create Cache!")
        }
        return cache
    }
}