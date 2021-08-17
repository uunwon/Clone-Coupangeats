package com.yunwoon.coupangeatsproject.util.smallStoreRecycler

import android.graphics.Bitmap

data class SmallStoreData (
    val storeImage: Bitmap,
    val storeTitle: String,
    val storeStarRating: String,
    val storeReviewCount: String,
    val storeLocation: String,
    val storeDeliveryTip: String
)