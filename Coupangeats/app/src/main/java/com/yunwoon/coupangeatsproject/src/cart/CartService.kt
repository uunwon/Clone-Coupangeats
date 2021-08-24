package com.yunwoon.coupangeatsproject.src.cart

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.cart.models.PostOrderRequest
import com.yunwoon.coupangeatsproject.src.cart.models.UserCartResponse
import com.yunwoon.coupangeatsproject.src.cart.models.UserOrderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartService(val view : CartActivityView) {

    fun tryGetCart(loginJwtToken: String){
        val cartRetrofitInterface = ApplicationClass.sRetrofit.create(CartRetrofitInterface::class.java)
        cartRetrofitInterface.getCart(loginJwtToken).enqueue(object : Callback<UserCartResponse> {
            override fun onResponse(call: Call<UserCartResponse>, response: Response<UserCartResponse>) {
                if(response.body() != null)
                    view.onGetUserCartSuccess(response.body() as UserCartResponse)
                else
                    view.onGetUserCartFailure("사용자의 카트를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<UserCartResponse>, t: Throwable) {
                view.onGetUserCartFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostOrder(loginJwtToken: String, postOrderRequest: PostOrderRequest){
        val cartRetrofitInterface = ApplicationClass.sRetrofit.create(CartRetrofitInterface::class.java)
        cartRetrofitInterface.postOrder(loginJwtToken, postOrderRequest).enqueue(object : Callback<UserOrderResponse> {
            override fun onResponse(call: Call<UserOrderResponse>, response: Response<UserOrderResponse>) {
                if(response.body() != null)
                    view.onPostOrderSuccess(response.body() as UserOrderResponse)
                else
                    view.onPostOrderFailure("주문에 실패했습니다")
            }

            override fun onFailure(call: Call<UserOrderResponse>, t: Throwable) {
                view.onPostOrderFailure(t.message ?: "통신 오류")
            }
        })
    }
}