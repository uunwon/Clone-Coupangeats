package com.yunwoon.coupangeatsproject.util.storeRecycler

import android.graphics.Bitmap

data class StoreData(
    val storeIndex : Int,
    val storeImage: String,
    val storeTitle: String,
    val storeDeliveryTime: String,
    val storeStarRating: String,
    val storeReviewCount: String,
    val storeLocation: String,
    val storeDeliveryTip: String
)
