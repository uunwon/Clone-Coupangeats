package com.yunwoon.coupangeatsproject.src.main.mypage.models

import com.google.gson.annotations.SerializedName

data class ResultMyPage(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("createAt") val createAt: String,
    @SerializedName("updateAt") val updateAt: String
)
