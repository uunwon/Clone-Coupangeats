package com.yunwoon.coupangeatsproject.src.storelist.models

import com.google.gson.annotations.SerializedName

data class ResultNewStore(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("minOrderPrice") val minOrderPrice: String,
    @SerializedName("delieveryFee") val deliveryFee: String,
    @SerializedName("categoryName") val categoryName: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("reviewCount") val reviewCount: Int,
    @SerializedName("ratingAvg") val ratingAvg : Int,
    @SerializedName("imgUrl") val imgUrl : String
)
