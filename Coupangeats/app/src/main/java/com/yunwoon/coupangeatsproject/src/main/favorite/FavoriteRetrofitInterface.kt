package com.yunwoon.coupangeatsproject.src.main.favorite

import com.yunwoon.coupangeatsproject.src.main.favorite.models.FavoriteStoreResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface FavoriteRetrofitInterface {

    @GET("/favorites")
    fun getFavorite(
        @Header("x-jwt") jwt : String
    ): Call<FavoriteStoreResponse>

}