package com.yunwoon.coupangeatsproject.src.store.optionmenu

import com.yunwoon.coupangeatsproject.src.store.models.OptionMenuCategoryResponse
import com.yunwoon.coupangeatsproject.src.store.models.OptionMenuResponse

interface OptionMenuView {

    fun onGetOptionMenuCategoriesSuccess(responseMenu: OptionMenuCategoryResponse)

    fun onGetOptionMenuCategoriesFailure(message: String)

    fun onGetOptionMenusSuccess(responseMenu: OptionMenuResponse)

    fun onGetOptionMenusFailure(message: String)

}