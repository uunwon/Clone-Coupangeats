package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName

data class PostOptionCartRequest(
    @SerializedName("optionId") val optionId: Int,
    @SerializedName("cartId") val cartId: Int
)
