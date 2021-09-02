package com.yunwoon.coupangeatsproject.src.main.order

import com.yunwoon.coupangeatsproject.src.main.order.models.OrderingResponse
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderMenuData

interface OrderingFragmentView {

    fun onGetOrderingSuccess(response: OrderingResponse)

    fun onGetOrderingFailure(message: String)

    fun onGetOrderingOptionSuccess(response: OrderingResponse, cartId: Int, orderMenuData: MutableList<OrderMenuData>)

    fun onGetOrderingOptionFailure(message: String)
}