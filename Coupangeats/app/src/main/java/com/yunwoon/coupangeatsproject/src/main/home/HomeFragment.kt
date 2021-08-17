package com.yunwoon.coupangeatsproject.src.main.home

import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentHomeBinding
import com.yunwoon.coupangeatsproject.src.address.AddressActivity
import com.yunwoon.coupangeatsproject.src.category.CategoryDetailActivity
import com.yunwoon.coupangeatsproject.util.categoryRecycler.CategoryAdapter
import com.yunwoon.coupangeatsproject.util.categoryRecycler.CategoryData
import com.yunwoon.coupangeatsproject.util.smallStoreRecycler.SmallStoreAdapter
import com.yunwoon.coupangeatsproject.util.smallStoreRecycler.SmallStoreData
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home){

    private val imageHomeAd : Array<Int> = arrayOf(R.drawable.image_home_ad_1, R.drawable.image_home_ad_2)

    private lateinit var scope : Job

    private lateinit var clayoutManager : LinearLayoutManager // 수평 레이아웃 매니저
    private lateinit var hlayoutManager : LinearLayoutManager

    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryData = mutableListOf<CategoryData>()

    private lateinit var hotStoreAdapter: SmallStoreAdapter
    private val hotStoreData = mutableListOf<SmallStoreData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        clayoutManager = LinearLayoutManager(requireContext())
        clayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        hlayoutManager = LinearLayoutManager(requireContext())
        hlayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        binding.homeRecyclerViewCategory.layoutManager = clayoutManager // 카테고리 리스트
        binding.homeRecyclerViewCategory.isNestedScrollingEnabled = true

        binding.homeRecyclerViewRecommend.layoutManager = hlayoutManager // 추천맛집 리스트
        binding.homeRecyclerViewRecommend.isNestedScrollingEnabled = true

        setToolBar(binding.homeToolbar)
        initViewFlipper()
        initCategoryRecyclerView()
        initHotRecyclerView()

        // 배달지 주소 설정 화면 이동
        binding.textToolbarTitle.setOnClickListener {
            this.startActivity(Intent(requireContext(), AddressActivity::class.java))
        }
    }

    // 카테고리 리사이클러뷰 세팅
    private fun initCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter(requireContext(),this@HomeFragment)
        binding.homeRecyclerViewCategory.adapter = categoryAdapter

        val resources : Resources = this.resources
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_category)

        categoryData.apply {
            add(CategoryData(bitmap, "신규 맛집"))
            add(CategoryData(bitmap, "1인분"))
            add(CategoryData(bitmap, "한식"))
            add(CategoryData(bitmap, "치킨"))
            add(CategoryData(bitmap, "분식"))
            add(CategoryData(bitmap, "중식"))

            categoryAdapter.categoryData = categoryData
        }
    }

    private fun initHotRecyclerView() {
        hotStoreAdapter = SmallStoreAdapter(requireContext(), this@HomeFragment)
        binding.homeRecyclerViewRecommend.adapter = hotStoreAdapter

        val resources : Resources = this.resources
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_small_store)

        hotStoreData.apply {
            add(SmallStoreData(bitmap, "피자헛 당산점", "4.9", "(528)", "0.1km", "3,000원"))
            add(SmallStoreData(bitmap, "피자헛 당산점", "4.8", "(528)", "0.1km", "3,000원"))
            add(SmallStoreData(bitmap, "피자헛 당산점", "4.7", "(528)", "0.1km", "3,000원"))
            add(SmallStoreData(bitmap, "피자헛 당산점", "4.9", "(528)", "0.1km", "3,000원"))
            add(SmallStoreData(bitmap, "피자헛 당산점", "4.9", "(528)", "0.1km", "3,000원"))
        }

        hotStoreAdapter.smallStoreDataArrayList = hotStoreData
    }

    // 카테고리 아이템 클릭 시 화면 이동
    fun moveToCategoryDetailActivity(position: Int) {
        val intent = Intent(requireContext(), CategoryDetailActivity::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }

    // 검색 옵션 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_option, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_main_option_search -> {
                showCustomToast("검색 누름")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 광고 이미지 view flipper 구현
    private fun initViewFlipper() {
        runBlocking {
            scope = launch {
                for(image in imageHomeAd) {
                    setViewFlipper(image)
                }
            }
        }
    }

    private fun setViewFlipper(image : Int) {
        val imageView = ImageView(context)
        imageView.setBackgroundResource(image)

        binding.homeViewFlipper.addView(imageView)
        binding.homeViewFlipper.flipInterval = 3000
        binding.homeViewFlipper.isAutoStart = true
        binding.homeViewFlipper.startFlipping()
    }

    override fun onDestroyView() {
        scope.cancel()
        super.onDestroyView()
    }
}