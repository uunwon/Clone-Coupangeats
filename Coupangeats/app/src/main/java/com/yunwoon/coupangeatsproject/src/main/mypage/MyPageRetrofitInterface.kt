package com.yunwoon.coupangeatsproject.src.main.mypage

import com.yunwoon.coupangeatsproject.src.main.mypage.models.MyPageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MyPageRetrofitInterface {
    @GET("/users/user-profile")
    fun getMyPage(
        @Header("x-jwt") jwt : String
    ): Call<MyPageResponse>
}