package com.yunwoon.coupangeatsproject.src.main.order

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentOrderingBinding
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderData
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderMenuData
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderingAdapter

class OrderingFragment :
    BaseFragment<FragmentOrderingBinding>(FragmentOrderingBinding::bind, R.layout.fragment_ordering) {

    private lateinit var orderViewPager: ViewPager

    private lateinit var olayoutManager : LinearLayoutManager
    private lateinit var orderingAdapter : OrderingAdapter

    private val orderData = mutableListOf<OrderData>()
    private val orderMenuData = mutableListOf<OrderMenuData>()

    fun newInstance() : OrderingFragment {
        val args = Bundle()
        val frag = OrderingFragment()
        frag.arguments = args
        return frag
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOrderRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        orderViewPager = requireActivity().findViewById(R.id.order_view_pager)

        binding.orderingButtonViewOrdered.setOnClickListener {
            orderViewPager.currentItem = 0
        }
    }

    private fun setOrderRecyclerView() {
        Log.d("OrderedFragment", "setOrderRecyclerView 호출")

        olayoutManager = LinearLayoutManager(requireContext())
        binding.orderingRecyclerView.layoutManager = olayoutManager

        orderingAdapter = OrderingAdapter(requireContext())
        binding.orderingRecyclerView.isNestedScrollingEnabled = true
        binding.orderingRecyclerView.adapter = orderingAdapter

        val resources : Resources = this.resources
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_favorite_store)

        orderMenuData.apply {
            add(OrderMenuData(1, "스노윙 치킨 한마리"))
            add(OrderMenuData(2, "상콤 치즈볼"))
        }

        orderData.apply {
            add(OrderData("굽네치킨 문래점", bitmap, "2021-08-20 오후 08:45", 5, orderMenuData, "14,000원", false))
        }

        orderingAdapter.orderData = orderData
    }

}