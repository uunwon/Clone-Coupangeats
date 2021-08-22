package com.yunwoon.coupangeatsproject.src.store.models

import com.google.gson.annotations.SerializedName

data class ResultStoreRestaurant(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("minOrderPrice") val minOrderPrice : String,
    @SerializedName("delieveryFee") val delieveryFee: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String
)
