package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName

data class ResultStoreCategoryFood(
    @SerializedName("id") val id : Int,
    @SerializedName("menuName") val menuName : String,
    @SerializedName("restaurantId") val restaurantId : Int,
    @SerializedName("menuCategoryName") val menuCategoryName : String,
    @SerializedName("categoryId") val categoryId : Int,
    @SerializedName("price") val price : String
)
