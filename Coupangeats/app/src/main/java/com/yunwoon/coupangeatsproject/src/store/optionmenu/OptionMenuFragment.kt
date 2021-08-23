package com.yunwoon.coupangeatsproject.src.store.optionmenu

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentOptionMenuBinding
import com.yunwoon.coupangeatsproject.src.store.models.OptionMenuCategoryResponse
import com.yunwoon.coupangeatsproject.src.store.models.OptionMenuResponse
import com.yunwoon.coupangeatsproject.util.menuDetailRecycler.MenuCheckData
import com.yunwoon.coupangeatsproject.util.menuDetailRecycler.MenuDetailAdapter
import com.yunwoon.coupangeatsproject.util.menuDetailRecycler.MenuDetailData
import com.yunwoon.coupangeatsproject.util.menuDetailRecycler.MenuRadioData
import kotlin.math.abs

class OptionMenuFragment(private val menuIndex: Int) :
    BaseFragment<FragmentOptionMenuBinding>(FragmentOptionMenuBinding::bind, R.layout.fragment_option_menu)
    , AppBarLayout.OnOffsetChangedListener, OptionMenuView {
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
        setOptionMenuCategory()

        binding.storeImageButtonBack.setOnClickListener {
            (context as OptionMenuActivity).backToStore()
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

    // 옵션 메뉴 카테고리 설정
    private fun setOptionMenuCategory() {
        mlayoutManager = LinearLayoutManager(requireContext())

        binding.menuRecyclerView.layoutManager = mlayoutManager
        binding.menuRecyclerView.isNestedScrollingEnabled = true

        menuDetailAdapter = MenuDetailAdapter(requireContext())
        binding.menuRecyclerView.adapter = menuDetailAdapter

        showLoadingDialog(requireContext())
        OptionMenuService(this).tryGetOptionMenuCategories(menuIndex)
    }

    override fun onGetOptionMenuCategoriesSuccess(response: OptionMenuCategoryResponse) {
        dismissLoadingDialog()
        if(response.isSuccess && response.result.isNotEmpty()) {
            for(i in response.result) {
                showLoadingDialog(requireContext())
                OptionMenuService(this).tryGetOptionMenus(menuIndex, i.id)
                menuDetailData.add(MenuDetailData(i.categoryName, i.isRequired, menuRadioData, menuCheckData))
            }
        }
        menuDetailAdapter.menuDetailDataArrayList = menuDetailData
        menuDetailAdapter.notifyDataSetChanged()
        Log.d("OptionMenuFragment", "menuRadioData = $menuRadioData")
        Log.d("OptionMenuFragment", "menuCheckData = $menuCheckData")
    }

    override fun onGetOptionMenusSuccess(response: OptionMenuResponse) {
        dismissLoadingDialog()
        if(response.isSuccess && response.result.isNotEmpty()) {
            for(i in response.result) {
                if(i.isRequired == 1) // 필수
                    menuRadioData.add(MenuRadioData(false, i.optionName, i.price.toInt()))
                else
                    menuCheckData.add(MenuCheckData(false, i.optionName, i.price.toInt()))
            }
        }
    }

    override fun onGetOptionMenuCategoriesFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetOptionMenusFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}