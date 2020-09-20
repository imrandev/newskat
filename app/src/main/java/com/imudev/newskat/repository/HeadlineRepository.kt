package com.imudev.newskat.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.imudev.newskat.model.headline.Article
import com.imudev.newskat.model.headline.HeadLine
import com.imudev.newskat.model.headline.Source
import com.imudev.newskat.network.EnqueueResponse
import com.imudev.newskat.network.RetrofitClient
import com.imudev.newskat.utils.ConstantUtil

class HeadlineRepository {

    private var topHeadlineMutableList : MutableLiveData<List<Article>> = MutableLiveData()
    private var headlineSourceMutableList : MutableLiveData<List<Article>> = MutableLiveData()

    companion object {
        var instance = HeadlineRepository()
        private const val TAG = "HeadlineRepository"
    }

    fun getHeadlines(context: Context) : MutableLiveData<List<Article>> {
        retrieve(context)
        return topHeadlineMutableList;
    }

    fun getSourceHeadlines(context: Context, source: Source) : MutableLiveData<List<Article>> {
        retrieve(context, source)
        return headlineSourceMutableList;
    }

    fun getSourceHeadlines(context: Context, source: String) : MutableLiveData<List<Article>> {
        retrieve(context, source)
        return headlineSourceMutableList;
    }

    private fun retrieve(context: Context){
        val map = hashMapOf<String, String>()
        map["apiKey"] = ConstantUtil.API_KEY
        map["language"] = "en"
        // set data from webservice
        RetrofitClient.getInstance(ConstantUtil.BASE_URL, context)?.findTopHeadlines(map.toMap())?.enqueue(topHeadlineEnqueueResponse)
    }

    private fun retrieve(context: Context, source: Source){
        val map = hashMapOf<String, String>()
        map["apiKey"] = ConstantUtil.API_KEY
        map["language"] = "en"
        map["sources"] = source.id
        // set data from webservice
        RetrofitClient.getInstance(ConstantUtil.BASE_URL, context)?.findTopHeadlines(map.toMap())?.enqueue(headlineSourceEnqueueResponse)
    }

    private fun retrieve(context: Context, source: String){
        val map = hashMapOf<String, String>()
        map["apiKey"] = ConstantUtil.API_KEY
        map["language"] = "en"
        map["sources"] = source
        // set data from webservice
        RetrofitClient.getInstance(ConstantUtil.BASE_URL, context)?.findTopHeadlines(map.toMap())?.enqueue(headlineSourceEnqueueResponse)
    }

    private val topHeadlineEnqueueResponse  =  object : EnqueueResponse<HeadLine>() {

        override fun onReceived(body: HeadLine?, message: String?) {
            if (body != null) {
                topHeadlineMutableList.value = body.articles
                Log.d(TAG, "onReceived: " + body.articles.size)
            }
        }

        override fun onError(message: String?, code: Int) {
            Log.d(TAG, "onError: $message")
        }

        override fun onFailed(message: String?) {
            Log.d(TAG, "onFailed: $message")
        }
    }

    private val headlineSourceEnqueueResponse  =  object : EnqueueResponse<HeadLine>() {

        override fun onReceived(body: HeadLine?, message: String?) {
            if (body != null) {
                headlineSourceMutableList.value = body.articles
                Log.d(TAG, "onReceived: " + body.articles.size)
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