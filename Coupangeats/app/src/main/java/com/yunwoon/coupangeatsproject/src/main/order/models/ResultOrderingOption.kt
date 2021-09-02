package com.yunwoon.coupangeatsproject.src.main.order.models

import com.google.gson.annotations.SerializedName

data class ResultOrderingOption(
    @SerializedName("optionName") val optionName: String,
    @SerializedName("price") val price: String,
    @SerializedName("cartId") val cartId: Int
)