package com.yunwoon.coupangeatsproject.src.address

import com.yunwoon.coupangeatsproject.src.address.models.AddressPostResponse
import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse
import com.yunwoon.coupangeatsproject.src.address.models.AddressSearchResponse
import com.yunwoon.coupangeatsproject.src.address.models.PostAddressRequest
import retrofit2.Call
import retrofit2.http.*

interface AddressRetrofitInterface {

    @GET("/locations/users")
    fun getAddress(
        @Header("x-jwt") jwt : String
    ): Call<AddressResponse>

    @POST("/locations/users")
    fun postAddress(
        @Header("x-jwt") jwt : String,
        @Body postAddressRequest: PostAddressRequest
    ): Call<AddressPostResponse>

    @GET("/addrlink/addrLinkApi.do")
    fun getRoad(
        @Query("confmKey") confmKey : String,
        @Query("currentPage") currentPage : Int,
        @Query("countPerPage") countPerPage : Int,
        @Query("keyword") keyword : String,
        @Query("resultType") resultType : String
    ): Call<AddressSearchResponse>
}