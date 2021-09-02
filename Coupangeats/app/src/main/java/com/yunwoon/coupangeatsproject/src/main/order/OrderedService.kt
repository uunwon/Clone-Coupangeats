package com.yunwoon.coupangeatsproject.src.main.order

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.main.order.models.OrderedResponse
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderMenuData
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class OrderedService(val view: OrderedFragmentView) {

    fun tryGetOrdered(loginJwtToken: String){
        val orderedRetrofitInterface = ApplicationClass.sRetrofit.create(OrderedRetrofitInterface::class.java)
        orderedRetrofitInterface.getOrdered(loginJwtToken).enqueue(object :
            Callback<OrderedResponse> {
            override fun onResponse(call: Call<OrderedResponse>, response: Response<OrderedResponse>) {
                if(response.body() != null)
                    view.onGetOrderedSuccess(response.body() as OrderedResponse)
                else
                    view.onGetOrderedFailure("과거 배달 목록을 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<OrderedResponse>, t: Throwable) {
                view.onGetOrderedFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetOrderedOption(loginJwtToken: String, cartId: Int, orderMenuData: MutableList<OrderMenuData>) = runBlocking {
        thread(start=true) {
            val orderedRetrofitInterface = ApplicationClass.sRetrofit.create(OrderedRetrofitInterface::class.java)

            try {
                val response : Response<OrderedResponse> = orderedRetrofitInterface.getOrdered(loginJwtToken).execute()
                if(response.body() != null)
                    view.onGetOrderedOptionSuccess(response.body() as OrderedResponse, cartId, orderMenuData)
                else
                    view.onGetOrderedOptionFailure("과거 배달 목록을 받아오는데 실패했습니다")
            } catch (e : Exception) {
                e.printStackTrace()
                view.onGetOrderedOptionFailure("통신 오류")
            }
        }
    }

}