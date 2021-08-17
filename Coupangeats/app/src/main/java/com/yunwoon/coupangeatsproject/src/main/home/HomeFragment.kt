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
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentHomeBinding
import com.yunwoon.coupangeatsproject.src.address.AddressActivity
import com.yunwoon.coupangeatsproject.src.category.CategoryDetailActivity
import com.yunwoon.coupangeatsproject.util.categoryRecycler.CategoryAdapter
import com.yunwoon.coupangeatsproject.util.categoryRecycler.CategoryData
import com.yunwoon.coupangeatsproject.util.smallStoreRecycler.SmallStoreAdapter
import com.yunwoon.coupangeatsproject.util.smallStoreRecycler.SmallStoreData
import com.yunwoon.coupangeatsproject.util.storeRecycler.StoreAdapter
import com.yunwoon.coupangeatsproject.util.storeRecycler.StoreData
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home){

    private val loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)
    private val imageHomeAd : Array<Int> = arrayOf(R.drawable.image_home_ad_1, R.drawable.image_home_ad_2)

    private lateinit var scope : Job

    private lateinit var clayoutManager : LinearLayoutManager // 수평 레이아웃 매니저
    private lateinit var rlayoutManager : LinearLayoutManager
    private lateinit var hlayoutManager : LinearLayoutManager
    private lateinit var nlayoutManager: LinearLayoutManager
    private lateinit var chlayoutManager: LinearLayoutManager

    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryData = mutableListOf<CategoryData>()

    private lateinit var recommendStoreAdapter: SmallStoreAdapter
    private val recommendStoreData = mutableListOf<SmallStoreData>()

    private lateinit var hotStoreAdapter: SmallStoreAdapter
    private val hotStoreData = mutableListOf<SmallStoreData>()

    private lateinit var newStoreAdapter: SmallStoreAdapter
    private val newStoreData = mutableListOf<SmallStoreData>()

    private lateinit var chooseStoreAdapter: StoreAdapter
    private val chooseStoreData = mutableListOf<StoreData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setRecyclerViewLayoutManager()
        setToolBar(binding.homeToolbar)
        setAddress()

        initViewFlipper()
        initRecyclerView()

        // 배달지 주소 설정 화면 이동
        binding.homeTextAddress.setOnClickListener {
            this.startActivity(Intent(requireContext(), AddressActivity::class.java))
        }
    }

    // 주소 받아오기
    private fun setAddress() {
        if(loginJwtToken != null) {
            binding.homeTextAddress.text = "집"
            // 사용자 주소 받아오기
        } else {
            binding.homeTextAddress.text = "배달 주소를 입력하세요."
            // 최초 init gps 권한 체크
        }
    }

    // RecyclerView LayoutManager 세팅
    private fun setRecyclerViewLayoutManager() {
        clayoutManager = LinearLayoutManager(requireContext())
        clayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rlayoutManager = LinearLayoutManager(requireContext())
        rlayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        hlayoutManager = LinearLayoutManager(requireContext())
        hlayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        nlayoutManager = LinearLayoutManager(requireContext())
        nlayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        chlayoutManager = LinearLayoutManager(requireContext())
        chlayoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.homeRecyclerViewCategory.layoutManager = clayoutManager // 카테고리 리스트
        binding.homeRecyclerViewCategory.isNestedScrollingEnabled = true

        binding.homeRecyclerViewRecommend.layoutManager = rlayoutManager // 추천맛집 리스트
        binding.homeRecyclerViewRecommend.isNestedScrollingEnabled = true

        binding.homeRecyclerViewHot.layoutManager = hlayoutManager // 인기 프랜차이즈 리스트
        binding.homeRecyclerViewHot.isNestedScrollingEnabled = true

        binding.homeRecyclerViewNew.layoutManager = nlayoutManager // 새로 들어왔어요 리스트
        binding.homeRecyclerViewNew.isNestedScrollingEnabled = true

        binding.homeRecyclerViewChoose.layoutManager = chlayoutManager // 골라먹는 맛집 리스트
        binding.homeRecyclerViewChoose.isNestedScrollingEnabled = true
    }

    // RecyclerView init
    private fun initRecyclerView() {
        initCategoryRecyclerView()
        initHotRecyclerView()
        initNewRecyclerView()
        initChooseRecyclerView()

        if(loginJwtToken != null) {
            initRecommendRecyclerView()
        } else {
            binding.homeTextRecommendStore.visibility = View.GONE
            binding.homeRecyclerViewRecommend.visibility = View.GONE
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

    // 로그인 시, 즐겨찾기 기반 추천 맛집 세팅
    private fun initRecommendRecyclerView() {
        recommendStoreAdapter = SmallStoreAdapter(requireContext(), this@HomeFragment)
        binding.homeRecyclerViewRecommend.adapter = recommendStoreAdapter

        val resources : Resources = this.resources
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_recommend_store)

        recommendStoreData.apply {
            add(SmallStoreData(bitmap, "피자헛 당산점", "4.9", "(528)", "0.1km", "3,000원"))
            add(SmallStoreData(bitmap, "피자헛 당산점", "4.8", "(528)", "0.1km", "3,000원"))
            add(SmallStoreData(bitmap, "피자헛 당산점", "4.7", "(528)", "0.1km", "3,000원"))
            add(SmallStoreData(bitmap, "피자헛 당산점", "4.9", "(528)", "0.1km", "3,000원"))
            add(SmallStoreData(bitmap, "피자헛 당산점", "4.9", "(528)", "0.1km", "3,000원"))
        }

        recommendStoreAdapter.smallStoreDataArrayList = recommendStoreData
        binding.homeTextRecommendStore.visibility = View.VISIBLE
        binding.homeRecyclerViewRecommend.visibility = View.VISIBLE
    }

    // 인기 프랜차이즈 세팅
    private fun initHotRecyclerView() {
        hotStoreAdapter = SmallStoreAdapter(requireContext(), this@HomeFragment)
        binding.homeRecyclerViewHot.adapter = hotStoreAdapter

        val resources : Resources = this.resources
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_hot_store)

        hotStoreData.apply {
            add(SmallStoreData(bitmap, "스쿨푸드 문래점", "3.9", "(1,342)", "1.1km", "무료배달"))
            add(SmallStoreData(bitmap, "스쿨푸드 문래점", "3.9", "(1,342)", "1.1km", "무료배달"))
            add(SmallStoreData(bitmap, "스쿨푸드 문래점", "3.9", "(1,342)", "1.1km", "무료배달"))
            add(SmallStoreData(bitmap, "스쿨푸드 문래점", "3.9", "(1,342)", "1.1km", "무료배달"))
            add(SmallStoreData(bitmap, "스쿨푸드 문래점", "3.9", "(1,342)", "1.1km", "무료배달"))
        }

        hotStoreAdapter.smallStoreDataArrayList = hotStoreData
    }

    // 새로 들어왔어요! 세팅
    private fun initNewRecyclerView() {
        newStoreAdapter = SmallStoreAdapter(requireContext(), this@HomeFragment)
        binding.homeRecyclerViewNew.adapter = newStoreAdapter

        val resources : Resources = this.resources
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_new_store)

        newStoreData.apply {
            add(SmallStoreData(bitmap, "쥬씨 선유도점", "5.0", "(13)", "0.8km", "무료배달"))
            add(SmallStoreData(bitmap, "쥬씨 선유도점", "5.0", "(13)", "0.8km", "무료배달"))
            add(SmallStoreData(bitmap, "쥬씨 선유도점", "5.0", "(13)", "0.8km", "무료배달"))
            add(SmallStoreData(bitmap, "쥬씨 선유도점", "5.0", "(13)", "0.8km", "무료배달"))
            add(SmallStoreData(bitmap, "쥬씨 선유도점", "5.0", "(13)", "0.8km", "무료배달"))
        }

        newStoreAdapter.smallStoreDataArrayList = newStoreData

    }

    // 골라먹는 맛집 세팅
    private fun initChooseRecyclerView() {
        chooseStoreAdapter = StoreAdapter(requireContext())
        binding.homeRecyclerViewChoose.adapter = chooseStoreAdapter

        val resources : Resources = this.resources
        val bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.test_home_store1)
        val bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.test_home_store2)
        val bitmap3 = BitmapFactory.decodeResource(resources, R.drawable.test_home_store3)

        chooseStoreData.apply {
            add(StoreData(bitmap1, bitmap2, bitmap3,"진심을 담아내다. 고기장인", "10-20분", "4.8", "(352)", "1.1km", "2,000원"))
            add(StoreData(bitmap1, bitmap2, bitmap3,"진심을 담아내다. 고기장인", "10-20분", "4.8", "(352)", "1.1km", "2,000원"))
       }

        chooseStoreAdapter.storeDataArrayList = chooseStoreData
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