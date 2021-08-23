package com.yunwoon.coupangeatsproject.src.store

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.store.models.FavoriteResponse
import com.yunwoon.coupangeatsproject.src.store.models.PostFavoriteRequest
import com.yunwoon.coupangeatsproject.src.store.models.StoreCategoryResponse
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

    fun tryGetStoreCategories(restaurantId: Int){
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(StoreRetrofitInterface::class.java)
        storeRetrofitInterface.getStoreCategories(restaurantId).enqueue(object : Callback<StoreCategoryResponse> {
            override fun onResponse(call: Call<StoreCategoryResponse>, response: Response<StoreCategoryResponse>) {
                if(response.body() != null)
                    view.onGetStoreCategoriesSuccess(response.body() as StoreCategoryResponse)
                else
                    view.onGetStoreCategoriesFailure("가게 카테고리를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<StoreCategoryResponse>, t: Throwable) {
                view.onGetStoreCategoriesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostFavorite(loginJwtToken: String, postFavoriteRequest : PostFavoriteRequest){
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(StoreRetrofitInterface::class.java)
        storeRetrofitInterface.postFavorite(loginJwtToken, postFavoriteRequest).enqueue(object : Callback<FavoriteResponse> {
            override fun onResponse(call: Call<FavoriteResponse>, response: Response<FavoriteResponse>) {
                if(response.body() != null)
                    view.onPostFavoriteSuccess(response.body() as FavoriteResponse)
                else
                    view.onPostFavoriteFailure("가게 즐겨찾기에 실패했습니다")
            }

            override fun onFailure(call: Call<FavoriteResponse>, t: Throwable) {
                view.onPostFavoriteFailure(t.message ?: "통신 오류")
            }
        })
    }
}