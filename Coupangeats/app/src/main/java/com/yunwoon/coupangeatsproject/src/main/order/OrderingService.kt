package com.yunwoon.coupangeatsproject.src.main.order

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.main.order.models.OrderingResponse
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderMenuData
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class OrderingService(val view: OrderingFragmentView) {

    fun tryGetOrdering(loginJwtToken: String){
        val orderingRetrofitInterface = ApplicationClass.sRetrofit.create(OrderingRetrofitInterface::class.java)
        orderingRetrofitInterface.getOrdering(loginJwtToken).enqueue(object : Callback<OrderingResponse> {
            override fun onResponse(call: Call<OrderingResponse>, response: Response<OrderingResponse>) {
                if(response.body() != null)
                    view.onGetOrderingSuccess(response.body() as OrderingResponse)
                else
                    view.onGetOrderingFailure("현재 배달 목록을 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<OrderingResponse>, t: Throwable) {
                view.onGetOrderingFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetOrderingOption(loginJwtToken: String, cartId: Int, orderMenuData: MutableList<OrderMenuData>) = runBlocking {
        thread(start=true) {
            val orderingRetrofitInterface = ApplicationClass.sRetrofit.create(OrderingRetrofitInterface::class.java)

            try {
                val response : Response<OrderingResponse> = orderingRetrofitInterface.getOrdering(loginJwtToken).execute()
                if(response.body() != null)
                    view.onGetOrderingOptionSuccess(response.body() as OrderingResponse, cartId, orderMenuData)
                else
                    view.onGetOrderingOptionFailure("현재 배달 목록을 받아오는데 실패했습니다")
            } catch (e : Exception) {
                e.printStackTrace()
                view.onGetOrderingOptionFailure("통신 오류")
            }
        }
    }

    /* fun tryGetOrderingOption(loginJwtToken: String){
        val orderingRetrofitInterface = ApplicationClass.sRetrofit.create(OrderingRetrofitInterface::class.java)
        orderingRetrofitInterface.getOrdering(loginJwtToken).enqueue(object : Callback<OrderingResponse> {
            override fun onResponse(call: Call<OrderingResponse>, response: Response<OrderingResponse>) {
                if(response.body() != null)
                    view.onGetOrderingSuccess(response.body() as OrderingResponse)
                else
                    view.onGetOrderingFailure("현재 배달 목록을 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<OrderingResponse>, t: Throwable) {
                view.onGetOrderingFailure(t.message ?: "통신 오류")
            }
        })
    } */

}