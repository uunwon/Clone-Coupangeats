package com.yunwoon.coupangeatsproject.src.main.order

import android.os.Bundle
import android.view.View
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentOrderedBinding

class OrderedFragment :
    BaseFragment<FragmentOrderedBinding>(FragmentOrderedBinding::bind, R.layout.fragment_ordered) {

    val orderedFragment = newInstance(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}