package com.yunwoon.coupangeatsproject.src.storelist

import com.yunwoon.coupangeatsproject.src.main.home.models.CategoryResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.HomeResponse

interface StoreListActivityView {
    fun onGetCategoriesSuccess(response: CategoryResponse)

    fun onGetCategoriesFailure(message: String)

    fun onGetRestaurantsSuccess(response: HomeResponse)

    fun onGetRestaurantsFailure(message: String)
}