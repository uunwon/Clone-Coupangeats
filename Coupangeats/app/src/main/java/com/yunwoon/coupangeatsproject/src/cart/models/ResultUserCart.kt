package com.yunwoon.coupangeatsproject.src.cart.models

import com.google.gson.annotations.SerializedName

data class ResultUserCart(
    @SerializedName("carts") val carts: ArrayList<ResultUserMainCart>,
    @SerializedName("optionCarts") val optionCarts: ArrayList<ResultUserOptionCart>
)