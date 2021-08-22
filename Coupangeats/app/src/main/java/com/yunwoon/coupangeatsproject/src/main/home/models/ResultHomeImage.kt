package com.yunwoon.coupangeatsproject.src.main.home.models

import com.google.gson.annotations.SerializedName

data class ResultHomeImage(
    @SerializedName("id") val id: Int,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("isForMain") val isForMain: Int,
    @SerializedName("restaurantId") val restaurantId: Int,
)
