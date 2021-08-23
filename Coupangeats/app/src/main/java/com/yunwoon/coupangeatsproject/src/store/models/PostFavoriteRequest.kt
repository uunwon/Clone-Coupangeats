package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName

data class PostFavoriteRequest(
    @SerializedName("restaurantId") val restaurantId: Int
)
