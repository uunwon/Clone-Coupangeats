package com.yunwoon.coupangeatsproject.src.store.menu

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MenuFragmentAdapter (fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager)  {
    val TYPE_VERTICAL_VIEWPAGER = 1001
    val TYPE_HORIAONTALL_VIEWPAGER = 1002
    private var type : Int = 0
    private var count : Int = 0

    fun setType(type: Int) {
        this.type = type
    }

    fun setType(type: Int, count: Int) {
        this.type = type
        this.count = count
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        val fragment = when(position) {
            0 -> MenuFragment().newInstance()
            1 -> MenuFragment().newInstance()
            else -> MenuFragment().newInstance()
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title = when(position) {
            0 -> "대표메뉴"
            1-> "반반피자"
            else -> "대표메뉴"
        }
        return title
    }
}