package com.yunwoon.coupangeatsproject.src.store.optionmenu

import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityOptionMenuBinding
import com.yunwoon.coupangeatsproject.src.cart.CartActivity

class OptionMenuActivity : BaseActivity<ActivityOptionMenuBinding>(ActivityOptionMenuBinding::inflate) {
    private lateinit var bottomAppBarLayout: AppBarLayout
    private var menuIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        menuIndex = intent.getIntExtra("menuIndex", 0) // 메뉴 인덱스 받아옴
        bottomAppBarLayout = binding.menuAppBarLayoutBottom

        binding.cartToolbar.setOnClickListener {
            this.startActivity(Intent(this, CartActivity::class.java))
        }
    }

    fun backToStore() { finish() }

    override fun onResume() {
        super.onResume()

        supportFragmentManager.beginTransaction().replace(R.id.menu_frame_layout, OptionMenuFragment(menuIndex))
            .commitAllowingStateLoss()
    }
}