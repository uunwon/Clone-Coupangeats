package com.yunwoon.coupangeatsproject.src.review

import com.yunwoon.coupangeatsproject.src.review.models.PostReviewRequest
import com.yunwoon.coupangeatsproject.src.review.models.ReviewResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ReviewRetrofitInterface {

    @POST("/reviews")
    fun postReview(
        @Header("x-jwt") jwt : String,
        @Body params: PostReviewRequest
    ): Call<ReviewResponse>

}