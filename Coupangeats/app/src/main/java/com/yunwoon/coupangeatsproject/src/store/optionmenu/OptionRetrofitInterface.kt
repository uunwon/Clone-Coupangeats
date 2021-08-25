package com.yunwoon.coupangeatsproject.src.store.optionmenu

import com.yunwoon.coupangeatsproject.src.store.models.*
import retrofit2.Call
import retrofit2.http.*

interface OptionRetrofitInterface {

    @GET("/categories/options")
    fun getMenuOptionCategories(
        @Query("menuId") menuId : Int
    ): Call<OptionMenuCategoryResponse>

    @GET("/options")
    fun getOptionMenus(
        @Query("menuId") menuId : Int,
        @Query("categoryId") categoryId : Int
    ): Call<OptionMenuResponse>

    @POST("/carts")
    fun postCart(
        @Header("x-jwt") jwt : String,
        @Body params: PostCartRequest
    ): Call<CartResponse>

    @POST("/carts")
    fun postCartwithOption(
        @Header("x-jwt") jwt : String,
        @Body params: PostCartwithOptionRequest
    ): Call<CartwithOptionResponse>

    @POST("/carts/options")
    fun postOptionCart(
        @Header("x-jwt") jwt : String,
        @Body params: PostOptionCartRequest
    ): Call<OptionCartResponse>

}
