package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName

data class SampleOptionCart(
    @SerializedName("optionName") val optionName : String,
    @SerializedName("price") val price : String
)
