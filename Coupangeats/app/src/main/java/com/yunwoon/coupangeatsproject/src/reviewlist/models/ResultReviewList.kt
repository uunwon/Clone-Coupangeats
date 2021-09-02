package com.yunwoon.coupangeatsproject.src.reviewlist.models

import com.google.gson.annotations.SerializedName

data class ResultReviewList(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("review") val review: String,
    @SerializedName("restaurantId") val restaurantId: Int,
    @SerializedName("rating") val rating: String,
    @SerializedName("createAt") val createAt: String,
    @SerializedName("name") val name: String
)