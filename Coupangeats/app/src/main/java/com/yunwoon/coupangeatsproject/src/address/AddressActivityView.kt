package com.yunwoon.coupangeatsproject.src.address

import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse

interface AddressActivityView {

    fun onGetAddressSuccess(response: AddressResponse)

    fun onPostAddressFailure(message: String)
}