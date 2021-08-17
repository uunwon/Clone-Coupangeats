package com.yunwoon.coupangeatsproject.src.address.models

import com.google.gson.annotations.SerializedName

data class AddressResults(
    @SerializedName("common") val common: AddressResultsCommon,
    @SerializedName("juso") val juso: ArrayList<AddressResultsJuso>
)
