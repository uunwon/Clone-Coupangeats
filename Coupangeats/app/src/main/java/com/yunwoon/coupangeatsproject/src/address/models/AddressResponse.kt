package com.yunwoon.coupangeatsproject.src.address.models

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("results") val results: AddressResults
)
