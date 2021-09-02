package com.yunwoon.coupangeatsproject.src.address.models

import com.google.gson.annotations.SerializedName

data class ResultAddress(
    @SerializedName("id") val id: Int,
    @SerializedName("location") val location: String,
    @SerializedName("locationDetail") val locationDetail: String,
    @SerializedName("category") val category: String,
    @SerializedName("userId") val userId: Int
)
