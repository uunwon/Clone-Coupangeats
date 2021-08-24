package com.yunwoon.coupangeatsproject.src.main.order

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentOrderedBinding
import com.yunwoon.coupangeatsproject.src.main.order.models.OrderedResponse
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderAdapter
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderData
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderMenuData

class OrderedFragment :
    BaseFragment<FragmentOrderedBinding>(FragmentOrderedBinding::bind, R.layout.fragment_ordered), OrderedFragmentView {
    private val loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)

    private lateinit var olayoutManager : LinearLayoutManager
    private lateinit var orderAdapter : OrderAdapter

    private val orderData = mutableListOf<OrderData>()
    private val orderMenuData = mutableListOf<OrderMenuData>()

    private var count = 1
    private var price = 0
    private lateinit var bitmap : Bitmap

    fun newInstance() : OrderedFragment {
        val args = Bundle()
        val frag = OrderedFragment()
        frag.arguments = args
        return frag
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resources : Resources = this.resources
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_favorite_store)

        // setOrderRecyclerView()
        getOrderedData()
    }

    private fun getOrderedData() {
        if(loginJwtToken != null) {
            showLoadingDialog(requireContext())
            OrderedService(this).tryGetOrdered(loginJwtToken)
        }
        else
            showCustomToast("로그인이 필요한 서비스입니다")
    }

    override fun onGetOrderedSuccess(response: OrderedResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            // RecyclerView 세팅
            olayoutManager = LinearLayoutManager(requireContext())
            binding.orderedRecyclerView.layoutManager = olayoutManager

            orderAdapter = OrderAdapter(requireContext())
            binding.orderedRecyclerView.isNestedScrollingEnabled = true
            binding.orderedRecyclerView.adapter = orderAdapter

            if(response.result.selectOrderResult.isNotEmpty()) {
                binding.nestedScrollView.visibility = View.VISIBLE
                binding.anyOrderedTextView.visibility = View.GONE

                for (i in response.result.selectOrderResult) {
                    var orderMenuData2 = mutableListOf<OrderMenuData>()
                    price = 0
                    count = 1
                    orderMenuData2.add(OrderMenuData(count++, i.menuName))
                    price += i.menuPrice.toInt()

                    if (loginJwtToken != null)
                        OrderedService(this).tryGetOrderedOption(loginJwtToken, i.cartId, orderMenuData2).join()

                    orderData.add(OrderData("동대문 엽기 떡볶이", bitmap, i.createAt, 5, orderMenuData2, "$price 원", false))
                }
                orderAdapter.orderData = orderData
            }
            else {
                binding.nestedScrollView.visibility = View.GONE
                binding.anyOrderedTextView.visibility = View.VISIBLE
            }
        }
    }

    override fun onGetOrderedOptionSuccess(response: OrderedResponse, cartId: Int, orderMenuData: MutableList<OrderMenuData>) {
        Log.d("OrderedFragment", "onGetOrderedOptionSuccess 들어옴")
        dismissLoadingDialog()
        if(response.isSuccess) {
            if(response.result.selectOptionOrderResult.isNotEmpty()) {
                Log.d("OrderedFragment", "selectOptionOrderResult 들어옴")
                for(i in response.result.selectOptionOrderResult){
                    if(i.cartId == cartId) {
                        price += i.price.toInt()
                        orderMenuData.add(OrderMenuData(count++, i.optionName))
                    }
                }
            }
        } else {
            showCustomToast("옵션 메뉴들을 받아오지 못했습니다")
        }
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

    override fun onGetOrderedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetOrderedOptionFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}