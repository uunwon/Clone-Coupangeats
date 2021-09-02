package com.yunwoon.coupangeatsproject.src.review

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.review.models.PostReviewRequest
import com.yunwoon.coupangeatsproject.src.review.models.ReviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewService(val view: ReviewActivityView) {

    fun tryPostReview(jwt: String, params: PostReviewRequest){
        val reviewRetrofitInterface = ApplicationClass.sRetrofit.create(ReviewRetrofitInterface::class.java)
        reviewRetrofitInterface.postReview(jwt, params).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                if(response.body() != null)
                    view.onPostReviewSuccess(response.body() as ReviewResponse)
                else
                    view.onPostReviewFailure("리뷰 생성에 실패했습니다")
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                view.onPostReviewFailure(t.message ?: "통신 오류")
            }
        })
    }

}