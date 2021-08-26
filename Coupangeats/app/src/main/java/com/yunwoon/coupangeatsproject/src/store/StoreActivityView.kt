package com.yunwoon.coupangeatsproject.src.store

import com.yunwoon.coupangeatsproject.src.cart.models.UserCartResponse
import com.yunwoon.coupangeatsproject.src.reviewlist.models.ReviewListResponse
import com.yunwoon.coupangeatsproject.src.store.models.FavoriteResponse
import com.yunwoon.coupangeatsproject.src.store.models.StoreCategoryResponse
import com.yunwoon.coupangeatsproject.src.store.models.StoreResponse

interface StoreActivityView {
    fun onGetStoreSuccess(response: StoreResponse)

    fun onGetStoreFailure(message: String)

    fun onGetReviewsSuccess(response: ReviewListResponse)

    fun onGetReviewsFailure(message: String)

    fun onGetStoreCategoriesSuccess(response: StoreCategoryResponse)

    fun onGetStoreCategoriesFailure(message: String)

    fun onPostFavoriteSuccess(response: FavoriteResponse)

    fun onPostFavoriteFailure(message: String)

    fun onGetUserCartSuccess(response: UserCartResponse)

    fun onGetUserCartFailure(message: String)
}