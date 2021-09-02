package com.yunwoon.coupangeatsproject.src.address

import com.yunwoon.coupangeatsproject.src.address.models.AddressPostResponse
import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse
import com.yunwoon.coupangeatsproject.src.address.models.AddressSearchResponse

interface AddressActivityView {

    fun onGetAddressSuccess(response: AddressResponse)

    fun onGetAddressFailure(message: String)

    fun onPostAddressSuccess(response: AddressPostResponse)

    fun onPostAddressFailure(message: String)

    fun onGetAddressSearchSuccess(searchResponse: AddressSearchResponse)

    fun onGetAddressSearchFailure(message: String)
}