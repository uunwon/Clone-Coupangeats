package com.yunwoon.coupangeatsproject.src.main.order

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentOrderBinding

class OrderFragment :
    BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::bind, R.layout.fragment_order){

    val orderFragment = newInstance(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.orderTabLayout.addTab(binding.orderTabLayout.newTab().setText("과거 주문 내역"))
        binding.orderTabLayout.addTab(binding.orderTabLayout.newTab().setText("준비중"))

        fragmentManager?.beginTransaction()?.replace(R.id.order_frame_layout, OrderedFragment())?.commitAllowingStateLoss()

        binding.orderTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                changeTabContainer(tab!!.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun changeTabContainer(position: Int) {
        when(position) {
            0 -> {
                Log.d("orderFragment", "changeTabContainer 들어옴")
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.order_frame_layout, OrderedFragment())
                    ?.commitAllowingStateLoss()
            }
            1 -> {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.order_frame_layout, OrderingFragment())
                    ?.commitAllowingStateLoss()
            }
            else -> {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.order_frame_layout, OrderedFragment())
                    ?.commitAllowingStateLoss()
            }
        }
    }
}