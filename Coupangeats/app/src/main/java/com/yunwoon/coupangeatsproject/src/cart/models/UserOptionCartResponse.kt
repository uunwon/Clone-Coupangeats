package com.yunwoon.coupangeatsproject.src.cart.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class UserOptionCartResponse(
    @SerializedName("result") val result: ArrayList<ResultUserOptionCart>
) : BaseResponse()