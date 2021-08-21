package com.yunwoon.coupangeatsproject.src.main.order

import android.os.Bundle
import android.util.Log
import android.view.View
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentOrderBinding

class OrderFragment :
    BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::bind, R.layout.fragment_order){
    private lateinit var orderFragmentAdapter: OrderFragmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("OrderFragment", "onViewCreated 호출")

        setViewPager()
    }

    private fun setViewPager() {
        Log.d("OrderFragment", "setViewPager 호출")

        orderFragmentAdapter = OrderFragmentAdapter(childFragmentManager)
        binding.orderViewPager.adapter = orderFragmentAdapter
        binding.orderTabLayout.setupWithViewPager(binding.orderViewPager)
    }
}