package com.yunwoon.coupangeatsproject.src.main.login

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.main.login.models.LogInResponse
import com.yunwoon.coupangeatsproject.src.main.login.models.PostLogInRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val view: LoginActivityView) {

    fun tryPostLogIn(postLogInRequest: PostLogInRequest) {
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postLogIn(postLogInRequest).enqueue(object: Callback<LogInResponse>{
            override fun onResponse(call: Call<LogInResponse>, response: Response<LogInResponse>) {
                view.onPostLogInSuccess(response.body() as LogInResponse)
            }

            override fun onFailure(call: Call<LogInResponse>, t: Throwable) {
                view.onPostLogInFailure(t.message ?: "통신 오류")
            }

        })
    }
}