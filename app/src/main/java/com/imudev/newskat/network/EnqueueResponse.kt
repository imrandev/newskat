package com.imudev.newskat.network

import android.util.Log
import com.imudev.newskat.utils.ConstantUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class EnqueueResponse<T> : Callback<T?> {
    private val TAG = "EnqueueResponse"

    override fun onResponse(call: Call<T?>, response: Response<T?>) {
        val code: Int = response.code()
        val message: String = response.raw().message()

        val result: T? = response.body()
        if (response.isSuccessful() && result != null) {
            onReceived(result, message)
        } else {
            onError(message, code)
        }
        Log.e(TAG, "onResponse: $message")
    }

    override fun onFailure(call: Call<T?>, t: Throwable) {
        onFailed(t.message)
        Log.e(TAG, "onFailure: " + t.message)
    }

    abstract fun onReceived(body: T?, message: String?)
    abstract fun onError(message: String?, code: Int)
    abstract fun onFailed(message: String?)
}