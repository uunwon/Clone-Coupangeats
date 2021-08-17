package com.yunwoon.coupangeatsproject.src.address

import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressRetrofitInterface {
    @GET("/addrlink/addrLinkApi.do")
    fun getRoad(
        @Query("confmKey") confmKey : String,
        @Query("currentPage") currentPage : Int,
        @Query("countPerPage") countPerPage : Int,
        @Query("keyword") keyword : String,
        @Query("resultType") resultType : String
    ): Call<AddressResponse>
}