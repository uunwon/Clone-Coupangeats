package com.yunwoon.coupangeatsproject.src.cart

import com.yunwoon.coupangeatsproject.src.cart.models.UserCartResponse
import com.yunwoon.coupangeatsproject.src.cart.models.UserOrderResponse

interface CartActivityView {
    fun onGetUserCartSuccess(response: UserCartResponse)

    fun onGetUserCartFailure(message: String)

    fun onPostOrderSuccess(response: UserOrderResponse)

    fun onPostOrderFailure(message: String)
}