package com.yunwoon.coupangeatsproject.src.store

import com.yunwoon.coupangeatsproject.src.store.models.StoreResponse

interface StoreActivityView {
    fun onGetStoreSuccess(response: StoreResponse)

    fun onGetStoreFailure(message: String)
}