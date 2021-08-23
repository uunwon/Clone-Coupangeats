package com.yunwoon.coupangeatsproject.src.main.favorite.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class FavoriteStoreResponse(
    @SerializedName("result") val resultStore: ArrayList<ResultFavoriteStore>
) : BaseResponse()