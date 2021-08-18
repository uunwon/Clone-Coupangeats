package com.yunwoon.coupangeatsproject.src.main.mypage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentMyPageBinding
import com.yunwoon.coupangeatsproject.databinding.ItemMyPageBinding
import com.yunwoon.coupangeatsproject.src.main.MainActivity
import com.yunwoon.coupangeatsproject.src.main.mypage.models.MyPageResponse
import java.util.*
import kotlin.collections.ArrayList

data class MyPageData(val myPageImageView: Int, val myPageText: String)
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page), MyPageFragmentView {
    private val loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)
    private val LOGOUT_CODE = 101

    private val myPageDataArrayList = ArrayList<MyPageData>()
    private lateinit var myPageAdapter : MyPageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myPageListView.isNestedScrollingEnabled = true

        initMyPageListView()
        initUserProfile()

        binding.myPageImageViewLogout.setOnClickListener {
            val dialogLogout = LogoutDialog()
            dialogLogout.setTargetFragment(this@MyPageFragment, LOGOUT_CODE)
            fragmentManager?.let { it1 -> dialogLogout.show(it1, "LogoutDialog") }
        }
    }

    // 마이페이지 목록 세팅
    private fun initMyPageListView() {
        addMyPageData()

        // myPageDataArrayList.clear()
        myPageAdapter = MyPageAdapter(myPageDataArrayList, requireContext())
        binding.myPageListView.adapter = myPageAdapter

        myPageAdapter.notifyDataSetChanged()
    }

    private fun addMyPageData() {
        myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test, "배달 주소 관리"))
        myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test2, "즐겨찾기"))
        myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test, "할인쿠폰"))
        myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test2, "진행중인 이벤트"))
        myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test, "결제관리"))

        myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test2, "배달파트너 모집"))
        myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test, "자주 묻는 질문"))
        myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test2, "고객 지원"))
        myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test, "설정"))
        myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test2, "공지사항"))
        myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test, "약관ㆍ개인정보 처리방침"))
    }

    class MyPageAdapter(private val myPageDataArrayList:ArrayList<MyPageData>, context: Context) : BaseAdapter() {
        private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        private lateinit var binding: ItemMyPageBinding

        override fun getCount(): Int = myPageDataArrayList.size

        override fun getItem(p0: Int): Any = myPageDataArrayList[p0]

        override fun getItemId(p0: Int): Long = p0.toLong()

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            binding = ItemMyPageBinding.inflate(inflater, p2, false)

            binding.myPageImageView.setImageResource(myPageDataArrayList[p0].myPageImageView)
            binding.myPageText.text = myPageDataArrayList[p0].myPageText

            return binding.root
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == LOGOUT_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                val bundle = data?.extras
                if(bundle?.getInt("code") == 1) {
                    backToMain()
                }
            }
        }
    }

    private fun backToMain() {
        ApplicationClass.sEditor.putString("loginJwtToken", null).apply()
        (activity as MainActivity).setMainFragment()
    }

    // 사용자 프로필 조회
    private fun initUserProfile() {
        if(loginJwtToken != null) {
            showLoadingDialog(requireContext())
            MyPageService(this).tryGetMyPage(loginJwtToken)
        }
    }

    override fun onGetMyPageSuccess(response: MyPageResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            showCustomToast("사용자 정보를 받아왔습니다")
            // val phoneFormat = PhoneNumberUtils.formatNumber(response.result[0].phoneNumber)
            val phoneFormat = PhoneNumberUtils.formatNumber(response.result[0].phoneNumber, Locale.getDefault().country)

            /* val phoneSplit = phoneFormat.split("-").toMutableList()

            if(phoneSplit.size == 3) {
                phoneSplit[1] = "****"
                binding.myPageTextPhone.text = phoneSplit[0].plus(phoneSplit[1]).plus(phoneSplit[2])
            } else {
                binding.myPageTextPhone.text = phoneFormat
            } */

            binding.myPageTextPhone.text = phoneFormat
            binding.myPageTextName.text = response.result[0].name
        } else {
            showCustomToast("사용자 정보를 받아오는데 실패했습니다")
        }
    }

    override fun onGetMyPageFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}