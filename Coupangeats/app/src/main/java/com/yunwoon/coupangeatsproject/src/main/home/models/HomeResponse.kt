package com.yunwoon.coupangeatsproject.src.main.home.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class HomeResponse(
    @SerializedName("result") val result: ResultHome
) : BaseResponse()
