package com.imudev.newskat.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.jetbrains.annotations.NotNull
import java.io.IOException
import java.util.concurrent.TimeUnit

class OfflineCacheInterceptor(var context: Context?) : Interceptor {
    
    companion object{
        private const val TAG = "OfflineCacheInterceptor"
    }

    @NotNull
    override fun intercept(chain: Interceptor.Chain): Response {
        val cacheControl : CacheControl
        if (hasNetwork()){
            cacheControl = CacheControl.Builder()
                .maxStale(5, TimeUnit.SECONDS)
                .build()
        } else {
            cacheControl = CacheControl.FORCE_CACHE
        }
        val original: Request = chain.request()
        val request: Request = original.newBuilder()
            .cacheControl(cacheControl)
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