package com.yunwoon.coupangeatsproject.src.store

import com.yunwoon.coupangeatsproject.src.store.models.StoreCategoryResponse
import com.yunwoon.coupangeatsproject.src.store.models.StoreResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreRetrofitInterface {
    @GET("/restaurants/{restaurantId}")
    fun getStore(
        @Path ("restaurantId") id : Int
    ): Call<StoreResponse>

    @GET("/categories/menus")
    fun getStoreCategories(
        @Query("restaurantId") id : Int
    ): Call<StoreCategoryResponse>
}