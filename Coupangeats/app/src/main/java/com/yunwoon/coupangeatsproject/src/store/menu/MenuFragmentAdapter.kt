package com.yunwoon.coupangeatsproject.src.store.menu

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MenuFragmentAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    var fragments : ArrayList<Fragment> = ArrayList()

    val TYPE_VERTICAL_VIEWPAGER = 1001

    private var type : Int = 0
    private var count : Int = 0

    fun setType(type: Int) {
        this.type = type
    }

    fun setType(type: Int, count: Int) {
        this.type = type
        this.count = count
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)
    }

    fun removeFragment() {
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
    }

    override fun getItemCount(): Int  = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }



}