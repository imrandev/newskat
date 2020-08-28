package com.imudev.newskat.network

import com.imudev.newskat.model.HeadLine
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiRepository {

    @GET("/v2/top-headlines")
    fun findTopHeadlines(@QueryMap queryMap: Map<String, String>): Call<HeadLine>
}