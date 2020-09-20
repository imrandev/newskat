package com.imudev.newskat.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.imudev.newskat.model.source.Media
import com.imudev.newskat.model.source.Source
import com.imudev.newskat.network.EnqueueResponse
import com.imudev.newskat.network.RetrofitClient
import com.imudev.newskat.utils.ConstantUtil

class SourceRepository {

    private var sourceMutableList : MutableLiveData<List<Source>> = MutableLiveData()

    companion object {
        var instance = SourceRepository()
        private const val TAG = "SourceRepository"
    }

    fun findSources(context: Context, category : String) : MutableLiveData<List<Source>> {
        retrieve(context, category)
        return sourceMutableList;
    }

    private fun retrieve(context: Context, category : String) {
        val map = hashMapOf<String, String>()
        map["apiKey"] = ConstantUtil.API_KEY
        map["category"] = category
        map["language"] = "en"

        RetrofitClient.getInstance(ConstantUtil.BASE_URL, context)?.findSources(map.toMap())?.enqueue(sourcesEnqueueResponse)
    }

    private val sourcesEnqueueResponse  =  object : EnqueueResponse<Media>() {

        override fun onReceived(body: Media?, message: String?) {
            if (body != null) {
                sourceMutableList.value = body.sources
                Log.d(Companion.TAG, "onReceived: " + body.sources.size)
            }
        }

        override fun onError(message: String?, code: Int) {
            Log.d(Companion.TAG, "onError: $message")
        }

        override fun onFailed(message: String?) {
            Log.d(Companion.TAG, "onFailed: $message")
        }
    }
}