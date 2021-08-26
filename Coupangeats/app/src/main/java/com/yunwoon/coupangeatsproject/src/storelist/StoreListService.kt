package com.yunwoon.coupangeatsproject.src.storelist

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.main.home.models.CategoryResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.HomeResponse
import com.yunwoon.coupangeatsproject.src.storelist.models.NewStoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreListService(val view : StoreListActivityView) {
    fun tryGetCategories(){
        val storeListRetrofitInterface = ApplicationClass.sRetrofit.create(StoreListRetrofitInterface::class.java)
        storeListRetrofitInterface.getCategories().enqueue(object : Callback<CategoryResponse> {
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

    fun tryGetRestaurantswithCategory(categoryId : Int){
        val storeListRetrofitInterface = ApplicationClass.sRetrofit.create(StoreListRetrofitInterface::class.java)
        storeListRetrofitInterface.getRestaurantswithCategory(categoryId-1).enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if(response.body() != null)
                    view.onGetRestaurantswithCategorySuccess(response.body() as HomeResponse, categoryId)
                else
                    view.onGetRestaurantswithCategoryFailure("카테고리별 가게를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                view.onGetRestaurantswithCategoryFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetNewRestaurants(categoryId: Int, order : String) {
        val storeListRetrofitInterface = ApplicationClass.sRetrofit.create(StoreListRetrofitInterface::class.java)
        storeListRetrofitInterface.getNewRestaurants(categoryId-1, order).enqueue(object : Callback<NewStoreResponse> {
            override fun onResponse(call: Call<NewStoreResponse>, response: Response<NewStoreResponse>) {
                if(response.body() != null)
                    view.onGetNewRestaurantsSuccess(response.body() as NewStoreResponse, categoryId, order)
                else
                    view.onGetNewRestaurantsFailure("신규 식당을 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<NewStoreResponse>, t: Throwable) {
                view.onGetNewRestaurantsFailure(t.message ?: "통신 오류")
            }
        })
    }
}