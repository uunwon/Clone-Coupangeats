package com.yunwoon.coupangeatsproject.src.store.menu

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentMenuBinding
import com.yunwoon.coupangeatsproject.src.store.models.StoreCategoryFoodResponse
import com.yunwoon.coupangeatsproject.util.menuRecycler.MenuAdapter
import com.yunwoon.coupangeatsproject.util.menuRecycler.MenuData

class MenuFragment(private val tabLayoutTextArray : String, private val storeIndex : Int,
            private val categoryIndex: Int)  :
    BaseFragment<FragmentMenuBinding>(FragmentMenuBinding::bind, R.layout.fragment_menu), MenuFragmentView {
    private lateinit var mlayoutManager: LinearLayoutManager

    private lateinit var menuAdapter: MenuAdapter
    private val menuData = mutableListOf<MenuData>()

    fun newInstance() : MenuFragment {
        val args = Bundle()
        val frag = MenuFragment(tabLayoutTextArray, storeIndex, categoryIndex)
        frag.arguments = args
        return frag
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMenuRecyclerView()
        MenuService(this).tryGetStore(storeIndex, categoryIndex)
    }

    // 메뉴 리사이클러뷰 세팅
    private fun setMenuRecyclerView() {
        mlayoutManager = LinearLayoutManager(requireContext())

        binding.storeRecyclerViewMenu.layoutManager = mlayoutManager
        binding.storeRecyclerViewMenu.isNestedScrollingEnabled = true

        binding.storeTextMenuTitle.text = tabLayoutTextArray

        menuAdapter = MenuAdapter(requireContext())
        binding.storeRecyclerViewMenu.adapter = menuAdapter
    }

    override fun onGetStoreCategoryFoodSuccess(response: StoreCategoryFoodResponse) {
        if(response.isSuccess && response.result.isNotEmpty()) {

            for(i in response.result) {
                menuData.add(MenuData(i.menuName, i.price, "(매운맛 조절가능, 토핑 축가능, 떡/오뎅사리 추가옵션 미선택시 기본 양으로만 제공)" +
                        "/ 피크타임 조리시간 60-70분 소요", "https://user-images.githubusercontent.com/48541984/130426115-e805e693-ef47-4f1f-91b2-d5f57ccabb8e.png"))
            }
            menuAdapter.menuDataArrayList = menuData
            menuAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetStoreCategoryFoodFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}