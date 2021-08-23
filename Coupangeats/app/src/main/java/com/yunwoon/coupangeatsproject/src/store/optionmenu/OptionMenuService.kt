package com.yunwoon.coupangeatsproject.src.store.optionmenu

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.store.models.OptionMenuCategoryResponse
import com.yunwoon.coupangeatsproject.src.store.models.OptionMenuResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OptionMenuService(val view : OptionMenuView) {

    fun tryGetOptionMenuCategories(menuId: Int){
        val optionRetrofitInterface = ApplicationClass.sRetrofit.create(OptionRetrofitInterface::class.java)
        optionRetrofitInterface.getMenuOptionCategories(menuId).enqueue(object : Callback<OptionMenuCategoryResponse> {
            override fun onResponse(call: Call<OptionMenuCategoryResponse>, responseMenu: Response<OptionMenuCategoryResponse>) {
                if(responseMenu.body() != null)
                    view.onGetOptionMenuCategoriesSuccess(responseMenu.body() as OptionMenuCategoryResponse)
                else
                    view.onGetOptionMenuCategoriesFailure("옵션의 카테고리를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<OptionMenuCategoryResponse>, t: Throwable) {
                view.onGetOptionMenuCategoriesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetOptionMenus(menuId: Int, categoryId: Int){
        val optionRetrofitInterface = ApplicationClass.sRetrofit.create(OptionRetrofitInterface::class.java)
        optionRetrofitInterface.getOptionMenus(menuId, categoryId).enqueue(object : Callback<OptionMenuResponse> {
            override fun onResponse(call: Call<OptionMenuResponse>, responseMenu: Response<OptionMenuResponse>) {
                if(responseMenu.body() != null)
                    view.onGetOptionMenusSuccess(responseMenu.body() as OptionMenuResponse)
                else
                    view.onGetOptionMenusFailure("옵션의 카테고리를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<OptionMenuResponse>, t: Throwable) {
                view.onGetOptionMenusFailure(t.message ?: "통신 오류")
            }
        })
    }

}