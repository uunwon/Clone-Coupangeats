package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName

data class ResultStoreCategory(
    @SerializedName("id") val id : Int,
    @SerializedName("categoryName") val categoryName : String,
    @SerializedName("restaurantId") val restaurantId : Int
)
