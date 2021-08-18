package com.yunwoon.coupangeatsproject.src.main.order

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class OrderFragmentAdapter (fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        val fragment = when(position) {
            0 -> OrderedFragment().newInstance()
            1 -> OrderingFragment().newInstance()
            else -> OrderedFragment().newInstance()
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title = when(position) {
            0 -> "과거 주문 내역"
            1-> "준비중"
            else -> "과거 주문 내역"
        }
        return title
    }
}