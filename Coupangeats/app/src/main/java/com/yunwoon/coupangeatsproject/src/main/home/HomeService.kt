package com.yunwoon.coupangeatsproject.src.main.home

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.address.AddressRetrofitInterface
import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.CategoryResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.HomeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService(val view: HomeFragmentView) {

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

    fun tryGetCategories(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getCategories().enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if(response.body() != null)
                    view.onGetCategoriesSuccess(response.body() as CategoryResponse)
                else
                    view.onGetCategoriesFailure("카테고리를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                view.onGetCategoriesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetMainRestaurants(){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getRestaurants().enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if(response.body() != null)
                    view.onGetMainRestaurantsSuccess(response.body() as HomeResponse)
                else
                    view.onGetMainRestaurantsFailure("전체 식당 리스트를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                view.onGetMainRestaurantsFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetOrderRestaurants(order : String) {
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getOrderRestaurants(order).enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if(response.body() != null)
                    view.onGetOrderRestaurantsSuccess(response.body() as HomeResponse, order)
                else
                    view.onGetOrderRestaurantsFailure("정렬된 전체 식당 리스트를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                view.onGetOrderRestaurantsFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetOrderMainRestaurants(order : String) {
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getOrderRestaurants(order).enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if(response.body() != null)
                    view.onGetOrderMainRestaurantsSuccess(response.body() as HomeResponse, order)
                else
                    view.onGetOrderMainRestaurantsFailure("정렬된 전체 식당 리스트를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                view.onGetOrderMainRestaurantsFailure(t.message ?: "통신 오류")
            }
        })
    }
}