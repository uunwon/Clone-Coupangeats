package com.yunwoon.coupangeatsproject.src.main.order.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class OrderingResponse(
    @SerializedName("result") val result: ResultOrdering
) : BaseResponse()

