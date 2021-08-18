package com.yunwoon.coupangeatsproject.src.main.mypage

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.main.mypage.models.MyPageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService(val view: MyPageFragmentView) {

    fun tryGetMyPage(loginJwtToken: String){
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.getMyPage(loginJwtToken).enqueue(object :
            Callback<MyPageResponse> {
            override fun onResponse(call: Call<MyPageResponse>, response: Response<MyPageResponse>) {
                if(response.body() != null)
                    view.onGetMyPageSuccess(response.body() as MyPageResponse)
                else
                    view.onGetMyPageFailure("정보를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<MyPageResponse>, t: Throwable) {
                view.onGetMyPageFailure(t.message ?: "통신 오류")
            }
        })
    }
}