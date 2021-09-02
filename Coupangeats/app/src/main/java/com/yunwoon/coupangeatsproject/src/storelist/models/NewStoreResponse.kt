package com.yunwoon.coupangeatsproject.src.storelist.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class NewStoreResponse(
    @SerializedName("result") val result: ArrayList<ResultNewStore>
) : BaseResponse()
