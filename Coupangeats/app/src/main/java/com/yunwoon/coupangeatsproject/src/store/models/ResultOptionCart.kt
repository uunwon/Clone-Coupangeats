package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName

data class ResultOptionCart(
    @SerializedName("cartResult") val cartResult: Int,
    @SerializedName("optionCartResult") val optionCartResult: Int
)