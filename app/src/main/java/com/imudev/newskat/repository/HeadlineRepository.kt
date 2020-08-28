package com.imudev.newskat.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.imudev.newskat.model.Article
import com.imudev.newskat.model.HeadLine
import com.imudev.newskat.network.EnqueueResponse
import com.imudev.newskat.network.RetrofitClient
import com.imudev.newskat.utils.ConstantUtil

class HeadlineRepository {

    private var mutableLiveData : MutableLiveData<List<Article>> = MutableLiveData()

    companion object {
        var instance = HeadlineRepository()
        private const val TAG = "HeadlineRepository"
    }

    fun getHeadlines(context: Context) : MutableLiveData<List<Article>> {
        retrieve(context)
        return mutableLiveData;
    }

    fun retrieve(context: Context){
        val map = hashMapOf<String, String>()
        map["apiKey"] = ConstantUtil.API_KEY
        map["language"] = "en"
        // set data from webservice
        RetrofitClient.getInstance(ConstantUtil.BASE_URL, context)?.findTopHeadlines(map.toMap())?.enqueue(headlineEnqueueResponse)
    }

    private val headlineEnqueueResponse  =  object : EnqueueResponse<HeadLine>() {

        override fun onReceived(body: HeadLine?, message: String?) {
            if (body != null) {
                mutableLiveData.value = body.articles
            }
        }

        override fun onError(message: String?, code: Int) {
            Log.d(TAG, "onError: $message")
        }

        override fun onFailed(message: String?) {
            Log.d(TAG, "onFailed: $message")
        }
    }
}