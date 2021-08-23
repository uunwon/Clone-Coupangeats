package com.yunwoon.coupangeatsproject.src.main.favorite.models

import com.google.gson.annotations.SerializedName

data class ResultFavoriteStore(
    @SerializedName("restaurantId") val restaurantId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("imgUrl") val imgUrl: String
)
