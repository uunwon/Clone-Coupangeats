package com.yunwoon.coupangeatsproject.src.store

import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityMenuBinding
import com.yunwoon.coupangeatsproject.src.cart.CartActivity

class MenuActivity : BaseActivity<ActivityMenuBinding>(ActivityMenuBinding::inflate) {
    private lateinit var bottomAppBarLayout: AppBarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomAppBarLayout = binding.menuAppBarLayoutBottom

        binding.cartToolbar.setOnClickListener {
            this.startActivity(Intent(this, CartActivity::class.java))
        }
    }

    fun backToStore() { finish() }

    override fun onResume() {
        super.onResume()

        supportFragmentManager.beginTransaction().replace(R.id.menu_frame_layout, MenuInsideFragment())
            .commitAllowingStateLoss()
    }
}