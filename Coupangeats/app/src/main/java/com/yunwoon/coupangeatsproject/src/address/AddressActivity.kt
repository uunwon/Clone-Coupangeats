package com.yunwoon.coupangeatsproject.src.address

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityAddressBinding
import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse
import com.yunwoon.coupangeatsproject.util.roadRecycler.RoadAdapter
import com.yunwoon.coupangeatsproject.util.roadRecycler.RoadData

class AddressActivity : BaseActivity<ActivityAddressBinding>(ActivityAddressBinding::inflate), View.OnKeyListener, AddressActivityView {

    private var addressSearchStatus = false
    private lateinit var inputMethodManager : InputMethodManager
    private var count = 0

    private val roadItemArrayList = ArrayList<RoadData>()
    private lateinit var roadAdapter : RoadAdapter
    private lateinit var mlayoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        mlayoutManager = LinearLayoutManager(this)

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

    // 사용자 주소 데이터 받아오기 // recyclerview 아이템 뿌려주기
    private fun initAddressData() {

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

    override fun onBackPressed() {
        setAddressView()
    }

    // 검색 ENTER EVENT
    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        if(p1 == KeyEvent.KEYCODE_ENTER) {
            binding.addressLinearLayoutSearch.visibility = View.GONE
            binding.addressNestedScrollViewSearch.visibility = View.VISIBLE
            // 도로명 주소 받아오는 API 구현
            setAddressSearchRecyclerView()
            binding.addressEditTextSearch.clearFocus()
            inputMethodManager.hideSoftInputFromWindow(binding.addressEditTextSearch.windowToken, 0)
        }
        return false
    }

    // 도로명 주소 검색 결과 RecyclerView 세팅
    private fun setAddressSearchRecyclerView() {
        val address = binding.addressEditTextSearch.text.toString()
        showLoadingDialog(this)
        AddressService(this).tryGetAddress(address)
    }

    override fun onGetAddressSuccess(response: AddressResponse) {
        dismissLoadingDialog()
        Log.d("AddressActivity", response.results.common.errorMessage)
        if(response.results.common.errorCode == "0") {
            showCustomToast("도로명 받아오기 GET 성공")
            // 리사이클러뷰에 정보 뿌리기
            initRecyclerView(response)
        } else {
            showCustomToast("도로명 받아오기 GET 실패")
        }
    }

    override fun onPostAddressFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 도로명 주소 리사이클러뷰에 정보 뿌리기
    private fun initRecyclerView(response: AddressResponse) {
        binding.addressRecyclerViewSearch.layoutManager = mlayoutManager
        roadAdapter = RoadAdapter(this)
        roadItemArrayList.clear()

        for(i in 0..9) {
            roadItemArrayList.add(RoadData(response.results.juso[i].roadAddrPart1, response.results.juso[i].jibunAddr))
        }

        roadAdapter.roadDataArrayList = roadItemArrayList
        binding.addressRecyclerViewSearch.adapter = roadAdapter
        roadAdapter.notifyDataSetChanged()
    }

}