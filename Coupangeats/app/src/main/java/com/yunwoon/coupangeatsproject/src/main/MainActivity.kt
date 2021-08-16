package com.yunwoon.coupangeatsproject.src.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityMainBinding
import com.yunwoon.coupangeatsproject.src.main.favorite.FavoriteActivity
import com.yunwoon.coupangeatsproject.src.main.home.HomeFragment
import com.yunwoon.coupangeatsproject.src.main.login.BottomLoginDialog
import com.yunwoon.coupangeatsproject.src.main.order.OrderFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                        this.startActivity(Intent(this, FavoriteActivity::class.java))
                    }
                    R.id.menu_main_btm_nav_order -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame_layout, OrderFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_btm_nav_my_page -> {
                        // 로그인 상태 체크
                        setLoginDialog()
                    }
                }
                false
            }
        )
    }

    override fun onRestart() {
        super.onRestart()
        val token = ApplicationClass.sSharedPreferences.getString("jwt", null)

        Log.d("MainActivity", "$token")
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame_layout, HomeFragment())
            .commitAllowingStateLoss()
    }

    // 로그인 창 띄우기
    private fun setLoginDialog() {
        val dialogBottomLogin = BottomLoginDialog()
        dialogBottomLogin.show(supportFragmentManager, "BottomLoginDialog")
    }
}