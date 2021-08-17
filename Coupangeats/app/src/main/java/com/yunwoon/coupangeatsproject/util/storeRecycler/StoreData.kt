package com.yunwoon.coupangeatsproject.util.storeRecycler

import android.graphics.Bitmap

data class StoreData(
    val storeImage1: Bitmap,
    val storeImage2: Bitmap,
    val storeImage3: Bitmap,
    val storeTitle: String,
    val storeDeliveryTime: String,
    val storeStarRating: String,
    val storeReviewCount: String,
    val storeLocation: String,
    val storeDeliveryTip: String
)
