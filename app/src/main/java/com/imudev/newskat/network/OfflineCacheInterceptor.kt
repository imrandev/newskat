package com.imudev.newskat.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit


class OfflineCacheInterceptor(var context: Context?) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val cacheControl = CacheControl.Builder()
            .maxStale(5, TimeUnit.SECONDS)
            .build()
        val original: Request = chain.request()
        val request: Request = original.newBuilder()
            .cacheControl(if (hasNetwork()) cacheControl else CacheControl.FORCE_CACHE)
            .method(original.method(), original.body())
            .build()
        return chain.proceed(request)
    }

    private fun hasNetwork(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}