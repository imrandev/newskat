package com.imudev.newskat.model.headline

data class HeadLine(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)