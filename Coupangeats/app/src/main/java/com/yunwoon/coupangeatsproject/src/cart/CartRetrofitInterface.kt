package com.yunwoon.coupangeatsproject.src.cart

import com.yunwoon.coupangeatsproject.src.cart.models.PostOrderRequest
import com.yunwoon.coupangeatsproject.src.cart.models.UserCartResponse
import com.yunwoon.coupangeatsproject.src.cart.models.UserOrderResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CartRetrofitInterface {

    @GET("/carts")
    fun getCart(
        @Header("x-jwt") jwt : String
    ): Call<UserCartResponse>

    @POST("/orders")
    fun postOrder(
        @Header("x-jwt") jwt : String,
        @Body postOrderRequest: PostOrderRequest
    ): Call<UserOrderResponse>

}