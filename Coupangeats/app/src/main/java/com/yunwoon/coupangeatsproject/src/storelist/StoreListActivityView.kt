package com.yunwoon.coupangeatsproject.src.storelist

import com.yunwoon.coupangeatsproject.src.main.home.models.CategoryResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.HomeResponse
import com.yunwoon.coupangeatsproject.src.storelist.models.NewStoreResponse

interface StoreListActivityView {
    fun onGetCategoriesSuccess(response: CategoryResponse)

    fun onGetCategoriesFailure(message: String)

    fun onGetRestaurantswithCategorySuccess(response: HomeResponse, categoryId: Int)

    fun onGetRestaurantswithCategoryFailure(message: String)

    fun onGetNewRestaurantsSuccess(response: NewStoreResponse, categoryId: Int, order: String)

    fun onGetNewRestaurantsFailure(message: String)
}