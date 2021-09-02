package com.yunwoon.coupangeatsproject.src.main.home

import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.CategoryResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.HomeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface HomeRetrofitInterface {

    @GET("/locations/users")
    fun getAddress(
        @Header("x-jwt") jwt : String
    ): Call<AddressResponse>

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