package com.yunwoon.coupangeatsproject.src.address.models

import com.google.gson.annotations.SerializedName

data class AddressSearchResponse(
    @SerializedName("results") val searchResults: AddressSearchResults
)
