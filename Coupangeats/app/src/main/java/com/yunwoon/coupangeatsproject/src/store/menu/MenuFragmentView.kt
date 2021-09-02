package com.yunwoon.coupangeatsproject.src.store.menu

import com.yunwoon.coupangeatsproject.src.store.models.StoreCategoryFoodResponse

interface MenuFragmentView {
    fun onGetStoreCategoryFoodSuccess(response: StoreCategoryFoodResponse)

    fun onGetStoreCategoryFoodFailure(message: String)
}