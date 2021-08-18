package com.yunwoon.coupangeatsproject.src.main.mypage.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class MyPageResponse(
    @SerializedName("result") val result: ArrayList<ResultMyPage>
) : BaseResponse()
