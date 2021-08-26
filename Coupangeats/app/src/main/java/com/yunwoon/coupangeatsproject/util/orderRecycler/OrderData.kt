package com.yunwoon.coupangeatsproject.util.orderRecycler

import android.graphics.Bitmap

data class OrderData (
    val orderStoreId: Int,
    val orderStoreTitle : String,
    val orderStoreImage : Bitmap,
    val orderDate : String,
    val orderStarRating : Int,
    val orderMenuData : MutableList<OrderMenuData>,
    val orderSum : String,
    val reviewStatus : Boolean
)