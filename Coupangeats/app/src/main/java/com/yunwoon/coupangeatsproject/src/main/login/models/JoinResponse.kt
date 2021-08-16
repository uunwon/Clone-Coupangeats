package com.yunwoon.coupangeatsproject.src.main.login.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class JoinResponse(
    @SerializedName("result") val result: Int
) : BaseResponse()
