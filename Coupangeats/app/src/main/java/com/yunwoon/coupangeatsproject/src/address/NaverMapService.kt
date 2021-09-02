package com.yunwoon.coupangeatsproject.src.address

import com.yunwoon.coupangeatsproject.BuildConfig
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.address.naverModels.naverResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NaverMapService(val view : NaverMapActivityView) {
    private val id = BuildConfig.NAVER_CLIENT_ID
    private val key = BuildConfig.NAVER_CLIENT_SECRET_KEY

    // 좌표 -> 위치 정보 받아오기
    fun tryGetGeocoding(coords: String) {
        val naverRetrofitInterface = ApplicationClass.geoRetrofit.create(NaverMapRetrofitInterface::class.java)
        naverRetrofitInterface.getGeocoding(id, key, coords).enqueue(object : Callback<naverResponse> {
            override fun onResponse(call: Call<naverResponse>, response: Response<naverResponse>) {
                if(response.body() != null)
                    view.onGetGeocodingSuccess(response.body() as naverResponse)
                else
                    view.onGetGeocodingFailure("지오코딩에 실패했습니다")
            }
            override fun onFailure(call: Call<naverResponse>, t: Throwable) {
                view.onGetGeocodingFailure(t.message ?: "통신 오류")
            }
        })
    }

}