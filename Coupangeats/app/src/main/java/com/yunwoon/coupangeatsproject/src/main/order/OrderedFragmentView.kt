package com.yunwoon.coupangeatsproject.src.main.order

import com.yunwoon.coupangeatsproject.src.main.order.models.OrderedResponse
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderMenuData

interface OrderedFragmentView {

    fun onGetOrderedSuccess(response: OrderedResponse)

    fun onGetOrderedFailure(message: String)

    fun onGetOrderedOptionSuccess(response: OrderedResponse, cartId: Int, orderMenuData: MutableList<OrderMenuData>)

    fun onGetOrderedOptionFailure(message: String)

}