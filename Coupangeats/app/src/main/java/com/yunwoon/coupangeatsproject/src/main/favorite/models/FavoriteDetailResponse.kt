package com.yunwoon.coupangeatsproject.src.main.favorite.models

import com.google.gson.annotations.SerializedName
import com.yunwoon.coupangeatsproject.config.BaseResponse

data class FavoriteDetailResponse(
    @SerializedName("result") val result : ResultFavoriteDetail
) : BaseResponse()