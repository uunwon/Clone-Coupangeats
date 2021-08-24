package com.yunwoon.coupangeatsproject.src.cart.models

import com.google.gson.annotations.SerializedName

data class ResultOrder(
    @SerializedName("fieldCount") val fieldCount: Int,
    @SerializedName("affectedRows") val affectedRows: Int,
    @SerializedName("insertId") val insertId: Int,
    @SerializedName("info") val info: String,
    @SerializedName("serverStatus") val serverStatus: Int,
    @SerializedName("warningStatus") val warningStatus: Int,
)
