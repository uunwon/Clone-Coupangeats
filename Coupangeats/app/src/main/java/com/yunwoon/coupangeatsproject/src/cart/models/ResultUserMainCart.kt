package com.yunwoon.coupangeatsproject.src.cart.models

import com.google.gson.annotations.SerializedName

data class ResultUserMainCart(
    @SerializedName("cartId") val cartId: Int,
    @SerializedName("menuCounts") val menuCounts: Int,
    @SerializedName("isOrdered") val isOrdered: Int,
    @SerializedName("menuId") val menuId: Int,
    @SerializedName("price") val price: String,
    @SerializedName("menuName") val menuName: String,
    @SerializedName("userId") val userId: Int,
    @SerializedName("userName") val userName: String,
    @SerializedName("restaurantId") val restaurantId: Int,
    @SerializedName("restaurantName") val restaurantName: String,
    @SerializedName("delieveryFee") val delieveryFee: String,
    @SerializedName("createAt") val createAt: String,
    @SerializedName("updateAt") val updateAt: String
)