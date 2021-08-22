package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName

data class ResultStore(
    @SerializedName("restaurantResult") val restaurantResult : ArrayList<ResultStoreRestaurant>,
    @SerializedName("imgResult") val imgResult : ArrayList<ResultStoreImage>
)