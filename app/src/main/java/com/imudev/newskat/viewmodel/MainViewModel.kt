package com.imudev.newskat.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imudev.newskat.model.Article
import com.imudev.newskat.repository.HeadlineRepository

class MainViewModel : ViewModel() {

    private lateinit var headlines : MutableLiveData<List<Article>>

    fun init(context: Context){
        val headlineRepository = HeadlineRepository.instance
        headlines = headlineRepository.getHeadlines(context)
    }

    fun getHeadlines() : LiveData<List<Article>> {
        return headlines
    }
}