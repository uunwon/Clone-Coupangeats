package com.yunwoon.coupangeatsproject.util.menuDetailRecycler

data class MenuDetailData (
    val menuDetailTitle : String,
    val menuDetailNecessary : Int,
    val menuRadioData: MutableList<MenuRadioData>,
    val menuCheckData: MutableList<MenuCheckData>
)