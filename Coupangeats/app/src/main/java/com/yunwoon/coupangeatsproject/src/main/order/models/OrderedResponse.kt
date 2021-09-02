package com.yunwoon.coupangeatsproject.src.main.order.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class OrderedResponse(
    @SerializedName("result") val result: ResultOrdered
) : BaseResponse()
