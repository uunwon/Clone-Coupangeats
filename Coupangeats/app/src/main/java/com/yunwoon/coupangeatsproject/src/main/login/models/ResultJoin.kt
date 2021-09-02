package com.yunwoon.coupangeatsproject.src.main.login.models

import com.google.gson.annotations.SerializedName

data class ResultJoin(
    @SerializedName("fieldCount") val fieldCount: Int,
    @SerializedName("affectedRows") val affectedRow: Int,
    @SerializedName("insertId") val insertId: Int,
    @SerializedName("info") val info: String,
    @SerializedName("serverStatus") val serverStatus: Int,
    @SerializedName("warningStatus") val warningStatus: Int
)