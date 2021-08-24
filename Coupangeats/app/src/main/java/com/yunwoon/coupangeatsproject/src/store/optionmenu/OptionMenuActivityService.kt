package com.yunwoon.coupangeatsproject.src.store.optionmenu

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.store.models.CartResponse
import com.yunwoon.coupangeatsproject.src.store.models.PostCartRequest
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
}