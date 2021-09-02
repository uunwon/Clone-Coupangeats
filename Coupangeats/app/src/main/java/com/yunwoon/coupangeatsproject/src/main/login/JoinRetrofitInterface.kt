package com.yunwoon.coupangeatsproject.src.main.login

import com.yunwoon.coupangeatsproject.src.main.login.models.JoinResponse
import com.yunwoon.coupangeatsproject.src.main.login.models.PostJoinRequest
import com.yunwoon.coupangeatsproject.src.main.login.models.UsersResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JoinRetrofitInterface {
    @GET("/users")
    fun getUsers(): Call<UsersResponse>

    @POST("/users")
    fun postJoin(@Body params: PostJoinRequest): Call<JoinResponse>
}