package com.yunwoon.coupangeatsproject.src.main.order

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentOrderedBinding
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderAdapter
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderData
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderMenuData

class OrderedFragment :
    BaseFragment<FragmentOrderedBinding>(FragmentOrderedBinding::bind, R.layout.fragment_ordered) {

    private lateinit var olayoutManager : LinearLayoutManager
    private lateinit var orderAdapter : OrderAdapter

    private val orderData = mutableListOf<OrderData>()
    private val orderMenuData = mutableListOf<OrderMenuData>()

    fun newInstance() : OrderedFragment {
        val args = Bundle()
        val frag = OrderedFragment()
        frag.arguments = args
        return frag
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOrderRecyclerView()
    }

    private fun setOrderRecyclerView() {
        Log.d("OrderedFragment", "setOrderRecyclerView 호출")

        olayoutManager = LinearLayoutManager(requireContext())
        binding.orderedRecyclerView.layoutManager = olayoutManager

        orderAdapter = OrderAdapter(requireContext())
        binding.orderedRecyclerView.isNestedScrollingEnabled = true
        binding.orderedRecyclerView.adapter = orderAdapter

        val resources : Resources = this.resources
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_favorite_store)

        orderMenuData.apply {
            add(OrderMenuData(1, "고추 바사삭 한마리"))
            add(OrderMenuData(2, "달콤 치즈볼"))
        }

        orderData.apply {
            add(OrderData("굽네치킨 문래점", bitmap, "2021-08-20 오후 08:45", 5, orderMenuData, "14,000원", false))
            add(OrderData("네네치킨 문래점", bitmap, "2021-08-17 오후 08:45", 2, orderMenuData, "24,000원", false))
            add(OrderData("교촌치킨 문래점", bitmap, "2021-08-15 오후 08:45", 4, orderMenuData, "10,000원", true))
        }

        orderAdapter.orderData = orderData
    }
}