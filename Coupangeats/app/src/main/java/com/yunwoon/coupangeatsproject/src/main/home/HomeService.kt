package com.yunwoon.coupangeatsproject.src.main.home

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.main.home.models.CategoryResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.HomeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService(val view: HomeFragmentView) {

    fun tryGetCategories(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getCategories().enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if(response.body() != null)
                    view.onGetCategoriesSuccess(response.body() as CategoryResponse)
                else
                    view.onGetCategoriesFailure("카테고리를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                view.onGetCategoriesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetRestaurants(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getRestaurants().enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if(response.body() != null)
                    view.onGetRestaurantsSuccess(response.body() as HomeResponse)
                else
                    view.onGetRestaurantsFailure("전체 식당 리스트를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                view.onGetRestaurantsFailure(t.message ?: "통신 오류")
            }
        })
    }
}