package com.yunwoon.coupangeatsproject.src.main.order

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentOrderingBinding

class OrderingFragment :
    BaseFragment<FragmentOrderingBinding>(FragmentOrderingBinding::bind, R.layout.fragment_ordering) {
    private lateinit var orderViewPager: ViewPager

    fun newInstance() : OrderingFragment {
        val args = Bundle()
        val frag = OrderingFragment()
        frag.arguments = args
        return frag
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderViewPager = requireActivity().findViewById(R.id.order_view_pager)

        binding.orderButtonViewOrdered.setOnClickListener {
            orderViewPager.currentItem = 0
        }
    }
}