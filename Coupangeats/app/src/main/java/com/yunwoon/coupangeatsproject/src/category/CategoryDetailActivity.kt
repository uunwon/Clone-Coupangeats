package com.yunwoon.coupangeatsproject.src.category

import android.os.Bundle
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityCategoryDetailBinding

class CategoryDetailActivity  : BaseActivity<ActivityCategoryDetailBinding>(ActivityCategoryDetailBinding::inflate){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val position = intent.getIntExtra("position", 0)
        showCustomToast("$position")
    }
}