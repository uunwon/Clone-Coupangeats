package com.yunwoon.coupangeatsproject.src.main.favorite

import android.os.Bundle
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityFavoriteBinding
import com.yunwoon.coupangeatsproject.src.main.home.HomeFragment

class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>(ActivityFavoriteBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolBar(binding.favoriteToolbar)
        binding.favoriteImageButtonBack.setOnClickListener {
            finish()
        }

        binding.favoriteButtonDefault.setOnClickListener {
            finish()
            supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout, HomeFragment())
                .commitAllowingStateLoss()
            // this.startActivity(Intent(this, MainActivity::class.java))
        }
    }
}