package com.yunwoon.coupangeatsproject.src.reviewlist

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.reviewlist.models.ReviewListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewListService(val view: ReviewListActivityView) {

    fun tryGetReviews(restaurantId: Int, order: String){
        val reviewListRetrofitInterface = ApplicationClass.sRetrofit.create(ReviewListRetrofitInterface::class.java)
        reviewListRetrofitInterface.postReview(restaurantId, order).enqueue(object : Callback<ReviewListResponse> {
            override fun onResponse(call: Call<ReviewListResponse>, response: Response<ReviewListResponse>) {
                if(response.body() != null)
                    view.onGetReviewsSuccess(response.body() as ReviewListResponse)
                else
                    view.onGetReviewsFailure("리뷰 조회에 실패했습니다")
            }

            override fun onFailure(call: Call<ReviewListResponse>, t: Throwable) {
                view.onGetReviewsFailure(t.message ?: "통신 오류")
            }
        })
    }

}