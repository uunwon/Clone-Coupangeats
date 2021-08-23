package com.yunwoon.coupangeatsproject.src.storelist

import com.yunwoon.coupangeatsproject.src.main.home.models.CategoryResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.HomeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreListRetrofitInterface {
    @GET("/categories/food")
    fun getCategories(
    ): Call<CategoryResponse>

    @GET("/restaurants")
    fun getRestaurants(
    ): Call<HomeResponse>

    @GET("/restaurants")
    fun getOrderRestaurants(
        @Query("order") order : String
    ): Call<HomeResponse>
}