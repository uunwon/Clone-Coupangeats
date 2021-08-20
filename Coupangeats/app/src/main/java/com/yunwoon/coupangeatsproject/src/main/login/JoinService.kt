package com.yunwoon.coupangeatsproject.src.main.login

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.main.login.models.JoinResponse
import com.yunwoon.coupangeatsproject.src.main.login.models.PostJoinRequest
import com.yunwoon.coupangeatsproject.src.main.login.models.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinService(val view: JoinActivityView) {

    fun tryGetUsers() {
        val joinRetrofitInterface = ApplicationClass.sRetrofit.create(JoinRetrofitInterface::class.java)
        joinRetrofitInterface.getUsers().enqueue(object: Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if(response.isSuccessful)
                    view.onGetUsersSuccess(response.body() as UsersResponse)
                else
                    view.onGetUsersFailure("회원 정보를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                view.onGetUsersFailure(t.message ?: "통신 오류")
            }

        })
    }

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