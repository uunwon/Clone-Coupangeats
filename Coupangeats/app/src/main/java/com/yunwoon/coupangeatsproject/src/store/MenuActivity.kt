package com.yunwoon.coupangeatsproject.src.store

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityMenuBinding
import kotlin.math.abs

class MenuActivity : BaseActivity<ActivityMenuBinding>(ActivityMenuBinding::inflate), AppBarLayout.OnOffsetChangedListener {
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var bottomAppBarLayout: AppBarLayout

    private lateinit var menuIconDrawable: Drawable

    private lateinit var whiteFilter: PorterDuffColorFilter
    private lateinit var blackFilter: PorterDuffColorFilter

    private lateinit var mlayoutManager: LinearLayoutManager // 수평 레이아웃 매니저

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initMenuView()
        setMenuRecyclerView()

        binding.cartAppBarLayout.setOnClickListener {
            showCustomToast("카트에 담아보세요")
        }
    }

    // 메뉴 디테일 화면 세팅
    private fun initMenuView() {
        setSupportActionBar(binding.toolbar)

        appBarLayout = binding.appBarLayout
        bottomAppBarLayout = binding.cartAppBarLayout

        whiteFilter = PorterDuffColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
        blackFilter = PorterDuffColorFilter(resources.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP)
    }

    // 앱 바 on/off 세팅
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (appBarLayout != null) {
            when {
                //  State Expanded
                verticalOffset == 0 -> {
                    binding.storeImageButtonBack.setBackgroundResource(R.drawable.ic_toolbar_back_white)
                    binding.storeTextToolbar.setTextColor(resources.getColor(R.color.transparency))
                    menuIconDrawable.colorFilter = whiteFilter
                }
                //  State Collapsed
                abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                    binding.storeImageButtonBack.setBackgroundResource(R.drawable.ic_toolbar_back)
                    binding.storeTextToolbar.setTextColor(resources.getColor(R.color.black))
                    menuIconDrawable.colorFilter = blackFilter
                }
            }
        }
    }

    // 옵션 메뉴 세팅
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_store_detail, menu)
        if(menu != null) {
            menuIconDrawable = menu.getItem(0).icon
        }
        menuIconDrawable.mutate()

        appBarLayout.addOnOffsetChangedListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_store_detail_share -> {
                showCustomToast("menu_store_detail_share")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 메뉴 디테일 리스트 세팅
    private fun setMenuRecyclerView() {
        mlayoutManager = LinearLayoutManager(this)

        binding.menuRecyclerView.layoutManager = mlayoutManager
        binding.menuRecyclerView.isNestedScrollingEnabled = true

        /* menuAdapter = MenuAdapter(this)
        binding.storeRecyclerViewMenu.adapter = menuAdapter

        val resources: Resources = this.resources
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_store_menu)

        menuData.apply {
            add(MenuData("고르곤졸라 피자", "23,000",
                "이탈리아의 대표적인 블루치즈를 사용하여 치즈의 깊고 진한 맛을 느낄 수 있는 이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자", bitmap))
        }

        menuAdapter.menuDataArrayList = menuData */
    }
}