package com.yunwoon.coupangeatsproject.src.main.order.models

import com.google.gson.annotations.SerializedName

data class ResultOrdered(
    @SerializedName("selectOrderResult") val selectOrderResult: ArrayList<ResultOrderingMenu>,
    @SerializedName("selectOptionOrderResult") val selectOptionOrderResult: ArrayList<ResultOrderingOption>
)
