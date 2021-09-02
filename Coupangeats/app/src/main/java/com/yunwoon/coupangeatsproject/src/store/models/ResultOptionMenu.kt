package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName

data class ResultOptionMenu(
    @SerializedName("id") val id : Int,
    @SerializedName("optionName") val optionName : String,
    @SerializedName("price") val price : String,
    @SerializedName("categoryId") val categoryId : Int,
    @SerializedName("menuId") val menuId : Int,
    @SerializedName("isRequired") val isRequired : Int,
    @SerializedName("categoryName") val categoryName : String,
    @SerializedName("menuName") val menuName : String
)
