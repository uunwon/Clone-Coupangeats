package com.yunwoon.coupangeatsproject.src.main.favorite.models

import com.google.gson.annotations.SerializedName

data class ResultFavoriteDetail (
    @SerializedName("restaurantResult") val restaurantResult: ArrayList<ResultFavoriteDetailStore>,
    @SerializedName("imgResult") val imgResult: ArrayList<ResultFavoriteDetailImage>
)