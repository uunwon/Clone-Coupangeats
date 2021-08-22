package com.yunwoon.coupangeatsproject.src.main.home.models

import com.google.gson.annotations.SerializedName

data class ResultHome(
    @SerializedName("restaurantResult") val restaurantResult: ArrayList<ResultHomeRestaurants>,
    @SerializedName("imgResult") val imgResult : ArrayList<ResultHomeImage>
)