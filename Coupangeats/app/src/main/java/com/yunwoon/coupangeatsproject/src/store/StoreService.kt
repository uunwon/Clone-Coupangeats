package com.yunwoon.coupangeatsproject.src.store

import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.cart.CartRetrofitInterface
import com.yunwoon.coupangeatsproject.src.cart.models.UserCartResponse
import com.yunwoon.coupangeatsproject.src.main.favorite.FavoriteRetrofitInterface
import com.yunwoon.coupangeatsproject.src.main.favorite.models.FavoriteStoreResponse
import com.yunwoon.coupangeatsproject.src.reviewlist.ReviewListRetrofitInterface
import com.yunwoon.coupangeatsproject.src.reviewlist.models.ReviewListResponse
import com.yunwoon.coupangeatsproject.src.store.models.FavoriteResponse
import com.yunwoon.coupangeatsproject.src.store.models.PostFavoriteRequest
import com.yunwoon.coupangeatsproject.src.store.models.StoreCategoryResponse
import com.yunwoon.coupangeatsproject.src.store.models.StoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreService(val view: StoreActivityView) {

    fun tryGetStore(restaurantId: Int){
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(StoreRetrofitInterface::class.java)
        storeRetrofitInterface.getStore(restaurantId).enqueue(object : Callback<StoreResponse> {
            override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                if(response.body() != null)
                    view.onGetStoreSuccess(response.body() as StoreResponse)
                else
                    view.onGetStoreFailure("가게를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                view.onGetStoreFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetFavorite(loginJwtToken: String){
        val favoriteRetrofitInterface = ApplicationClass.sRetrofit.create(FavoriteRetrofitInterface::class.java)
        favoriteRetrofitInterface.getFavorite(loginJwtToken).enqueue(object : Callback<FavoriteStoreResponse> {
            override fun onResponse(call: Call<FavoriteStoreResponse>, favoriteStoreResponse: Response<FavoriteStoreResponse>) {
                if(favoriteStoreResponse.body() != null)
                    view.onGetFavoriteSuccess(favoriteStoreResponse.body() as FavoriteStoreResponse)
                else
                    view.onGetFavoriteFailure("즐겨찾기를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<FavoriteStoreResponse>, t: Throwable) {
                view.onGetFavoriteFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetReviews(restaurantId: Int, order: String){
        val reviewListRetrofitInterface = ApplicationClass.sRetrofit.create(ReviewListRetrofitInterface::class.java)
        reviewListRetrofitInterface.postReview(restaurantId, order).enqueue(object : Callback<ReviewListResponse> {
            override fun onResponse(call: Call<ReviewListResponse>, response: Response<ReviewListResponse>) {
                if(response.body() != null)
                    view.onGetReviewsSuccess(response.body() as ReviewListResponse)
                else
                    view.onGetReviewsFailure("리뷰 조회에 실패했습니다")
            }

            override fun onFailure(call: Call<ReviewListResponse>, t: Throwable) {
                view.onGetReviewsFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetStoreCategories(restaurantId: Int){
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(StoreRetrofitInterface::class.java)
        storeRetrofitInterface.getStoreCategories(restaurantId).enqueue(object : Callback<StoreCategoryResponse> {
            override fun onResponse(call: Call<StoreCategoryResponse>, response: Response<StoreCategoryResponse>) {
                if(response.body() != null)
                    view.onGetStoreCategoriesSuccess(response.body() as StoreCategoryResponse)
                else
                    view.onGetStoreCategoriesFailure("가게 카테고리를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<StoreCategoryResponse>, t: Throwable) {
                view.onGetStoreCategoriesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostFavorite(loginJwtToken: String, postFavoriteRequest : PostFavoriteRequest){
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(StoreRetrofitInterface::class.java)
        storeRetrofitInterface.postFavorite(loginJwtToken, postFavoriteRequest).enqueue(object : Callback<FavoriteResponse> {
            override fun onResponse(call: Call<FavoriteResponse>, response: Response<FavoriteResponse>) {
                if(response.body() != null)
                    view.onPostFavoriteSuccess(response.body() as FavoriteResponse)
                else
                    view.onPostFavoriteFailure("가게 즐겨찾기에 실패했습니다")
            }

            override fun onFailure(call: Call<FavoriteResponse>, t: Throwable) {
                view.onPostFavoriteFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetCart(loginJwtToken: String){
        val cartRetrofitInterface = ApplicationClass.sRetrofit.create(CartRetrofitInterface::class.java)
        cartRetrofitInterface.getCart(loginJwtToken).enqueue(object : Callback<UserCartResponse> {
            override fun onResponse(call: Call<UserCartResponse>, response: Response<UserCartResponse>) {
                if(response.body() != null)
                    view.onGetUserCartSuccess(response.body() as UserCartResponse)
                else
                    view.onGetUserCartFailure("사용자의 카트를 받아오는데 실패했습니다")
            }

            override fun onFailure(call: Call<UserCartResponse>, t: Throwable) {
                view.onGetUserCartFailure(t.message ?: "통신 오류")
            }
        })
    }
}