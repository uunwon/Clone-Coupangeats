package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName

data class ResultOptionMenuCategory(
    @SerializedName("id") val id : Int,
    @SerializedName("categoryName") val categoryName : String,
    @SerializedName("isRequired") val isRequired : Int,
    @SerializedName("menuId") val menuId : Int,
    @SerializedName("restaurantId") val restaurantId : Int
)
