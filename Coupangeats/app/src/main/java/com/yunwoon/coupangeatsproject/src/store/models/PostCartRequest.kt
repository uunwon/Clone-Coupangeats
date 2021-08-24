package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName

data class PostCartRequest(
    @SerializedName("menuId") val menuId: Int,
    @SerializedName("menuCounts") val menuCounts: Int
)