package com.yunwoon.coupangeatsproject.src.main.home

import com.yunwoon.coupangeatsproject.src.main.home.models.CategoryResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.HomeResponse

interface HomeFragmentView {
    fun onGetCategoriesSuccess(response: CategoryResponse)

    fun onGetCategoriesFailure(message: String)

    fun onGetMainRestaurantsSuccess(response: HomeResponse)

    fun onGetMainRestaurantsFailure(message: String)

    fun onGetOrderMainRestaurantsSuccess(response: HomeResponse, order: String)

    fun onGetOrderMainRestaurantsFailure(message: String)

    fun onGetOrderRestaurantsSuccess(response: HomeResponse, order: String)

    fun onGetOrderRestaurantsFailure(message: String)
}