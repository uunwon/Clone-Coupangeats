package com.yunwoon.coupangeatsproject.src.store.optionmenu

import com.yunwoon.coupangeatsproject.src.store.models.CartResponse

interface OptionActivityView {
    fun onPostCartSuccess(response: CartResponse)

    fun onPostCartFailure(message: String)
}