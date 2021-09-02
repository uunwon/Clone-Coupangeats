package com.yunwoon.coupangeatsproject.src.store.menu

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.store.models.StoreCategoryFoodResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuService(val view : MenuFragmentView) {

    fun tryGetStore(restaurantId: Int, categoryId: Int){
        val menuRetrofitInterface = ApplicationClass.sRetrofit.create(MenuRetrofitInterface::class.java)
        menuRetrofitInterface.getStoreCategoryFood(restaurantId, categoryId).enqueue(object : Callback<StoreCategoryFoodResponse> {
            override fun onResponse(call: Call<StoreCategoryFoodResponse>, response: Response<StoreCategoryFoodResponse>) {
                if(response.body() != null)
                    view.onGetStoreCategoryFoodSuccess(response.body() as StoreCategoryFoodResponse)
                else
                    view.onGetStoreCategoryFoodFailure("가게의 카테고리의 음식을 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<StoreCategoryFoodResponse>, t: Throwable) {
                view.onGetStoreCategoryFoodFailure(t.message ?: "통신 오류")
            }
        })
    }
}