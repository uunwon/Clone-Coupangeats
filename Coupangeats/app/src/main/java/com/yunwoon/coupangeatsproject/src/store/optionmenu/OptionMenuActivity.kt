package com.yunwoon.coupangeatsproject.src.store.optionmenu

import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityOptionMenuBinding
import com.yunwoon.coupangeatsproject.src.store.models.CartResponse
import com.yunwoon.coupangeatsproject.src.store.models.PostCartRequest

class OptionMenuActivity : BaseActivity<ActivityOptionMenuBinding>(ActivityOptionMenuBinding::inflate), OptionActivityView {
    private val loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)

    private lateinit var bottomAppBarLayout: AppBarLayout
    private var menuIndex = 0
    private lateinit var menuImage : String
    private lateinit var menuTitle : String
    private lateinit var menuDetail : String
    private lateinit var menuPrice : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        menuIndex = intent.getIntExtra("menuIndex", 0) // 메뉴 데이터 받아옴
        menuImage = intent.getStringExtra("menuImage").toString()
        menuTitle = intent.getStringExtra("menuTitle").toString()
        menuDetail = intent.getStringExtra("menuDetail").toString()
        menuPrice = intent.getStringExtra("menuPrice") .toString()

        bottomAppBarLayout = binding.menuAppBarLayoutBottom

        // 카트에 담기
        binding.cartToolbar.setOnClickListener { setCart() }
    }

    fun backToStore() { finish() }

    override fun onResume() {
        super.onResume()

        supportFragmentManager.beginTransaction().replace(R.id.menu_frame_layout,
            OptionMenuFragment(menuIndex, menuImage, menuTitle, menuDetail, menuPrice))
            .commitAllowingStateLoss()
    }

    // 카트 담기
    private fun setCart() {
        val postCartRequest = PostCartRequest(menuId = menuIndex, menuCounts = 1,
            optionId = ApplicationClass.sSharedPreferences.getInt("requiredOptionId", 0))
        if(loginJwtToken != null) {
            showLoadingDialog(this)
            OptionMenuActivityService(this).tryPostCart(loginJwtToken, postCartRequest)
        } else {
            showCustomToast("로그인이 필요한 작업입니다")
        }
    }

    override fun onPostCartSuccess(response: CartResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            showCustomToast("카트에 담았습니다")
            finish()
        } else {
            showCustomToast("카트 담기에 실패했습니다")
        }
    }

    override fun onPostCartFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}