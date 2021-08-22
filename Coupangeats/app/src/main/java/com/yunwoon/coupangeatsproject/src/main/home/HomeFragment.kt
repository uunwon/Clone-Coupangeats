package com.yunwoon.coupangeatsproject.src.main.home

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.ChipGroup
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentHomeBinding
import com.yunwoon.coupangeatsproject.src.address.AddressActivity
import com.yunwoon.coupangeatsproject.src.main.home.dialogs.ChipArrangeDialog
import com.yunwoon.coupangeatsproject.src.main.home.models.CategoryResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.HomeResponse
import com.yunwoon.coupangeatsproject.src.store.StoreActivity
import com.yunwoon.coupangeatsproject.src.storelist.StoreListActivity
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
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home), HomeFragmentView{

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

    private lateinit var reSources : Resources
    private lateinit var bitmap1 : Bitmap
    private lateinit var bitmap2 : Bitmap
    private lateinit var bitmap3 : Bitmap
    private lateinit var bitmap4 : Bitmap

    private var filterCount = 0
    private var chipArrangeDialogNumber = 1
    private val dialogChipArrange = ChipArrangeDialog()
    private var params = ChipGroup.LayoutParams(ChipGroup.LayoutParams.WRAP_CONTENT, ChipGroup.LayoutParams.WRAP_CONTENT)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        reSources = this.resources
        bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.test_home_store1)
        bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.test_home_store2)
        bitmap3 = BitmapFactory.decodeResource(resources, R.drawable.test_home_store3)
        bitmap4 = BitmapFactory.decodeResource(resources, R.drawable.test_category)

        setRecyclerViewLayoutManager()
        setToolBar(binding.homeToolbar)
        setAddress()

        initViewFlipper()
        initRecyclerView()

        // 배달지 주소 설정 화면 이동
        binding.homeTextAddress.setOnClickListener {
            this.startActivity(Intent(requireContext(), AddressActivity::class.java))
        }

        // 매장 정렬 필터
        binding.homeChipStoreFilter.setOnClickListener { setChipArrangeDialog() }
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

    // 카테고리 세팅
    private fun initCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter(requireContext(),this@HomeFragment)
        binding.homeRecyclerViewCategory.adapter = categoryAdapter

        categoryData.add(CategoryData("https://user-images.githubusercontent.com/48541984/130348425-562f1565-7b95-49ff-b5c7-7700377b06a5.jpg", "신규 맛집"))
        categoryData.add(CategoryData("https://user-images.githubusercontent.com/48541984/130348314-9b8b6d34-2e29-4130-9c72-d84d3435beab.jpg", "1인분"))

        HomeService(this).tryGetCategories()
    }

    override fun onGetCategoriesSuccess(response: CategoryResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            for (i in response.result) {
                categoryData.add(CategoryData(i.imgUrl, i.categoryName))
            }

            categoryAdapter.categoryData = categoryData
            categoryAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetCategoriesFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
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
        chooseStoreAdapter = StoreAdapter(requireContext(), this@HomeFragment)
        binding.homeRecyclerViewChoose.adapter = chooseStoreAdapter

        showLoadingDialog(requireContext())
        HomeService(this).tryGetRestaurants()
    }

    override fun onGetRestaurantsSuccess(response: HomeResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            for (i in response.result.restaurantResult) {
                chooseStoreData.add(StoreData(bitmap1, bitmap2, bitmap3, i.name, "10-20분", i.ratingAvg.toString(), "(${i.reviewCount})", "1.1km", i.deliveryFee+"원"))
            }

            chooseStoreAdapter.storeDataArrayList = chooseStoreData
            chooseStoreAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetRestaurantsFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 매장 정렬 chip 필터
    private fun setChipArrangeDialog() {
        dialogChipArrange.setChipDialog(chipArrangeDialogNumber)
        dialogChipArrange.show(requireFragmentManager(), "ChipArrangeDialog")

        dialogChipArrange.setChipDialogResult(object : ChipArrangeDialog.SetChipResult{
            override fun setFilter(dialogResult: Int) {
                filterCount = 1

                when(dialogResult){
                    1 -> { // 추천순
                        binding.homeChipStoreFilter.setChipBackgroundColorResource(R.color.white)
                        binding.homeChipStoreFilter.setTextColor(reSources.getColor(R.color.black))
                        binding.homeChipStoreFilter.setText(R.string.home_chip_recommend)

                        filterCount = 0
                        chipArrangeDialogNumber = 1
                        // 아래 가게 정렬 받아오는 걸로 바뀌기
                    }
                    2 -> { // 주문많은순
                        params.setMargins(0,0,0,0)
                        binding.homeChipStoreFilter.layoutParams = params
                        binding.homeChipStoreFilter.setChipBackgroundColorResource(R.color.blue_300)
                        binding.homeChipStoreFilter.setTextColor(reSources.getColor(R.color.white))
                        binding.homeChipStoreFilter.setText(R.string.home_chip_order)

                        binding.homeChipReset.visibility = View.VISIBLE
                        binding.homeChipReset.setText("초기화 " + filterCount)

                        chipArrangeDialogNumber = 2
                    }
                    3 -> { // 가까운순
                        params.setMargins(0,0,0,0)
                        binding.homeChipStoreFilter.layoutParams = params
                        binding.homeChipStoreFilter.setChipBackgroundColorResource(R.color.blue_300)
                        binding.homeChipStoreFilter.setTextColor(reSources.getColor(R.color.white))
                        binding.homeChipStoreFilter.setText(R.string.home_chip_location)

                        binding.homeChipReset.visibility = View.VISIBLE
                        binding.homeChipReset.setText("초기화 " + filterCount)

                        chipArrangeDialogNumber = 3
                    }
                    4 -> { // 별점높은순
                        params.setMargins(0,0,0,0)
                        binding.homeChipStoreFilter.layoutParams = params
                        binding.homeChipStoreFilter.setChipBackgroundColorResource(R.color.blue_300)
                        binding.homeChipStoreFilter.setTextColor(reSources.getColor(R.color.white))
                        binding.homeChipStoreFilter.setText(R.string.home_chip_star_rating)

                        binding.homeChipReset.visibility = View.VISIBLE
                        binding.homeChipReset.setText("초기화 " + filterCount)

                        chipArrangeDialogNumber = 4
                    }
                    5 -> { // 신규매장순
                        params.setMargins(0,0,0,0)
                        binding.homeChipStoreFilter.layoutParams = params
                        binding.homeChipStoreFilter.setChipBackgroundColorResource(R.color.blue_300)
                        binding.homeChipStoreFilter.setTextColor(reSources.getColor(R.color.white))
                        binding.homeChipStoreFilter.setText(R.string.home_chip_new)

                        binding.homeChipReset.visibility = View.VISIBLE
                        binding.homeChipReset.setText("초기화 " + filterCount)

                        chipArrangeDialogNumber = 5
                    }
                }

                if(filterCount == 0) {
                    binding.homeChipReset.visibility = View.GONE
                    params.setMargins(38,0,0,0)
                    binding.homeChipStoreFilter.layoutParams = params
                }
            }
        })
    }

    // 카테고리 아이템 클릭 시 화면 이동
    fun moveToCategoryDetailActivity(position: Int) {
        val intent = Intent(requireContext(), StoreListActivity::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }

    fun moveToStoreActivity(position: Int) {
        this.startActivity(Intent(requireContext(), StoreActivity::class.java))
    }

    // 옵션 메뉴 - 검색 기능 생성
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_option, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_main_option_search -> {
                this.startActivity(Intent(requireContext(), StoreActivity::class.java))
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