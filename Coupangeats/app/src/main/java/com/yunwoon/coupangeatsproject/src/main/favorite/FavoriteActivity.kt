package com.yunwoon.coupangeatsproject.src.main.favorite

import android.os.Bundle
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityFavoriteBinding

class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>(ActivityFavoriteBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolBar(binding.favoriteToolbar)
        binding.favoriteImageButtonBack.setOnClickListener {
            finish()
        }

        binding.favoriteButtonDefault.setOnClickListener {
            finish()
        }
    }
}