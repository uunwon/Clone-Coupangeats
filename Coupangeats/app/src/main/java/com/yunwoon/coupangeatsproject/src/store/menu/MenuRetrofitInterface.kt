package com.yunwoon.coupangeatsproject.src.store.menu

import com.yunwoon.coupangeatsproject.src.store.models.StoreCategoryFoodResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MenuRetrofitInterface {
    @GET("/menus")
    fun getStoreCategoryFood(
        @Query("restaurantId") restaurantId : Int,
        @Query("categoryId") categoryId : Int
    ): Call<StoreCategoryFoodResponse>
}