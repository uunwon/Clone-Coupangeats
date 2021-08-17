package com.yunwoon.coupangeatsproject.src.main.mypage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentMyPageBinding
import com.yunwoon.coupangeatsproject.databinding.ItemMyPageBinding

data class MyPageData(val myPageImageView: Int, val myPageText: String)
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {

    private val myPageDataArrayList = ArrayList<MyPageData>()
    private lateinit var myPageAdapter : MyPageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myPageListView.isNestedScrollingEnabled = true
        initMyPageListView()
    }

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

        // myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test, "배달파트너 모집"))
        // myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test, "자주 묻는 질문"))
        // myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test, "고객 지원"))
        // myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test, "설정"))
        // myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test, "공지사항"))
        // myPageDataArrayList.add(MyPageData(R.drawable.ic_my_page_test, "약관ㆍ개인정보 처리방침"))
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
}