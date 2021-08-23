package com.yunwoon.coupangeatsproject.src.main.favorite

import com.yunwoon.coupangeatsproject.src.main.favorite.models.FavoriteStoreResponse

interface FavoriteActivityView {
    fun onGetFavoriteSuccess(storeResponse: FavoriteStoreResponse)

    fun onGetFavoriteFailure(message: String)
}