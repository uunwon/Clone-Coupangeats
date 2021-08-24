package com.yunwoon.coupangeatsproject.src.main.order

import com.yunwoon.coupangeatsproject.src.main.order.models.OrderedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface OrderedRetrofitInterface {

    @GET("/orders/history")
    fun getOrdered(
        @Header("x-jwt") jwt : String
    ): Call<OrderedResponse>

}