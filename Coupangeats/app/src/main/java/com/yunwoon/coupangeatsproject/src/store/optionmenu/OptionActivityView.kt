package com.yunwoon.coupangeatsproject.src.store.optionmenu

import com.yunwoon.coupangeatsproject.src.store.models.CartResponse
import com.yunwoon.coupangeatsproject.src.store.models.CartwithOptionResponse
import com.yunwoon.coupangeatsproject.src.store.models.OptionCartResponse

interface OptionActivityView {
    fun onPostCartSuccess(response: CartResponse)

    fun onPostCartFailure(message: String)

    fun onPostCartwithOptionSuccess(response: CartwithOptionResponse)

    fun onPostCartwithOptionFailure(message: String)

    fun onPostOptionCartSuccess(response: OptionCartResponse)

    fun onPostOptionCartFailure(message: String)
}