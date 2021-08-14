package com.yunwoon.coupangeatsproject.src.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityMainBinding
import com.yunwoon.coupangeatsproject.src.main.home.HomeFragment
import com.yunwoon.coupangeatsproject.src.main.order.OrderFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout, HomeFragment()).commitAllowingStateLoss()

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
                    }
                    R.id.menu_main_btm_nav_order -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame_layout, OrderFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_btm_nav_my_page -> {

                    }
                }
                false
            }
        )
    }
}