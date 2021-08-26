package com.yunwoon.coupangeatsproject.src.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunwoon.coupangeatsproject.BuildConfig
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityCartBinding
import com.yunwoon.coupangeatsproject.src.address.AddressActivity
import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse
import com.yunwoon.coupangeatsproject.src.cart.models.PostOrderRequest
import com.yunwoon.coupangeatsproject.src.cart.models.UserCartResponse
import com.yunwoon.coupangeatsproject.src.cart.models.UserOptionCartResponse
import com.yunwoon.coupangeatsproject.src.cart.models.UserOrderResponse
import com.yunwoon.coupangeatsproject.src.main.mypage.models.MyPageResponse
import com.yunwoon.coupangeatsproject.src.store.models.SampleOptionCart
import com.yunwoon.coupangeatsproject.util.SetAddressDialog
import com.yunwoon.coupangeatsproject.util.cartRecycler.CartAdapter
import com.yunwoon.coupangeatsproject.util.cartRecycler.CartData
import kr.co.bootpay.Bootpay
import kr.co.bootpay.BootpayAnalytics
import kr.co.bootpay.enums.Method
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser

class CartActivity : BaseActivity<ActivityCartBinding>(ActivityCartBinding::inflate), CartActivityView {
    private val loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)
    private lateinit var clayoutManager: LinearLayoutManager

    private lateinit var cartAdapter: CartAdapter
    private val cartData = mutableListOf<CartData>()
    val optionCart = mutableListOf<SampleOptionCart>()

    private var cartPrice = 0
    private var cartDeliveryTip = 0
    private var cartId = 0

    private var menuName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.storeDetailToolbar)
        getCartData()

        BootpayAnalytics.init(this, BuildConfig.BOOT_PAY_ID) // 부트페이 초기설정

        binding.storeCartImageButtonClose.setOnClickListener { finish() }
        binding.cartToolbarPay.setOnClickListener {
            // 결제하기 버튼 클릭 // 부트페이 가자
            postBootPay()
        }
    }

    private fun postBootPay() {
        val bootUser = BootUser()
            .setID(ApplicationClass.sSharedPreferences.getString("userId", "1"))
            .setPhone(ApplicationClass.sSharedPreferences.getString("userPhone", "010-1234-5678"))
        val bootExtra = BootExtra().setPopup(1)

        val stuck = 1 //재고 있음

        Bootpay.init(this)
            .setApplicationId(BuildConfig.BOOT_PAY_ID) // 해당 프로젝트(안드로이드)의 application id 값
            .setContext(this)
            .setBootUser(bootUser)
            .setBootExtra(bootExtra)
            .setUX(UX.PG_DIALOG)
            .setPG(PG.PAYAPP)
            .setMethod(Method.CARD)
            .setIsShowAgree(true)
//                .setUserPhone("010-1234-5678") // 구매자 전화번호
            .setName(menuName) // 결제할 상품명
            .setOrderId(cartId.toString()) // 결제 고유번호 expire_month
            .setPrice(cartPrice+cartDeliveryTip) // 결제할 금액
            .onConfirm { message ->
                if (0 < stuck) Bootpay.confirm(message) // 재고가 있을 경우.
                else Bootpay.removePaymentWindow() // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                Log.d("confirm", message)
            }
            .onDone {
                message -> Log.d("done", message)
                if(loginJwtToken != null) {
                    val postOrderRequest = PostOrderRequest(cartId = cartId)
                    showLoadingDialog(this)
                    CartService(this).tryPostOrder(loginJwtToken, postOrderRequest)
                } else {
                    showCustomToast("로그인이 필요한 서비스입니다")
                }
            }
            .onReady { message -> Log.d("ready", message)
            }
            .onCancel { message -> Log.d("cancel", message)
            }
            .onError{ message -> Log.d("error", message)
            }
            .onClose { message -> Log.d("close", "close")
            }
            .request()
    }

    override fun onResume() {
        super.onResume()

        // 사용자 정보 받아오기
        if(loginJwtToken != null) {
            CartService(this).tryGetMyPage(loginJwtToken)
        }
    }

    override fun onGetMyPageSuccess(response: MyPageResponse) {
        dismissLoadingDialog()
        if(response.isSuccess && response.result.isNotEmpty()) {
            ApplicationClass.sEditor.putString("userId", response.result[0].id.toString()).apply()
            ApplicationClass.sEditor.putString("userEmail", response.result[0].email).apply()
            ApplicationClass.sEditor.putString("userName", response.result[0].name).apply()
            ApplicationClass.sEditor.putString("userPhone", response.result[0].phoneNumber).apply()
        }
    }

    override fun onGetMyPageFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
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
        // binding.cartRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager(this).orientation))

        cartAdapter = CartAdapter(this, supportFragmentManager)
        binding.cartRecyclerView.adapter = cartAdapter

        dismissLoadingDialog()
        if(response.isSuccess) {
            if(response.result.carts.isNotEmpty()) {  // 메인 카트
                getAddress()
                binding.cartConstraintLayout.visibility = View.VISIBLE
                binding.anyCartConstraintLayout.visibility = View.GONE
                binding.cartTextStoreTitle.text = response.result.carts[0].restaurantName

                for(i in response.result.carts) {
                    var optionCart3 = mutableListOf<String>()
                    if (response.result.optionCarts.isNotEmpty() && loginJwtToken != null) {
                        CartService(this).tryGetOptionCart(loginJwtToken, i.cartId).join()
                        var optionCart2: MutableList<SampleOptionCart> = optionCart.toMutableList()

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
                            menuName = i.menuName
                            cartData.add(CartData(i.menuId, i.menuName, optionCart3, (i.price.toInt()+optionPrice).toString(), i.menuCounts))
                            cartPrice += i.price.toInt()
                        }
                    } else { // 해당 메뉴의 옵션이 없을 때
                        menuName = i.menuName
                        cartData.add(CartData(i.menuId, i.menuName, optionCart3, i.price, i.menuCounts))
                        cartPrice += i.price.toInt()
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