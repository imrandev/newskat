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
    private lateinit var headlineBySourceMutableList : MutableLiveData<List<Article>>
    private lateinit var headlineBySourceIdMutableList : MutableLiveData<List<Article>>

    companion object {
        var instance = HeadlineRepository()
        private const val TAG = "HeadlineRepository"
    }

    fun getHeadlines(context: Context) : MutableLiveData<List<Article>> {
        retrieve(context)
        return topHeadlineMutableList;
    }

    fun getHeadlinesBySource(context: Context, source: Source) : MutableLiveData<List<Article>> {
        headlineBySourceMutableList = MutableLiveData()
        retrieve(context, source)
        return headlineBySourceMutableList
    }

    fun getHeadlinesBySourceId(context: Context, source: String) : MutableLiveData<List<Article>> {
        headlineBySourceIdMutableList = MutableLiveData()
        retrieve(context, source)
        return headlineBySourceIdMutableList
    }

    private fun retrieve(context: Context){
        val map = hashMapOf<String, String>()
        map["apiKey"] = ConstantUtil.API_KEY
        map["language"] = "en"
        // find headlines from server
        RetrofitClient.getInstance(ConstantUtil.BASE_URL, context)?.findTopHeadlines(map.toMap())?.enqueue(topHeadlineEnqueueResponse)
    }

    private fun retrieve(context: Context, source: Source){
        val map = hashMapOf<String, String>()
        map["apiKey"] = ConstantUtil.API_KEY
        map["language"] = "en"
        map["sources"] = source.id
        // find headlines by source from server
        RetrofitClient.getInstance(ConstantUtil.BASE_URL, context)?.findTopHeadlines(map.toMap())?.enqueue(headlinesBySourceEnqueueResponse)
    }

    private fun retrieve(context: Context, sourceId: String){
        val map = hashMapOf<String, String>()
        map["apiKey"] = ConstantUtil.API_KEY
        map["language"] = "en"
        map["sources"] = sourceId
        // find headlines by source id from server
        RetrofitClient.getInstance(ConstantUtil.BASE_URL, context)?.findTopHeadlines(map.toMap())?.enqueue(headlinesBySourceIdEnqueueResponse)
    }

    private val topHeadlineEnqueueResponse  =  object : EnqueueResponse<HeadLine>() {

        override fun onReceived(body: HeadLine?, message: String?) {
            if (body != null) {
                topHeadlineMutableList.postValue(body.articles)
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

    private val headlinesBySourceEnqueueResponse  =  object : EnqueueResponse<HeadLine>() {

        override fun onReceived(body: HeadLine?, message: String?) {
            if (body != null) {
                headlineBySourceMutableList.postValue(body.articles)
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

    private val headlinesBySourceIdEnqueueResponse  =  object : EnqueueResponse<HeadLine>() {

        override fun onReceived(body: HeadLine?, message: String?) {
            if (body != null) {
                headlineBySourceIdMutableList.postValue(body.articles)
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