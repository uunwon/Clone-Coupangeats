package com.yunwoon.coupangeatsproject.src.address.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class AddressPostResponse(
    @SerializedName("result") val result: String
) : BaseResponse()
