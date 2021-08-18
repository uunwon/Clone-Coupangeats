package com.yunwoon.coupangeatsproject.src.store

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.WindowManager
import com.google.android.material.appbar.AppBarLayout
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityStoreBinding
import kotlin.math.abs

class StoreActivity : BaseActivity<ActivityStoreBinding>(ActivityStoreBinding::inflate), AppBarLayout.OnOffsetChangedListener {
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var menuIconDrawable1: Drawable
    private lateinit var menuIconDrawable2: Drawable
    private lateinit var whiteFilter: PorterDuffColorFilter
    private lateinit var blackFilter: PorterDuffColorFilter
    private lateinit var redFilter: PorterDuffColorFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStoreView()
    }

    // store 화면 세팅
    private fun initStoreView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        setSupportActionBar(binding.toolbar)

        binding.nestedScrollView.fullScroll(View.FOCUS_UP)
        binding.nestedScrollView.scrollTo(0,0)

        appBarLayout = binding.appBarLayout

        whiteFilter = PorterDuffColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
        blackFilter = PorterDuffColorFilter(resources.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP)
        redFilter = PorterDuffColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_store, menu)
        if(menu != null) {
            menuIconDrawable1 = menu.getItem(0).icon
            menuIconDrawable2 = menu.getItem(1).icon
        }
        menuIconDrawable1.mutate()
        menuIconDrawable2.mutate()

        appBarLayout.addOnOffsetChangedListener(this)
        return true
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (appBarLayout != null) {
            when {
                //  State Expanded
                verticalOffset == 0 -> {
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_toolbar_back_white)
                    menuIconDrawable1.colorFilter = whiteFilter
                    menuIconDrawable2.colorFilter = whiteFilter
                    window.decorView.systemUiVisibility = 0
                }
                //  State Collapsed
                abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_toolbar_back)
                    menuIconDrawable1.colorFilter = blackFilter
                    menuIconDrawable2.colorFilter = redFilter
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }
    }
}