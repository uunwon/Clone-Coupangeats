package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName

data class ResultStoreImage(
    @SerializedName("id") val id : Int,
    @SerializedName("imgUrl") val imgUrl : String,
    @SerializedName("isForMain") val isForMain : Int,
    @SerializedName("restaurantId") val restaurantId: Int
)
