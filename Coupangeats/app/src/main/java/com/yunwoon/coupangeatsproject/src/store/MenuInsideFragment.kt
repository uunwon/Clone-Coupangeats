package com.yunwoon.coupangeatsproject.src.store

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentMenuInsideBinding
import com.yunwoon.coupangeatsproject.util.menuDetailRecycler.MenuCheckData
import com.yunwoon.coupangeatsproject.util.menuDetailRecycler.MenuDetailAdapter
import com.yunwoon.coupangeatsproject.util.menuDetailRecycler.MenuDetailData
import com.yunwoon.coupangeatsproject.util.menuDetailRecycler.MenuRadioData
import kotlin.math.abs

class MenuInsideFragment :
    BaseFragment<FragmentMenuInsideBinding>(FragmentMenuInsideBinding::bind, R.layout.fragment_menu_inside), AppBarLayout.OnOffsetChangedListener {
    private lateinit var appBarLayout: AppBarLayout

    private lateinit var whiteFilter: PorterDuffColorFilter
    private lateinit var blackFilter: PorterDuffColorFilter

    private lateinit var mlayoutManager: LinearLayoutManager // 옵션 메뉴
    private lateinit var menuDetailAdapter: MenuDetailAdapter

    private val menuDetailData = mutableListOf<MenuDetailData>()
    private val menuRadioData = mutableListOf<MenuRadioData>()
    private val menuCheckData = mutableListOf<MenuCheckData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenuView()
        setMenuRecyclerView()

        binding.storeImageButtonBack.setOnClickListener {
            (context as MenuActivity).backToStore()
        }
    }

    // 메뉴 디테일 화면 세팅
    private fun initMenuView() {
        whiteFilter = PorterDuffColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
        blackFilter = PorterDuffColorFilter(resources.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP)

        setToolBar(binding.toolbar)
        appBarLayout = binding.appBarLayout
        appBarLayout.addOnOffsetChangedListener(this)
    }

    // 앱 바 on/off 세팅
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (appBarLayout != null) {
            Log.d("MenuInsideFragment", "onOffsetChanged appbarlayout not null")
            when {
                //  State Expanded
                verticalOffset == 0 -> {
                    binding.storeImageButtonBack.setBackgroundResource(R.drawable.ic_toolbar_back_white)
                    binding.storeTextToolbar.setTextColor(resources.getColor(R.color.transparency))
                }
                //  State Collapsed
                abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                    binding.storeImageButtonBack.setBackgroundResource(R.drawable.ic_toolbar_back)
                    binding.storeTextToolbar.setTextColor(resources.getColor(R.color.black))
                }
            }
        }
    }

    // 메뉴 디테일 리스트 세팅
    private fun setMenuRecyclerView() {
        mlayoutManager = LinearLayoutManager(requireContext())

        binding.menuRecyclerView.layoutManager = mlayoutManager
        binding.menuRecyclerView.isNestedScrollingEnabled = true

        menuDetailAdapter = MenuDetailAdapter(requireContext())
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