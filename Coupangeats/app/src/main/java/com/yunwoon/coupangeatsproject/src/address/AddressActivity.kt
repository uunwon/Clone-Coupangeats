package com.yunwoon.coupangeatsproject.src.address

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityAddressBinding

class AddressActivity : BaseActivity<ActivityAddressBinding>(ActivityAddressBinding::inflate), View.OnKeyListener {

    private var addressSearchStatus = false
    private lateinit var inputMethodManager : InputMethodManager
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        binding.addressRecyclerView.isNestedScrollingEnabled = true
        binding.addressRecyclerViewSearch.isNestedScrollingEnabled = true
        setToolBar(binding.addressToolbar)

        initAddressData()

        // 검색창 포커스 얻으면
        binding.addressEditTextSearch.setOnFocusChangeListener { _, b ->
            if(b) {
                addressSearchStatus = true

                if(count++ == 0) {
                    binding.addressImageButtonBack.setImageResource(R.drawable.ic_toolbar_back)
                    binding.addressLinearLayoutSearch.visibility = View.VISIBLE
                    binding.addressNestedScrollView.visibility = View.GONE
                    binding.addressImageButtonTextClear.visibility = View.VISIBLE
                }
            }
        }

        // 뒤로 가기 버튼 클릭하면
        binding.addressImageButtonBack.setOnClickListener {
            setAddressView()
        }

        // 검색창 클리어
        binding.addressImageButtonTextClear.setOnClickListener {
            if(addressSearchStatus) {
                binding.addressEditTextSearch.setText("")
            }
        }

        // 검색 ENTER EVENT
        binding.addressEditTextSearch.setOnKeyListener(this)
    }

    private fun initAddressData() {
        // 사용자 주소 데이터 받아오기 // recyclerview 아이템 뿌려주기
    }

    override fun onBackPressed() {
        setAddressView()
    }

     // 검색창 아래 화면 세팅
    private fun setAddressView() {
        if(addressSearchStatus && count > 0) {
            addressSearchStatus = !addressSearchStatus
            count = 0

            binding.addressImageButtonBack.setImageResource(R.drawable.ic_toolbar_close)
            binding.addressLinearLayoutSearch.visibility = View.GONE
            binding.addressNestedScrollView.visibility = View.VISIBLE
            binding.addressNestedScrollViewSearch.visibility = View.GONE
            binding.addressImageButtonTextClear.visibility = View.GONE

            binding.addressEditTextSearch.setText("")
            binding.addressEditTextSearch.clearFocus()
            inputMethodManager.hideSoftInputFromWindow(binding.addressEditTextSearch.windowToken, 0)
        }
        else if (count == 0) {
            finish()
        }
    }

    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        // 검색 ENTER EVENT
        if(p1 == KeyEvent.KEYCODE_ENTER) {
            binding.addressLinearLayoutSearch.visibility = View.GONE
            binding.addressNestedScrollViewSearch.visibility = View.VISIBLE
            // 도로명 주소 받아오는 API 구현
            showCustomToast("엔터키 클릭")
            binding.addressEditTextSearch.clearFocus()
            inputMethodManager.hideSoftInputFromWindow(binding.addressEditTextSearch.windowToken, 0)
        }

        return false
    }

}