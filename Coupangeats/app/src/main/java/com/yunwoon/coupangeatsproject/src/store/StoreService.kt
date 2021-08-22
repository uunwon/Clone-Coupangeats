package com.yunwoon.coupangeatsproject.src.store

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.store.models.StoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreService(val view: StoreActivityView) {
    fun tryGetStore(restaurantId: Int){
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(StoreRetrofitInterface::class.java)
        storeRetrofitInterface.getStore(restaurantId).enqueue(object : Callback<StoreResponse> {
            override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                if(response.body() != null)
                    view.onGetStoreSuccess(response.body() as StoreResponse)
                else
                    view.onGetStoreFailure("가게를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                view.onGetStoreFailure(t.message ?: "통신 오류")
            }
        })
    }
}