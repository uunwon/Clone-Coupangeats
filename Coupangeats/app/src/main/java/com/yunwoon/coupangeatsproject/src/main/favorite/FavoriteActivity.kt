package com.yunwoon.coupangeatsproject.src.main.favorite

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityFavoriteBinding
import com.yunwoon.coupangeatsproject.src.main.favorite.models.FavoriteDetailResponse
import com.yunwoon.coupangeatsproject.src.main.favorite.models.FavoriteStoreResponse
import com.yunwoon.coupangeatsproject.util.favoriteRecycler.FavoriteAdapter
import com.yunwoon.coupangeatsproject.util.favoriteRecycler.FavoriteData

class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>(ActivityFavoriteBinding::inflate), FavoriteActivityView {
    private val loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)

    private lateinit var flayoutManager: LinearLayoutManager

    private lateinit var favoriteAdapter: FavoriteAdapter
    private val favoriteData = mutableListOf<FavoriteData>()
    private var favoriteDataCount : Int = 0

    private lateinit var favoriteMenu : Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolBar(binding.favoriteToolbar)

        setFavoriteView()
        setFavoriteRecyclerView()

        binding.favoriteImageButtonBack.setOnClickListener  { finish() }
        binding.favoriteButtonDefault.setOnClickListener { finish() }
    }

    private fun setFavoriteRecyclerView() {
        flayoutManager = LinearLayoutManager(this)
        binding.favoriteRecyclerView.layoutManager = flayoutManager

        favoriteAdapter = FavoriteAdapter(this)

        binding.favoriteRecyclerView.isNestedScrollingEnabled = true
        binding.favoriteRecyclerView.adapter = favoriteAdapter
    }


    private fun setFavoriteView() {
        if(loginJwtToken != null) {
            showLoadingDialog(this)
            FavoriteService(this).tryGetFavorite(loginJwtToken) // get 으로 찜 목록 받아오기
        }
    }

    override fun onGetFavoriteSuccess(favoriteStoreResponse: FavoriteStoreResponse) {
        val favoriteData : MenuItem = favoriteMenu.findItem(R.id.menu_favorite_modify)

        dismissLoadingDialog()
        if(!favoriteStoreResponse.isSuccess)
            showCustomToast("이미 찜한 가게입니다!")

        // 찜 가게 유무에 따라 뷰 설정
        if(favoriteStoreResponse.result.isNotEmpty()) {
            binding.anyFavoriteConstraintLayout.visibility = View.GONE
            binding.favoriteConstraintLayout.visibility = View.VISIBLE
            favoriteData.isVisible = true

            for(i in favoriteStoreResponse.result)
                FavoriteService(this).tryGetFavoriteDetail(i.restaurantId)
        }
        else {
            binding.anyFavoriteConstraintLayout.visibility = View.VISIBLE
            binding.favoriteConstraintLayout.visibility = View.GONE
            favoriteData.isVisible = false
        }
    }

    override fun onGetFavoriteDetailSuccess(favoriteDetailResponse: FavoriteDetailResponse) {
        val deliveryType: String

        if(favoriteDetailResponse.isSuccess) {
            val deliveryFee = favoriteDetailResponse.result.restaurantResult[0].delieveryFee.toInt()

            deliveryType = if(deliveryFee == 0)
                "무료배달"
            else
                "배달비 ${deliveryFee}원"

            favoriteDataCount++
            if(favoriteDetailResponse.result.restaurantResult[0].ratingAvg.length > 2)
                favoriteData.add(FavoriteData(favoriteDetailResponse.result.imgResult[0].imgUrl, favoriteDetailResponse.result.restaurantResult[0].name,
                favoriteDetailResponse.result.restaurantResult[0].ratingAvg.substring(0,3), "(${favoriteDetailResponse.result.restaurantResult[0].reviewCount})",
                "4.1km", "10-20분", deliveryType))
            else
                favoriteData.add(FavoriteData(favoriteDetailResponse.result.imgResult[0].imgUrl, favoriteDetailResponse.result.restaurantResult[0].name,
                    favoriteDetailResponse.result.restaurantResult[0].ratingAvg, "(${favoriteDetailResponse.result.restaurantResult[0].reviewCount})",
                    "4.1km", "10-20분", deliveryType))

            favoriteAdapter.favoriteData = favoriteData
            favoriteAdapter.notifyDataSetChanged()
            binding.favoriteTextCount.text = favoriteDataCount.toString()
        }
    }

    override fun onGetFavoriteDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetFavoriteFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 옵션 메뉴 생성 - 수정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) { this.favoriteMenu = menu }

        menuInflater.inflate(R.menu.menu_modify, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite_modify -> {
                showCustomToast("수정하시죠")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}