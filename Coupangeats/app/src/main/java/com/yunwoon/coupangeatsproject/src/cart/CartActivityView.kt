package com.yunwoon.coupangeatsproject.src.cart

import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse
import com.yunwoon.coupangeatsproject.src.cart.models.UserCartResponse
import com.yunwoon.coupangeatsproject.src.cart.models.UserOptionCartResponse
import com.yunwoon.coupangeatsproject.src.cart.models.UserOrderResponse

interface CartActivityView {

    fun onGetAddressSuccess(response: AddressResponse)

    fun onGetAddressFailure(message: String)

    fun onGetUserCartSuccess(response: UserCartResponse)

    fun onGetUserCartFailure(message: String)

    fun onGetUserOptionCartSuccess(response: UserOptionCartResponse, cartId: Int)

    fun onGetUserOptionCartFailure(message: String)

    fun onPostOrderSuccess(response: UserOrderResponse)

    fun onPostOrderFailure(message: String)

}