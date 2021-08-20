package com.yunwoon.coupangeatsproject.src.main.home

import com.yunwoon.coupangeatsproject.src.main.home.models.CategoryResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.HomeResponse
import retrofit2.Call
import retrofit2.http.GET

interface HomeRetrofitInterface {

    @GET("/categories/food")
    fun getCategories(
    ): Call<CategoryResponse>

    @GET("/restaurants")
    fun getRestaurants(
    ): Call<HomeResponse>
}