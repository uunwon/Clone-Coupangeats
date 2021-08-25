package com.yunwoon.coupangeatsproject.src.store.optionmenu

import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityOptionMenuBinding
import com.yunwoon.coupangeatsproject.src.store.models.*

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
        ApplicationClass.sEditor.putInt("requiredOptionId", 0).apply()
        ApplicationClass.sEditor.putInt("checkOptionId", 0).apply()

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
        val postCartRequest = PostCartRequest(menuId = menuIndex, menuCounts = 1)
        val postCartwithOptionRequest = PostCartwithOptionRequest(menuId = menuIndex,
            menuCounts = ApplicationClass.sSharedPreferences.getInt("menuCount", 1),
            optionId = ApplicationClass.sSharedPreferences.getInt("requiredOptionId", 0))

        if(loginJwtToken != null) {
            if(menuIndex == 1 && ApplicationClass.sSharedPreferences.getInt("requiredOptionId", 0) == 0)
                showCustomToast("필수 옵션을 선택해주세요")
            else if(menuIndex == 1 && ApplicationClass.sSharedPreferences.getInt("requiredOptionId", 0) > 0 ) {
                showLoadingDialog(this)
                OptionMenuActivityService(this).tryPostCartwithOption(loginJwtToken, postCartwithOptionRequest)
            }
            else {
                showLoadingDialog(this)
                OptionMenuActivityService(this).tryPostCart(loginJwtToken, postCartRequest)
            }
        } else {
            showCustomToast("로그인이 필요한 작업입니다")
        }
    }

    // 일반 메인 카트 생성
    override fun onPostCartSuccess(response: CartResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            showCustomToast("메인 카트가 생성되었습니다")
            finish()
        } else {
            showCustomToast("카트 담기에 실패했습니다")
        }
    }

    // 옵션 메뉴 있는 메인 카트 생성
    override fun onPostCartwithOptionSuccess(response: CartwithOptionResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            showCustomToast("메인 카트가 생성되었습니다")
            if(ApplicationClass.sSharedPreferences.getInt("checkOptionId", 0) != 0 && loginJwtToken != null) {
                val postOptionCartRequest = PostOptionCartRequest(optionId = ApplicationClass.sSharedPreferences.getInt("checkOptionId", 0)
                    , cartId = response.result.cartResult)
                OptionMenuActivityService(this).tryPostOptionCart(loginJwtToken, postOptionCartRequest)
            }
            /* val postOptionCartRequest = PostOptionCartRequest(optionId = ApplicationClass.sSharedPreferences.getInt("requiredOptionId", 0)
                , cartId = response.result.cartResult)

            if(loginJwtToken != null)
                OptionMenuActivityService(this).tryPostOptionCart(loginJwtToken, postOptionCartRequest) */
        } else {
            showCustomToast("카트 담기에 실패했습니다")
        }
    }

    // 체크 박스 같은 옵션 카트 생성
    override fun onPostOptionCartSuccess(response: OptionCartResponse) {dismissLoadingDialog()
        if(response.isSuccess) {
            showCustomToast("옵션 카트가 생성되었습니다")
            finish()
        } else {
            showCustomToast("카트 담기에 실패했습니다")
        }
    }

    override fun onPostCartFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPostCartwithOptionFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPostOptionCartFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}