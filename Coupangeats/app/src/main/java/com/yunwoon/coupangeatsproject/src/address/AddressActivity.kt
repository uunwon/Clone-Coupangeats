package com.yunwoon.coupangeatsproject.src.address

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityAddressBinding
import com.yunwoon.coupangeatsproject.src.address.models.AddressPostResponse
import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse
import com.yunwoon.coupangeatsproject.src.address.models.AddressSearchResponse
import com.yunwoon.coupangeatsproject.src.address.models.PostAddressRequest
import com.yunwoon.coupangeatsproject.util.addressRecycler.AddressAdapter
import com.yunwoon.coupangeatsproject.util.addressRecycler.AddressData
import com.yunwoon.coupangeatsproject.util.roadRecycler.RoadAdapter
import com.yunwoon.coupangeatsproject.util.roadRecycler.RoadData

class AddressActivity : BaseActivity<ActivityAddressBinding>(ActivityAddressBinding::inflate),
    View.OnKeyListener, AddressActivityView {
    private val loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)

    private var addressSearchStatus = false
    private var addressDetailSetStatus = false
    private lateinit var inputMethodManager : InputMethodManager

    // addressPage = 1 , 세팅 초기
    // addressPage > 1 , 배송지 검색 페이지
    private var addressPage : Int = ApplicationClass.sSharedPreferences.getInt("addressPage", 1)
    private var addressCategory : String = "기타"

    private lateinit var alayoutManager : LinearLayoutManager // 수평 레이아웃 매니저
    private lateinit var addressAdapter: AddressAdapter
    private val addressData = mutableListOf<AddressData>()

    private val roadItemArrayList = ArrayList<RoadData>()
    private lateinit var roadAdapter : RoadAdapter
    private lateinit var rlayoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("addressPage", "현재 addressPage 는 ? $addressPage")

        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        rlayoutManager = LinearLayoutManager(this)

        binding.addressRecyclerView.isNestedScrollingEnabled = true
        binding.addressRecyclerViewSearch.isNestedScrollingEnabled = true
        setToolBar(binding.addressToolbar)

        initAddressData()

        // 검색창 포커스 얻으면
        binding.addressEditTextSearch.setOnFocusChangeListener { _, b ->
            if(b) {
                Log.d("addressPage", "현재 addressPage 는 ? $addressPage")

                addressSearchStatus = true

                if(addressPage++ == 1) {
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

        binding.addressButtonSearch.setOnClickListener {
            this.startActivity(Intent(this, NaverMapActivity::class.java))
        }

        // 홈버튼 클릭
        binding.addressButtonHome.setOnClickListener {
            binding.addressButtonHome.setBackgroundResource(R.drawable.button_coupang)
            binding.addressButtonCompany.setBackgroundResource(R.drawable.button_default_gray)
            binding.addressButtonEtc.setBackgroundResource(R.drawable.button_default_gray)
            addressCategory = "집"
        }

        // 회사버튼 클릭
        binding.addressButtonCompany.setOnClickListener {
            binding.addressButtonHome.setBackgroundResource(R.drawable.button_default_gray)
            binding.addressButtonCompany.setBackgroundResource(R.drawable.button_coupang)
            binding.addressButtonEtc.setBackgroundResource(R.drawable.button_default_gray)
            addressCategory = "회사"
        }

        // 기타버튼 클릭
        binding.addressButtonSet.setOnClickListener {
            binding.addressButtonHome.setBackgroundResource(R.drawable.button_default_gray)
            binding.addressButtonCompany.setBackgroundResource(R.drawable.button_default_gray)
            binding.addressButtonEtc.setBackgroundResource(R.drawable.button_coupang)
            addressCategory = "기타"
        }

        binding.addressButtonSet.setOnClickListener { setAddressDetail() }
    }

    // 사용자 주소 데이터 받아오기 // recyclerview 아이템 뿌려주기
    private fun initAddressData() {
        alayoutManager = LinearLayoutManager(this)
        alayoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.addressRecyclerView.layoutManager = alayoutManager
        binding.addressRecyclerView.isNestedScrollingEnabled = true

        if(loginJwtToken != null) {
            showLoadingDialog(this)
            AddressService(this).tryGetAddress(loginJwtToken)
        }
        else {
            showCustomToast("로그인이 필요한 서비스입니다")
            finish()
        }
    }

    override fun onGetAddressSuccess(response: AddressResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            addressAdapter = AddressAdapter(this)
            binding.addressRecyclerView.adapter = addressAdapter

            for(i in response.result)
                addressData.add(AddressData(i.category, i.location, i.locationDetail))

            addressAdapter.addressData = addressData
        } else {
            showCustomToast("사용자의 위치를 받아오는데 실패했습니다")
        }
    }

    override fun onGetAddressFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onBackPressed() { setAddressView() }

     // 검색창 아래 화면 세팅
    private fun setAddressView() {
        if(addressDetailSetStatus) {
            addressDetailSetStatus = !addressDetailSetStatus
            addressSearchStatus = true
            binding.addressConstraintLayoutDetail.visibility = View.GONE
            binding.addressLinearLayoutSearch.visibility = View.GONE
            binding.addressNestedScrollView.visibility = View.GONE

            binding.titleAddress.text = "배달지 주소 설정"
            binding.addressNestedScrollViewSearch.visibility = View.VISIBLE
            binding.addressImageButtonTextClear.visibility = View.VISIBLE

            binding.addressEditTextSearch.clearFocus()
            inputMethodManager.hideSoftInputFromWindow(binding.addressEditTextSearch.windowToken, 0)
        }
        else if(!addressDetailSetStatus && addressSearchStatus && addressPage > 1) {
            addressSearchStatus = !addressSearchStatus
            addressPage = 1

            binding.addressImageButtonBack.setImageResource(R.drawable.ic_toolbar_close)
            binding.addressConstraintLayoutDetail.visibility = View.GONE
            binding.addressLinearLayoutSearch.visibility = View.GONE
            binding.addressNestedScrollView.visibility = View.VISIBLE
            binding.addressNestedScrollViewSearch.visibility = View.GONE
            binding.addressImageButtonTextClear.visibility = View.GONE

            binding.addressEditTextSearch.setText("")
            binding.addressEditTextSearch.clearFocus()
            inputMethodManager.hideSoftInputFromWindow(binding.addressEditTextSearch.windowToken, 0)
        }
        else if (addressPage == 1) {
            finish()
        }
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
        AddressService(this).tryGetAddressSearch(address)
    }

    override fun onGetAddressSearchSuccess(searchResponse: AddressSearchResponse) {
        dismissLoadingDialog()
        Log.d("AddressActivity", searchResponse.searchResults.common.errorMessage)
        if(searchResponse.searchResults.common.errorCode == "0") {
            showCustomToast("도로명 받아오기 GET 성공")
            // 리사이클러뷰에 정보 뿌리기
            initRecyclerView(searchResponse)
        } else {
            showCustomToast("도로명 받아오기 GET 실패")
        }
    }

    override fun onGetAddressSearchFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 도로명 주소 리사이클러뷰에 정보 뿌리기
    private fun initRecyclerView(searchResponse: AddressSearchResponse) {
        binding.addressRecyclerViewSearch.layoutManager = rlayoutManager
        roadAdapter = RoadAdapter(this)
        roadItemArrayList.clear()

        for(i in 0 until searchResponse.searchResults.juso.size) {
            roadItemArrayList.add(RoadData(searchResponse.searchResults.juso[i].roadAddrPart1, searchResponse.searchResults.juso[i].jibunAddr))
        }

        roadAdapter.roadDataArrayList = roadItemArrayList
        binding.addressRecyclerViewSearch.adapter = roadAdapter
        roadAdapter.notifyDataSetChanged()
    }

    // 배달지 상세 정보 페이지로 이동
    fun moveToAddressDetailPage() {
        addressSearchStatus = false
        addressDetailSetStatus = true
        binding.addressConstraintLayoutDetail.visibility = View.VISIBLE
        binding.titleAddress.text = "배달지 상세 정보"

        binding.addressTextAddress.text = ApplicationClass.sSharedPreferences.getString("temporaryAddress", "temporaryAddress")
        binding.addressTextAddressDetail.text = ApplicationClass.sSharedPreferences.getString("temporaryAddressDetail", "temporaryAddressDetail")
    }

    // 배달지 상세 정보 페이지에서 배달지 주소 설정
    private fun setAddressDetail() {
        if(loginJwtToken != null) {
            val postAddressRequest = PostAddressRequest(location = binding.addressTextAddress.text.toString(),
                locationDetail = (binding.addressTextAddressDetail.text.toString()+" ${binding.addressEditTextDetail.text}"),
                category = addressCategory)
            AddressService(this).tryPostAddress(loginJwtToken, postAddressRequest)
        }
    }

    override fun onPostAddressSuccess(response: AddressPostResponse) {
        if(response.isSuccess) {
            finish()
            showCustomToast("주소가 생성되었습니다")
        } else {
            showCustomToast("주소 생성에 실패했습니다")
        }
    }

    override fun onPostAddressFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}