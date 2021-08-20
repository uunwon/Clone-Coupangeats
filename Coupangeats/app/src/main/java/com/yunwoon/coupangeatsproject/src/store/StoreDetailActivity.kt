package com.yunwoon.coupangeatsproject.src.store

import android.os.Bundle
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityStoreDetailBinding

class StoreDetailActivity : BaseActivity<ActivityStoreDetailBinding>(ActivityStoreDetailBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.storeDetailImageButtonBack.setOnClickListener { finish() }
    }
}