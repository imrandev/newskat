package com.imudev.newskat.model

import com.google.gson.annotations.SerializedName

data class BaseResponse (
    @SerializedName("status") val status : String,
    @SerializedName("totalResults") val totalResults : Int,
    override val code: String,
    override val message: String
) : ErrorResponse(code, message)

abstract class ErrorResponse (
    @SerializedName("code") open val code : String,
    @SerializedName("message") open val message : String
)