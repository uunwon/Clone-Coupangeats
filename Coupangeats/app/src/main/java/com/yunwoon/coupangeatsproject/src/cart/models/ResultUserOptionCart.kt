package com.yunwoon.coupangeatsproject.src.cart.models

import com.google.gson.annotations.SerializedName

data class ResultUserOptionCart(
    @SerializedName("optionCartId") val optionCartId: Int,
    @SerializedName("optionId") val optionId: Int,
    @SerializedName("optionName") val optionName: String,
    @SerializedName("cartId") val cartId: Int,
    @SerializedName("price") val price: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updateAt") val updateAt: String
)