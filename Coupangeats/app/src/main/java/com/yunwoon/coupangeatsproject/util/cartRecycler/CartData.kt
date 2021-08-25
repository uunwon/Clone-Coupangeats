package com.yunwoon.coupangeatsproject.util.cartRecycler

data class CartData(
    val cartId : Int,
    val cartMain : String,
    val cartOption : MutableList<String>,
    val cartPrice : String,
    val cartCount : Int
)