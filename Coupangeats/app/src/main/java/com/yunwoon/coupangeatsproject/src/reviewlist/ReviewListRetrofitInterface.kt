package com.yunwoon.coupangeatsproject.src.reviewlist

import com.yunwoon.coupangeatsproject.src.reviewlist.models.ReviewListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ReviewListRetrofitInterface {

    @GET("/reviews")
    fun postReview(
        @Query("restaurantId") restaurantId : Int,
        @Query("order") order : String
    ): Call<ReviewListResponse>

}