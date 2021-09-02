package com.yunwoon.coupangeatsproject.src.main.home.models

import com.google.gson.annotations.SerializedName

data class ResultCategory(
    @SerializedName("id") val id: Int,
    @SerializedName("categoryName") val categoryName: String,
    @SerializedName("imgUrl") val imgUrl: String
)
