package com.yunwoon.coupangeatsproject.src.cart

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityCartBinding
import com.yunwoon.coupangeatsproject.src.address.AddressActivity
import com.yunwoon.coupangeatsproject.util.SetAddressDialog
import com.yunwoon.coupangeatsproject.util.cartRecycler.CartAdapter
import com.yunwoon.coupangeatsproject.util.cartRecycler.CartData

class CartActivity : BaseActivity<ActivityCartBinding>(ActivityCartBinding::inflate) {
    private lateinit var clayoutManager: LinearLayoutManager

    private lateinit var cartAdapter: CartAdapter
    private val cartData = mutableListOf<CartData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.storeDetailToolbar)
        setAddress()
        setCartRecyclerView()

        binding.storeCartImageButtonClose.setOnClickListener { finish() }
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

    private fun moveToAddressActivity() {
        this.startActivity(Intent(this, AddressActivity::class.java))
    }

    // 카트 리사이클러뷰 세팅
    private fun setCartRecyclerView() {
        clayoutManager = LinearLayoutManager(this)

        binding.cartRecyclerView.layoutManager = clayoutManager
        binding.cartRecyclerView.isNestedScrollingEnabled = true

        cartAdapter = CartAdapter(this)
        binding.cartRecyclerView.adapter = cartAdapter

        cartData.apply {
            add(CartData("고르곤졸라 피자", "L (+3,000원), 선택없음, 참여안함", "21,900"))
        }

        cartAdapter.cartData = cartData
    }
}