package com.imudev.newskat.model.headline

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("@type")
    val type: String,
    val jobTitle: String,
    val name: String,
    val url: String
)