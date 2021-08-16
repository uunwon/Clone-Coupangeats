package com.yunwoon.coupangeatsproject.src.main.login.models

import com.google.gson.annotations.SerializedName

data class ResultLogIn(
    @SerializedName("token") val jwt: String
)
