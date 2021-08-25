package com.yunwoon.coupangeatsproject.src.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityCartBinding
import com.yunwoon.coupangeatsproject.src.address.AddressActivity
import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse
import com.yunwoon.coupangeatsproject.src.cart.models.PostOrderRequest
import com.yunwoon.coupangeatsproject.src.cart.models.UserCartResponse
import com.yunwoon.coupangeatsproject.src.cart.models.UserOptionCartResponse
import com.yunwoon.coupangeatsproject.src.cart.models.UserOrderResponse
import com.yunwoon.coupangeatsproject.src.store.models.SampleOptionCart
import com.yunwoon.coupangeatsproject.util.SetAddressDialog
import com.yunwoon.coupangeatsproject.util.cartRecycler.CartAdapter
import com.yunwoon.coupangeatsproject.util.cartRecycler.CartData

class CartActivity : BaseActivity<ActivityCartBinding>(ActivityCartBinding::inflate), CartActivityView {
    private val loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)
    private lateinit var clayoutManager: LinearLayoutManager

    private lateinit var cartAdapter: CartAdapter
    private val cartData = mutableListOf<CartData>()
    val optionCart = mutableListOf<SampleOptionCart>()

    private var cartPrice = 0
    private var cartDeliveryTip = 0
    private var cartId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.storeDetailToolbar)
        getCartData()

        binding.storeCartImageButtonClose.setOnClickListener { finish() }
        binding.cartToolbarPay.setOnClickListener {
            // 결제하기 버튼 클릭
            if(loginJwtToken != null) {
                val postOrderRequest = PostOrderRequest(cartId = cartId)
                showLoadingDialog(this)
                CartService(this).tryPostOrder(loginJwtToken, postOrderRequest)
            } else {
                showCustomToast("로그인이 필요한 서비스입니다")
            }
        }
    }

    private fun getAddress() {
        if(loginJwtToken != null) {
            CartService(this).tryGetAddress(loginJwtToken)
        }
    }

    override fun onGetAddressSuccess(response: AddressResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            if(response.result.isNotEmpty()) {
                for(i in response.result) {
                    binding.cartTextAddress.text = "${i.location} (으)로 배달"
                    binding.cartTextAddressDetail.text = i.locationDetail
                }
            }
            else
                setAddress()
        } else {
            showCustomToast("사용자 정보를 받아오지 못했습니다")
        }
    }

    override fun onGetAddressFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    private fun setAddress() {
        // 주소 받아와서 없으면
        val dialogSetAddress = SetAddressDialog()
        dialogSetAddress.isCancelable = false
        dialogSetAddress.show(supportFragmentManager, "SetAddressDialog")

        dialogSetAddress.setAddressDialogResult(object : SetAddressDialog.SetAddressResult{
            override fun finish(dialogResult: Int) {
                if(dialogResult == 1) {
                    // 주소 액티비티로 이동
                    moveToAddressActivity()
                }
                else if(dialogResult == 2){
                    finish()
                }
            }
        })
    }

    // 주소 설정 페이지로 이동
    private fun moveToAddressActivity() { this.startActivity(Intent(this, AddressActivity::class.java)) }

    // 주문하기
    override fun onPostOrderSuccess(response: UserOrderResponse) {
        dismissLoadingDialog()

        if(response.isSuccess) {
            finish()
            showCustomToast("주문 완료했습니다")
        } else {
            showCustomToast("주문에 실패했습니다")
        }
    }

    override fun onPostOrderFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 카트 데이터 받아오기
    private fun getCartData() {
        if(loginJwtToken != null) {
            showLoadingDialog(this)
            CartService(this).tryGetCart(loginJwtToken)
        } else {
            showCustomToast("사용자의 카트를 받아오는데 실패했습니다")
        }
    }

    override fun onGetUserCartSuccess(response: UserCartResponse) {
        // 메뉴들 (item_cart) 리사이클러뷰 세팅
        clayoutManager = LinearLayoutManager(this)

        binding.cartRecyclerView.layoutManager = clayoutManager
        binding.cartRecyclerView.isNestedScrollingEnabled = true

        cartAdapter = CartAdapter(this)
        binding.cartRecyclerView.adapter = cartAdapter

        dismissLoadingDialog()
        if(response.isSuccess) {
            if(response.result.carts.isNotEmpty()) {  // 메인 카트
                getAddress()
                binding.cartConstraintLayout.visibility = View.VISIBLE
                binding.anyCartConstraintLayout.visibility = View.GONE
                binding.cartTextStoreTitle.text = response.result.carts[0].restaurantName

                for(i in response.result.carts) {
                    if (response.result.optionCarts.isNotEmpty() && loginJwtToken != null) {
                        CartService(this).tryGetOptionCart(loginJwtToken, i.cartId).join()
                        var optionCart2: MutableList<SampleOptionCart> = optionCart.toMutableList()
                        var optionCart3 = mutableListOf<String>()

                        if (optionCart2.isNotEmpty()) { // 해당 메뉴의 옵션이 있을 때
                            var optionPrice = 0
                            for (i in optionCart2) {
                                if (i.price.toInt() != 0) {
                                    optionCart3.add("${i.optionName} (+${i.price}원)")
                                    optionPrice += i.price.toInt()
                                    cartPrice += i.price.toInt()
                                }
                                else
                                    optionCart3.add(i.optionName)
                            }
                            cartData.add(CartData(i.menuId, i.menuName, optionCart3, (i.price.toInt()+optionPrice).toString(), i.menuCounts))
                            cartPrice += i.price.toInt()
                        } else { // 해당 메뉴의 옵션이 없을 때
                            cartData.add(CartData(i.menuId, i.menuName, optionCart3, i.price, i.menuCounts))
                            cartPrice += i.price.toInt()
                        }
                    }
                }

                cartDeliveryTip += response.result.carts[0].delieveryFee.toInt()
                cartAdapter.cartData = cartData
                cartId = response.result.carts[0].cartId
            }
            else {
                binding.cartConstraintLayout.visibility = View.GONE
                binding.anyCartConstraintLayout.visibility = View.VISIBLE
            }
        }

        binding.cartTextOrderPrice.text = "$cartPrice 원"
        binding.cartTextDeliveryPrice.text = "+$cartDeliveryTip 원"
        binding.cartTextTotalPrice.text = "${cartDeliveryTip+cartPrice} 원"
        binding.cartTextOrder.text = "${cartDeliveryTip+cartPrice} 원 결제하기"
    }

    override fun onGetUserOptionCartSuccess(response: UserOptionCartResponse, cartId: Int) {
        dismissLoadingDialog()
        optionCart.clear()

        if(response.isSuccess && response.result.isNotEmpty()) {
            for (i in response.result) {
                if (cartId == i.cartId) {
                    optionCart.add(SampleOptionCart(i.optionName, i.price))
                }
            }
        } else {
            showCustomToast("옵션 카트를 받아오지 못했습니다")
        }
    }

    override fun onGetUserOptionCartFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetUserCartFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    /* 카트 리사이클러뷰 세팅
    private fun setCartRecyclerView() {
        clayoutManager = LinearLayoutManager(this)

        binding.cartRecyclerView.layoutManager = clayoutManager
        binding.cartRecyclerView.isNestedScrollingEnabled = true

        cartAdapter = CartAdapter(this)
        binding.cartRecyclerView.adapter = cartAdapter

        cartData.apply {
            add(CartData(1, "고르곤졸라 피자", "L (+3,000원), 선택없음, 참여안함", "21,900", 1))
        }

        cartAdapter.cartData = cartData
    } */
}