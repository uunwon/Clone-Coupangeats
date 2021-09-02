package com.yunwoon.coupangeatsproject.src.main.favorite.models

import com.google.gson.annotations.SerializedName

data class ResultFavoriteDetailImage (
        @SerializedName("id") val id: Int,
        @SerializedName("imgUrl") val imgUrl: String,
        @SerializedName("isForMain") val isForMain: String,
        @SerializedName("restaurantId") val restaurantId: String
)