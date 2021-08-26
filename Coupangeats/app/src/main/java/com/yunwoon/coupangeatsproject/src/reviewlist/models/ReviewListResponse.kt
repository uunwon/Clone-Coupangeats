package com.yunwoon.coupangeatsproject.src.reviewlist.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class ReviewListResponse(
    @SerializedName("result") val result: ArrayList<ResultReviewList>
) : BaseResponse()