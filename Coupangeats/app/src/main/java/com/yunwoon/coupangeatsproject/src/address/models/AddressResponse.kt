package com.yunwoon.coupangeatsproject.src.address.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class AddressResponse(
    @SerializedName("result") val result: ArrayList<ResultAddress>
) : BaseResponse()
