package com.yunwoon.coupangeatsproject.src.review.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class ReviewResponse(
    @SerializedName("result") val result: ResultReview
) : BaseResponse()
