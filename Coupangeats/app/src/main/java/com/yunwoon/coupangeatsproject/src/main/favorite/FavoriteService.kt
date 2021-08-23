package com.yunwoon.coupangeatsproject.src.main.favorite

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.main.favorite.models.FavoriteStoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteService(val view: FavoriteActivityView) {

    fun tryGetFavorite(loginJwtToken: String){
        val favoriteRetrofitInterface = ApplicationClass.sRetrofit.create(FavoriteRetrofitInterface::class.java)
        favoriteRetrofitInterface.getFavorite(loginJwtToken).enqueue(object : Callback<FavoriteStoreResponse> {
            override fun onResponse(call: Call<FavoriteStoreResponse>, favoriteStoreResponse: Response<FavoriteStoreResponse>) {
                if(favoriteStoreResponse.body() != null)
                    view.onGetFavoriteSuccess(favoriteStoreResponse.body() as FavoriteStoreResponse)
                else
                    view.onGetFavoriteFailure("즐겨찾기를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<FavoriteStoreResponse>, t: Throwable) {
                view.onGetFavoriteFailure(t.message ?: "통신 오류")
            }
        })
    }

}