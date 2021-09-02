package com.yunwoon.coupangeatsproject.src.main.login.models

import com.google.gson.annotations.SerializedName

data class PostJoinRequest(
    @SerializedName("name") val name: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
