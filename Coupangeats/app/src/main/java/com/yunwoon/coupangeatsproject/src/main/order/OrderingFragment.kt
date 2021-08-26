package com.yunwoon.coupangeatsproject.src.main.order

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentOrderingBinding
import com.yunwoon.coupangeatsproject.src.main.order.models.OrderingResponse
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderData
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderMenuData
import com.yunwoon.coupangeatsproject.util.orderRecycler.OrderingAdapter

class OrderingFragment :
    BaseFragment<FragmentOrderingBinding>(FragmentOrderingBinding::bind, R.layout.fragment_ordering), OrderingFragmentView {
    private val loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)
    private lateinit var orderViewPager: ViewPager

    private lateinit var olayoutManager : LinearLayoutManager
    private lateinit var orderingAdapter : OrderingAdapter

    private val orderData = mutableListOf<OrderData>()
    private val orderMenuData = mutableListOf<OrderMenuData>()

    private var count = 1
    private var price = 0
    private lateinit var bitmap : Bitmap

    fun newInstance() : OrderingFragment {
        val args = Bundle()
        val frag = OrderingFragment()
        frag.arguments = args
        return frag
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOrderingData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        orderViewPager = requireActivity().findViewById(R.id.order_view_pager)

        val resources : Resources = this.resources
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_favorite_store)

        binding.orderButtonViewOrdered.setOnClickListener {
            orderViewPager.currentItem = 0
        }

        binding.orderingButtonViewOrdered.setOnClickListener {
            orderViewPager.currentItem = 0
        }
    }

    private fun getOrderingData() {
        if(loginJwtToken != null) {
            showLoadingDialog(requireContext())
            OrderingService(this).tryGetOrdering(loginJwtToken)
        }
        else
            showCustomToast("로그인이 필요한 서비스입니다")
    }

    override fun onGetOrderingSuccess(response: OrderingResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            // RecyclerView 세팅
            olayoutManager = LinearLayoutManager(requireContext())
            binding.orderingRecyclerView.layoutManager = olayoutManager

            orderingAdapter = OrderingAdapter(requireContext())
            binding.orderingRecyclerView.isNestedScrollingEnabled = true
            binding.orderingRecyclerView.adapter = orderingAdapter

            if(response.result.selectMenuOrderResult.isNotEmpty()) {
                binding.nestedScrollView.visibility = View.VISIBLE
                binding.orderButtonViewOrdered.visibility = View.GONE

                for (i in response.result.selectMenuOrderResult) {
                    var orderMenuData2 = mutableListOf<OrderMenuData>()
                    price = 0
                    count = 1
                    orderMenuData2.add(OrderMenuData(count++, i.menuName))
                    price += i.menuPrice.toInt()

                    if (loginJwtToken != null)
                        OrderingService(this).tryGetOrderingOption(loginJwtToken, i.cartId, orderMenuData2).join()

                    orderData.add(OrderData(i.restaurantId, "동대문 엽기 떡볶이", bitmap, i.createAt.substring(0,10), 5, orderMenuData2, "$price 원", false))
                }
                orderingAdapter.orderData = orderData
            }
            else {
                binding.nestedScrollView.visibility = View.GONE
                binding.orderButtonViewOrdered.visibility = View.VISIBLE
            }
        }
    }
    override fun onGetOrderingOptionSuccess(response: OrderingResponse, cartId: Int, orderMenuData: MutableList<OrderMenuData>) {
        Log.d("OrderingFragment", "onGetOrderingOptionSuccess 들어옴")
        dismissLoadingDialog()
        if(response.isSuccess) {
            if(response.result.selectOptionOrderResult.isNotEmpty()) {
                Log.d("OrderingFragment", "selectOptionOrderResult 들어옴")
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

    override fun onGetOrderingFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetOrderingOptionFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    /* private fun setOrderRecyclerView() {
        Log.d("OrderedFragment", "setOrderRecyclerView 호출")

        orderMenuData.apply {
            add(OrderMenuData(1, "스노윙 치킨 한마리"))
            add(OrderMenuData(2, "상콤 치즈볼"))
        }

        orderData.apply {
            add(OrderData("굽네치킨 문래점", bitmap, "2021-08-20 오후 08:45", 5, orderMenuData, "14,000원", false))
        }

        orderingAdapter.orderData = orderData
    } */
}