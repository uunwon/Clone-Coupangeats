package com.yunwoon.coupangeatsproject.src.main.login

import com.yunwoon.coupangeatsproject.src.main.login.models.JoinResponse
import com.yunwoon.coupangeatsproject.src.main.login.models.PostJoinRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface JoinRetrofitInterface {

    @POST("/users")
    fun postJoin(@Body params: PostJoinRequest): Call<JoinResponse>
}