package com.yunwoon.coupangeatsproject.src.address

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressService(val view: AddressActivityView) {

    private val key = "devU01TX0FVVEgyMDIxMDgxNjAxMDEzMTExMTUyNTU="

    fun tryGetAddress(address: String) {
        val addressRetrofitInterface = ApplicationClass.roadRetrofit.create(AddressRetrofitInterface::class.java)
        addressRetrofitInterface.getRoad(key, 1, 10, address, "json").enqueue(object : Callback<AddressResponse>{
            override fun onResponse(call: Call<AddressResponse>, response: Response<AddressResponse>) {
                view.onGetAddressSuccess(response.body() as AddressResponse)
            }

            override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                view.onPostAddressFailure(t.message ?: "통신 오류")
            }

        })
    }

}