package com.yunwoon.coupangeatsproject.src.main.login

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.main.login.models.JoinResponse
import com.yunwoon.coupangeatsproject.src.main.login.models.PostJoinRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinService(val view: JoinActivityView) {

    fun tryGetUser() { }

    fun tryPostJoin(postJoinRequest: PostJoinRequest) {
    val joinRetrofitInterface = ApplicationClass.sRetrofit.create(JoinRetrofitInterface::class.java)
    joinRetrofitInterface.postJoin(postJoinRequest).enqueue(object: Callback<JoinResponse> {
        override fun onResponse(call: Call<JoinResponse>, response: Response<JoinResponse>) {
            view.onPostJoinSuccess(response.body() as JoinResponse)
        }

        override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
            view.onPostJoinFailure(t.message ?: "통신 오류")
        }

    })
}
}