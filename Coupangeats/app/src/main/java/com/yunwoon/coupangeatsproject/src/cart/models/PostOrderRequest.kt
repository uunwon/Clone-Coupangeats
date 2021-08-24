package com.yunwoon.coupangeatsproject.src.cart.models

import com.google.gson.annotations.SerializedName

data class PostOrderRequest(
    @SerializedName("cartId") val cartId: Int,
)
