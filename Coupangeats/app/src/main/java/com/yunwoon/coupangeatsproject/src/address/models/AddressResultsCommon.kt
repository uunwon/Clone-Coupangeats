package com.yunwoon.coupangeatsproject.src.address.models

import com.google.gson.annotations.SerializedName

data class AddressResultsCommon(
    @SerializedName("errorMessage") val errorMessage: String,
    @SerializedName("countPerPage") val countPerPage: Int,
    @SerializedName("totalCount") val totalCount: Int,
    @SerializedName("errorCode") val errorCode: String,
    @SerializedName("currentPage") val currentPage: String
)
