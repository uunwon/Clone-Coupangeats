package com.yunwoon.coupangeatsproject.src.storelist

import com.yunwoon.coupangeatsproject.src.main.home.models.CategoryResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.HomeResponse
import com.yunwoon.coupangeatsproject.src.storelist.models.NewStoreResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreListRetrofitInterface {
    @GET("/categories/food")
    fun getCategories(
    ): Call<CategoryResponse>

    @GET("/restaurants")
    fun getRestaurantswithCategory(
        @Query("categoryId") categoryId : Int
    ): Call<HomeResponse>

    @GET("/restaurants")
    fun getNewRestaurants(
        @Query("categoryId") categoryId : Int,
        @Query("order") order : String
    ): Call<NewStoreResponse>
}