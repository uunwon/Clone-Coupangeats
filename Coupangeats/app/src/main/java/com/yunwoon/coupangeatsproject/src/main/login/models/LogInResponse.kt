package com.yunwoon.coupangeatsproject.src.main.login.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class LogInResponse (
    @SerializedName("result") val result : ResultLogIn
) : BaseResponse()