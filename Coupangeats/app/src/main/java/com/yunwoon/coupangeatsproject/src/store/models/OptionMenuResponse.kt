package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class OptionMenuResponse(
    @SerializedName("result") val result: ArrayList<ResultOptionMenu>
) : BaseResponse()