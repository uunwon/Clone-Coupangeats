package com.yunwoon.coupangeatsproject.src.main.order.models

import com.google.gson.annotations.SerializedName

data class ResultOrdering(
    @SerializedName("selectMenuOrderResult") val selectMenuOrderResult: ArrayList<ResultOrderingMenu>,
    @SerializedName("selectOptionOrderResult") val selectOptionOrderResult: ArrayList<ResultOrderingOption>
)
