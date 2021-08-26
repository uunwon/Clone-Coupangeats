package com.yunwoon.coupangeatsproject.src.address

import com.yunwoon.coupangeatsproject.src.address.naverModels.naverResponse

interface NaverMapActivityView {

    fun onGetGeocodingSuccess(response: naverResponse)

    fun onGetGeocodingFailure(message: String)

}