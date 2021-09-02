package com.yunwoon.coupangeatsproject.src.review.models

import com.google.gson.annotations.SerializedName

data class PostReviewRequest(
    @SerializedName("review") val review: String,
    @SerializedName("rating") val rating: Float,
    @SerializedName("restaurantId") val restaurantId: Int
)
