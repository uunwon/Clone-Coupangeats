package com.yunwoon.coupangeatsproject.src.main.login.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class UsersResponse(
    @SerializedName("result") val result: ArrayList<ResultUsers>
) : BaseResponse()