package com.yunwoon.coupangeatsproject.src.store.optionmenu

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.store.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OptionMenuActivityService(val view: OptionActivityView) {

    fun tryPostCart(loginJwtToken: String, postCartRequest : PostCartRequest) {
        val optionRetrofitInterface = ApplicationClass.sRetrofit.create(OptionRetrofitInterface::class.java)
        optionRetrofitInterface.postCart(loginJwtToken, postCartRequest).enqueue(object: Callback<CartResponse> {
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                if(response.body() != null)
                    view.onPostCartSuccess(response.body() as CartResponse)
                else {
                    view.onPostCartFailure("입력 오류")
                }
            }
            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                view.onPostCartFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostCartwithOption(loginJwtToken: String, postCartwithOptionRequest: PostCartwithOptionRequest) {
        val optionRetrofitInterface = ApplicationClass.sRetrofit.create(OptionRetrofitInterface::class.java)
        optionRetrofitInterface.postCartwithOption(loginJwtToken, postCartwithOptionRequest).enqueue(object: Callback<CartwithOptionResponse> {
            override fun onResponse(call: Call<CartwithOptionResponse>, response: Response<CartwithOptionResponse>) {
                if(response.body() != null)
                    view.onPostCartwithOptionSuccess(response.body() as CartwithOptionResponse)
                else {
                    view.onPostCartwithOptionFailure("입력 오류")
                }
            }
            override fun onFailure(call: Call<CartwithOptionResponse>, t: Throwable) {
                view.onPostCartwithOptionFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostOptionCart(loginJwtToken: String, postOptionCartRequest : PostOptionCartRequest) {
        val optionRetrofitInterface = ApplicationClass.sRetrofit.create(OptionRetrofitInterface::class.java)
        optionRetrofitInterface.postOptionCart(loginJwtToken, postOptionCartRequest).enqueue(object: Callback<OptionCartResponse> {
            override fun onResponse(call: Call<OptionCartResponse>, response: Response<OptionCartResponse>) {
                if(response.body() != null)
                    view.onPostOptionCartSuccess(response.body() as OptionCartResponse)
                else {
                    view.onPostOptionCartFailure("입력 오류")
                }
            }
            override fun onFailure(call: Call<OptionCartResponse>, t: Throwable) {
                view.onPostOptionCartFailure(t.message ?: "통신 오류")
            }
        })
    }
}