package com.yunwoon.coupangeatsproject.src.store.menu

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentMenuBinding
import com.yunwoon.coupangeatsproject.util.menuRecycler.MenuAdapter
import com.yunwoon.coupangeatsproject.util.menuRecycler.MenuData

class MenuFragment  :
    BaseFragment<FragmentMenuBinding>(FragmentMenuBinding::bind, R.layout.fragment_menu) {
    private lateinit var mlayoutManager: LinearLayoutManager

    private lateinit var menuAdapter: MenuAdapter
    private val menuData = mutableListOf<MenuData>()

    fun newInstance() : MenuFragment {
        val args = Bundle()
        val frag = MenuFragment()
        frag.arguments = args
        return frag
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMenuRecyclerView()
    }

    // 메뉴 RecyclerView 세팅
    private fun setMenuRecyclerView() {
        mlayoutManager = LinearLayoutManager(requireContext())

        binding.storeRecyclerViewMenu.layoutManager = mlayoutManager
        binding.storeRecyclerViewMenu.isNestedScrollingEnabled = true

        menuAdapter = MenuAdapter(requireContext())
        binding.storeRecyclerViewMenu.adapter = menuAdapter

        val resources: Resources = this.resources
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_store_menu)

        menuData.apply {
            add(MenuData("고르곤졸라 피자", "23,000",
                "이탈리아의 대표적인 블루치즈를 사용하여 치즈의 깊고 진한 맛을 느낄 수 있는 이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자", bitmap))
            add(MenuData("고르곤졸라 피자", "23,000",
                "이탈리아의 대표적인 블루치즈를 사용하여 치즈의 깊고 진한 맛을 느낄 수 있는 이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자", bitmap))
            add(MenuData("고르곤졸라 피자", "23,000",
                "이탈리아의 대표적인 블루치즈를 사용하여 치즈의 깊고 진한 맛을 느낄 수 있는 이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자", bitmap))
            add(MenuData("고르곤졸라 피자", "23,000",
                "이탈리아의 대표적인 블루치즈를 사용하여 치즈의 깊고 진한 맛을 느낄 수 있는 이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자", bitmap))
            add(MenuData("고르곤졸라 피자", "23,000",
                "이탈리아의 대표적인 블루치즈를 사용하여 치즈의 깊고 진한 맛을 느낄 수 있는 이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자", bitmap))
            add(MenuData("고르곤졸라 피자", "23,000",
                "이탈리아의 대표적인 블루치즈를 사용하여 치즈의 깊고 진한 맛을 느낄 수 있는 이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자이태리 정통피자", bitmap))
        }

        menuAdapter.menuDataArrayList = menuData
    }
}