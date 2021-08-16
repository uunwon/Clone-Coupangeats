package com.yunwoon.coupangeatsproject.src.main.login

import com.yunwoon.coupangeatsproject.src.main.login.models.JoinResponse
import com.yunwoon.coupangeatsproject.src.main.login.models.LogInResponse
import com.yunwoon.coupangeatsproject.src.main.login.models.PostJoinRequest
import com.yunwoon.coupangeatsproject.src.main.login.models.PostLogInRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @POST("/users/login")
    fun postLogIn(@Body params: PostLogInRequest): Call<LogInResponse>
}