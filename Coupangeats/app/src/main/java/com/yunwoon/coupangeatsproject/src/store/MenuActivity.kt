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
import com.yunwoon.coupangeatsproject.util.menuDetailRecycler.MenuCheckData
import com.yunwoon.coupangeatsproject.util.menuDetailRecycler.MenuDetailAdapter
import com.yunwoon.coupangeatsproject.util.menuDetailRecycler.MenuDetailData
import com.yunwoon.coupangeatsproject.util.menuDetailRecycler.MenuRadioData
import kotlin.math.abs

class MenuActivity : BaseActivity<ActivityMenuBinding>(ActivityMenuBinding::inflate), AppBarLayout.OnOffsetChangedListener {
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var bottomAppBarLayout: AppBarLayout

    private lateinit var menuIconDrawable: Drawable

    private lateinit var whiteFilter: PorterDuffColorFilter
    private lateinit var blackFilter: PorterDuffColorFilter

    private lateinit var mlayoutManager: LinearLayoutManager // 옵션 메뉴
    private lateinit var menuDetailAdapter: MenuDetailAdapter
    private val menuDetailData = mutableListOf<MenuDetailData>()
    private val menuRadioData = mutableListOf<MenuRadioData>()
    private val menuCheckData = mutableListOf<MenuCheckData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initMenuView()
        setMenuRecyclerView()

        binding.cartToolbar.setOnClickListener {
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

        menuDetailAdapter = MenuDetailAdapter(this)
        binding.menuRecyclerView.adapter = menuDetailAdapter

        menuRadioData.apply {
            add(MenuRadioData(false, "필수 - 달콤씁쓸치즈볼"))
            add(MenuRadioData(true, "필수 - 달콤상콤치즈볼"))
        }

        menuCheckData.apply {
            add(MenuCheckData(false, "선택 - 달콤씁쓸치즈볼"))
            add(MenuCheckData(false, "선택 - 달콤상콤치즈볼"))
        }

        menuDetailData.apply {
            add(MenuDetailData("기본", "필수항목", menuRadioData, menuCheckData, true))
            add(MenuDetailData("선택", "", menuRadioData, menuCheckData, false))
        }

        menuDetailAdapter.menuDetailDataArrayList = menuDetailData
    }
}