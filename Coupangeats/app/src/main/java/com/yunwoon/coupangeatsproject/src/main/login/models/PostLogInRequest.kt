package com.yunwoon.coupangeatsproject.src.main.login.models

import com.google.gson.annotations.SerializedName

data class PostLogInRequest (
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)