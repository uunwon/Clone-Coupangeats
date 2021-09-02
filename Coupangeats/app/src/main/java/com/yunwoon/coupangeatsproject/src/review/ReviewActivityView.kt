package com.yunwoon.coupangeatsproject.src.review

import com.yunwoon.coupangeatsproject.src.review.models.ReviewResponse

interface ReviewActivityView {

    fun onPostReviewSuccess(response: ReviewResponse)

    fun onPostReviewFailure(message: String)
}