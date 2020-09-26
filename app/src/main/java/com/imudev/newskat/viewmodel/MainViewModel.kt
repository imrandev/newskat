package com.imudev.newskat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imudev.newskat.R
import com.imudev.newskat.model.headline.Article
import com.imudev.newskat.model.headline.Source
import com.imudev.newskat.repository.HeadlineRepository
import com.imudev.newskat.repository.SourceRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val headlinesApiDeferred = CompletableDeferred<MutableLiveData<List<Article>>>()
    private val sourcesApiDeferred = CompletableDeferred<MutableLiveData<List<com.imudev.newskat.model.source.Source>>>()

    suspend fun findHeadlines() : LiveData<List<Article>> {
        val headlineRepository = HeadlineRepository.instance
        viewModelScope.launch(Dispatchers.IO) {
            val headlines = headlineRepository.getHeadlines(getApplication())
            headlinesApiDeferred.complete(headlines)
        }
        return headlinesApiDeferred.await()
    }

    suspend fun findHeadlinesBySourceId(source: Source) : LiveData<List<Article>> {
        val sourceHeadlinesApiDeferred = CompletableDeferred<MutableLiveData<List<Article>>>()
        val headlineRepository = HeadlineRepository.instance
        viewModelScope.launch(Dispatchers.IO) {
            val sourceHeadlines = headlineRepository.getHeadlinesBySource(getApplication(), source)
            sourceHeadlinesApiDeferred.complete(sourceHeadlines)
        }
        return sourceHeadlinesApiDeferred.await()
    }

    suspend fun findHeadlinesBySourceId(source: String) : LiveData<List<Article>> {
        val sourceIdHeadlinesApiDeferred = CompletableDeferred<MutableLiveData<List<Article>>>()
        val headlineRepository = HeadlineRepository.instance
        viewModelScope.launch(Dispatchers.IO) {
            val sourceHeadlines = headlineRepository.getHeadlinesBySourceId(getApplication(), source)
            sourceIdHeadlinesApiDeferred.complete(sourceHeadlines)
        }
        return sourceIdHeadlinesApiDeferred.await()
    }

    suspend fun findSources(category: String) : LiveData<List<com.imudev.newskat.model.source.Source>>{
        val sourceRepository = SourceRepository.instance
        viewModelScope.launch(Dispatchers.IO) {
            val sources = sourceRepository.findSources(getApplication(), category)
            sourcesApiDeferred.complete(sources)
        }
        return sourcesApiDeferred.await()
    }

    fun getCategories (): MutableList<String> {
        return getApplication<Application>().resources.getStringArray(R.array.categories).toMutableList()
    }
}