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
import com.yunwoon.coupangeatsproject.src.category.CategoryDetailActivity
import com.yunwoon.coupangeatsproject.util.categoryRecycler.CategoryAdapter
import com.yunwoon.coupangeatsproject.util.categoryRecycler.CategoryData
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home){

    private val imageHomeAd : Array<Int> = arrayOf(R.drawable.image_home_ad_1, R.drawable.image_home_ad_2)
    private lateinit var scope : Job


    private lateinit var mlayoutManager : LinearLayoutManager
    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryData = mutableListOf<CategoryData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        mlayoutManager = LinearLayoutManager(requireContext())
        mlayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.homeRecyclerViewCategory.layoutManager = mlayoutManager
        binding.homeRecyclerViewCategory.isNestedScrollingEnabled = true

        setToolBar(binding.homeToolbar)
        initViewFlipper()
        initCategoryRecyclerView()
    }

    // 카테고리 리사이클러뷰 세팅
    private fun initCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter(requireContext(), this@HomeFragment)
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