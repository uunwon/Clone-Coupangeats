package com.yunwoon.coupangeatsproject.src.address

import com.yunwoon.coupangeatsproject.BuildConfig
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.address.models.AddressPostResponse
import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse
import com.yunwoon.coupangeatsproject.src.address.models.AddressSearchResponse
import com.yunwoon.coupangeatsproject.src.address.models.PostAddressRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressService(val view: AddressActivityView) {
    private val key = BuildConfig.ROAD_API_KEY

    // 사용자 주소 받아오기
    fun tryGetAddress(loginJwtToken: String) {
        val addressRetrofitInterface = ApplicationClass.sRetrofit.create(AddressRetrofitInterface::class.java)
        addressRetrofitInterface.getAddress(loginJwtToken).enqueue(object : Callback<AddressResponse>{
            override fun onResponse(call: Call<AddressResponse>, response: Response<AddressResponse>) {
                if(response.body() != null)
                    view.onGetAddressSuccess(response.body() as AddressResponse)
                else
                    view.onGetAddressFailure("주소를 받아오는데 실패했습니다")
            }
            override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                view.onGetAddressFailure(t.message ?: "통신 오류")
            }
        })
    }

    // 사용자 주소 생성하기
    fun tryPostAddress(loginJwtToken: String, postAddressRequest: PostAddressRequest) {
        val addressRetrofitInterface = ApplicationClass.sRetrofit.create(AddressRetrofitInterface::class.java)
        addressRetrofitInterface.postAddress(loginJwtToken, postAddressRequest).enqueue(object : Callback<AddressPostResponse>{
            override fun onResponse(call: Call<AddressPostResponse>, response: Response<AddressPostResponse>) {
                if(response.body() != null)
                    view.onPostAddressSuccess(response.body() as AddressPostResponse)
                else
                    view.onPostAddressFailure("주소 생성에 실패했습니다")
            }
            override fun onFailure(call: Call<AddressPostResponse>, t: Throwable) {
                view.onPostAddressFailure(t.message ?: "통신 오류")
            }
        })
    }

    // 주소 검색 결과 받아오기
    fun tryGetAddressSearch(address: String) {
        val addressRetrofitInterface = ApplicationClass.roadRetrofit.create(AddressRetrofitInterface::class.java)
        addressRetrofitInterface.getRoad(key, 1, 10, address, "json").enqueue(object : Callback<AddressSearchResponse>{
            override fun onResponse(call: Call<AddressSearchResponse>, searchResponse: Response<AddressSearchResponse>) {
                view.onGetAddressSearchSuccess(searchResponse.body() as AddressSearchResponse)
            }
            override fun onFailure(call: Call<AddressSearchResponse>, t: Throwable) {
                view.onGetAddressSearchFailure(t.message ?: "통신 오류")
            }
        })
    }
}