package com.yunwoon.coupangeatsproject.src.main.order.models

import com.google.gson.annotations.SerializedName

data class ResultOrderingMenu(
    @SerializedName("id") val id: Int,
    @SerializedName("isComplete") val isComplete: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("createAt") val createAt: String,
    @SerializedName("menuCounts") val menuCounts: Int,
    @SerializedName("isOrdered") val isOrdered: Int,
    @SerializedName("menuPrice") val menuPrice: String,
    @SerializedName("menuName") val menuName: String,
    @SerializedName("cartId") val cartId: Int,
    @SerializedName("restaurantId") val restaurantId: Int,
)