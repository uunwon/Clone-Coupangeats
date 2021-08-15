package com.yunwoon.coupangeatsproject.src.main.order

import android.os.Bundle
import android.view.View
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentOrderingBinding

class OrderingFragment :
    BaseFragment<FragmentOrderingBinding>(FragmentOrderingBinding::bind, R.layout.fragment_ordering) {

    val orderingFragment = newInstance(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.oderButtonViewOrdered.setOnClickListener {
            // 과거 주문 내역 페이지로 이동
            OrderFragment().changeTabContainer(0)
        }
    }
}