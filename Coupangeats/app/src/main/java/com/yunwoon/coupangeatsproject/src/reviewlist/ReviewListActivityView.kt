package com.yunwoon.coupangeatsproject.src.reviewlist

import com.yunwoon.coupangeatsproject.src.reviewlist.models.ReviewListResponse

interface ReviewListActivityView {

    fun onGetReviewsSuccess(response: ReviewListResponse)

    fun onGetReviewsFailure(message: String)
}