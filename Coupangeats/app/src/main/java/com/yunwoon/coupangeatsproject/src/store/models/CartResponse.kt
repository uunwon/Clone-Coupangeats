package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class CartResponse(
    @SerializedName("result") val result: Int
) : BaseResponse()