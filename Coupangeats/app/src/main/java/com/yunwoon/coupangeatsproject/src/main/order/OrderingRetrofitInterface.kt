package com.yunwoon.coupangeatsproject.src.main.order

import com.yunwoon.coupangeatsproject.src.main.order.models.OrderingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface OrderingRetrofitInterface {
    @GET("/orders")
    fun getOrdering(
        @Header("x-jwt") jwt : String
    ): Call<OrderingResponse>
}