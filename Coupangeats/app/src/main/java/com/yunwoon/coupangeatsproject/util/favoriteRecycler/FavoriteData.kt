package com.yunwoon.coupangeatsproject.util.favoriteRecycler

import android.graphics.Bitmap

data class FavoriteData (
    val favoriteStoreImage : Bitmap,
    val favoriteTitle : String,
    val favoriteStarRating : String,
    val favoriteReviewCount : String,
    val favoriteLocation : String,
    val favoriteDeliveryTime : String,
    val favoriteDeliveryTip : String
)