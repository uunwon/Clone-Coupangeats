package com.yunwoon.coupangeatsproject.src.address.models

import com.google.gson.annotations.SerializedName

data class PostAddressRequest(
    @SerializedName("location") val location: String,
    @SerializedName("locationDetail") val locationDetail: String,
    @SerializedName("category") val category: String
)
