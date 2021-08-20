package com.yunwoon.coupangeatsproject.src.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityMainBinding
import com.yunwoon.coupangeatsproject.src.main.favorite.FavoriteActivity
import com.yunwoon.coupangeatsproject.src.main.home.HomeFragment
import com.yunwoon.coupangeatsproject.src.main.login.BottomLoginDialog
import com.yunwoon.coupangeatsproject.src.main.mypage.MyPageFragment
import com.yunwoon.coupangeatsproject.src.main.order.OrderFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private var loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)
    private lateinit var bottomAppBarLayout: AppBarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "$loginJwtToken")
        // bottomAppBarLayout = binding.cartAppBarLayoutBottom

        supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBottomNavigation.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_main_btm_nav_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame_layout, HomeFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_btm_nav_search -> {
                    }
                    R.id.menu_main_btm_nav_favorite -> {
                        if(loginJwtToken != null)
                            this.startActivity(Intent(this, FavoriteActivity::class.java))
                        else
                            setLoginDialog()
                    }
                    R.id.menu_main_btm_nav_order -> {
                        if(loginJwtToken != null) {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.main_frame_layout, OrderFragment())
                                .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }
                        else
                            setLoginDialog()
                    }
                    R.id.menu_main_btm_nav_my_page -> {
                        if(loginJwtToken != null) {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.main_frame_layout, MyPageFragment())
                                .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }
                        else
                            setLoginDialog()
                    }
                }
                false
            }
        )
    }

    override fun onResume() {
        super.onResume()
        setHomeFragment()
    }

    // 홈 프래그먼트 화면 띄우기
    fun setHomeFragment() {
        loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)
        Log.d("MainActivity", "$loginJwtToken")

        binding.mainBottomNavigation.selectedItemId = R.id.menu_main_btm_nav_home
    }

    // 로그인 창 띄우기
    private fun setLoginDialog() {
        val dialogBottomLogin = BottomLoginDialog()
        dialogBottomLogin.show(supportFragmentManager, "BottomLoginDialog")
    }
}