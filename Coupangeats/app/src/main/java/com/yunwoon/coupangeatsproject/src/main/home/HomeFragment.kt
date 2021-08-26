package com.yunwoon.coupangeatsproject.src.main.home

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.ChipGroup
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentHomeBinding
import com.yunwoon.coupangeatsproject.src.address.AddressActivity
import com.yunwoon.coupangeatsproject.src.address.models.AddressResponse
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
    private lateinit var homeBitmap : Bitmap
    private lateinit var categoryBitmap : Bitmap
    private lateinit var newBitmap : Bitmap
    private lateinit var hotBitmap : Bitmap

    private var filterCount = 0
    private var chipArrangeDialogNumber = 1
    private lateinit var order : String
    private val dialogChipArrange = ChipArrangeDialog()
    private var params = ChipGroup.LayoutParams(ChipGroup.LayoutParams.WRAP_CONTENT, ChipGroup.LayoutParams.WRAP_CONTENT)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        reSources = this.resources
        homeBitmap = BitmapFactory.decodeResource(resources, R.drawable.test_home_store1)
        categoryBitmap = BitmapFactory.decodeResource(resources, R.drawable.test_category)
        newBitmap = BitmapFactory.decodeResource(resources, R.drawable.test_new_store)
        hotBitmap = BitmapFactory.decodeResource(resources, R.drawable.test_hot_store)

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
            // 사용자 주소 받아오기
            HomeService(this).tryGetAddress(loginJwtToken)
        } else {
            binding.homeTextAddress.text = "배달 주소를 입력하세요."
        }
    }

    override fun onGetAddressSuccess(response: AddressResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            for(i in response.result)
                binding.homeTextAddress.text = i.location
        } else {
            showCustomToast("사용자의 위치를 받아오는데 실패했습니다")
        }
    }

    override fun onGetAddressFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
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
            // initRecommendRecyclerView()
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

        recommendStoreData.apply {
            add(SmallStoreData(1, "https://user-images.githubusercontent.com/48541984/130389421-9118e255-0e59-4060-9746-c62098c0c913.jpg", "피자헛 당산점", "4.9", "(528)", "0.1km", "3,000원"))
            add(SmallStoreData(2, "https://user-images.githubusercontent.com/48541984/130389421-9118e255-0e59-4060-9746-c62098c0c913.jpg", "피자헛 당산점", "4.8", "(528)", "0.1km", "3,000원"))
            add(SmallStoreData(3, "https://user-images.githubusercontent.com/48541984/130389421-9118e255-0e59-4060-9746-c62098c0c913.jpg", "피자헛 당산점", "4.7", "(528)", "0.1km", "3,000원"))
            add(SmallStoreData(4, "https://user-images.githubusercontent.com/48541984/130389421-9118e255-0e59-4060-9746-c62098c0c913.jpg", "피자헛 당산점", "4.9", "(528)", "0.1km", "3,000원"))
            add(SmallStoreData(5, "https://user-images.githubusercontent.com/48541984/130389421-9118e255-0e59-4060-9746-c62098c0c913.jpg", "피자헛 당산점", "4.9", "(528)", "0.1km", "3,000원"))
        }

        recommendStoreAdapter.smallStoreDataArrayList = recommendStoreData
        binding.homeTextRecommendStore.visibility = View.VISIBLE
        binding.homeRecyclerViewRecommend.visibility = View.VISIBLE
    }

    // 인기 프랜차이즈 세팅
    private fun initHotRecyclerView() {
        hotStoreAdapter = SmallStoreAdapter(requireContext(), this@HomeFragment)
        binding.homeRecyclerViewHot.adapter = hotStoreAdapter

        order = "best"
        HomeService(this).tryGetOrderRestaurants(order)
    }

    // 새로 들어왔어요! 세팅
    private fun initNewRecyclerView() {
        newStoreAdapter = SmallStoreAdapter(requireContext(), this@HomeFragment)
        binding.homeRecyclerViewNew.adapter = newStoreAdapter

        order = "new"
        HomeService(this).tryGetOrderRestaurants(order)
    }

    override fun onGetOrderRestaurantsSuccess(response: HomeResponse, order: String) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            // 새로 들어왔어요
            if(order == "new") {
                for (i in response.result.restaurantResult) {
                    if(i.imgUrl != null) {
                        if(i.ratingAvg.length > 2)
                            newStoreData.add(SmallStoreData(i.id, i.imgUrl, i.name, i.ratingAvg.substring(0,3), i.reviewCount.toString(), "0.8km", "${i.deliveryFee}원"))
                        else
                            newStoreData.add(SmallStoreData(i.id, i.imgUrl, i.name, i.ratingAvg, i.reviewCount.toString(), "0.8km", "${i.deliveryFee}원"))
                    }
                    else
                        newStoreData.add(SmallStoreData(i.id, "https://user-images.githubusercontent.com/48541984/130389421-9118e255-0e59-4060-9746-c62098c0c913.jpg", i.name, i.ratingAvg.toString(), "(${i.reviewCount})", "1.1km", i.deliveryFee+"원"))
                }

                newStoreAdapter.smallStoreDataArrayList = newStoreData
                newStoreAdapter.notifyDataSetChanged()
            }
            // 인기 프랜차이즈 // 별점 높은 순
            else if(order == "best") {
                for (i in response.result.restaurantResult) {
                    if(i.imgUrl != null){
                        if(i.ratingAvg.length > 2)
                            hotStoreData.add(SmallStoreData(i.id, i.imgUrl, i.name, i.ratingAvg.substring(0,3), i.reviewCount.toString(), "1.8km", "${i.deliveryFee}원"))
                        else
                            hotStoreData.add(SmallStoreData(i.id, i.imgUrl, i.name, i.ratingAvg, i.reviewCount.toString(), "1.8km", "${i.deliveryFee}원"))
                    }else
                        hotStoreData.add(SmallStoreData(i.id, "https://user-images.githubusercontent.com/48541984/130389421-9118e255-0e59-4060-9746-c62098c0c913.jpg", i.name, i.ratingAvg.toString(), "(${i.reviewCount})", "1.8km", i.deliveryFee+"원"))
                }

                hotStoreAdapter.smallStoreDataArrayList = hotStoreData
                hotStoreAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onGetOrderRestaurantsFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 골라먹는 맛집 세팅
    private fun initChooseRecyclerView() {
        chooseStoreAdapter = StoreAdapter(requireContext(), this@HomeFragment)
        binding.homeRecyclerViewChoose.adapter = chooseStoreAdapter

        chooseStoreData.clear()

        showLoadingDialog(requireContext())
        HomeService(this).tryGetMainRestaurants()
    }

    override fun onGetMainRestaurantsSuccess(response: HomeResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            for (i in response.result.restaurantResult) {
                if(i.imgUrl != null) {
                    if(i.ratingAvg.length > 2)
                        chooseStoreData.add(StoreData(i.id, i.imgUrl, i.name, "10-20분", i.ratingAvg.substring(0,3), i.reviewCount.toString(), "1.1km", i.deliveryFee+"원"))
                    else
                        chooseStoreData.add(StoreData(i.id, i.imgUrl, i.name, "10-20분", i.ratingAvg, i.reviewCount.toString(), "1.1km", i.deliveryFee+"원"))
                }
                else
                    chooseStoreData.add(StoreData(i.id, "https://user-images.githubusercontent.com/48541984/130389421-9118e255-0e59-4060-9746-c62098c0c913.jpg", i.name, "10-20분", i.ratingAvg.toString(), "(${i.reviewCount})", "1.1km", i.deliveryFee+"원"))
            }

            chooseStoreAdapter.storeDataArrayList = chooseStoreData
            chooseStoreAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetMainRestaurantsFailure(message: String) {
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
                        initChooseRecyclerView()
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
                        initArrangeChip()
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

    private fun initArrangeChip() {
        showLoadingDialog(requireContext())
        HomeService(this).tryGetOrderMainRestaurants("new")
    }

    override fun onGetOrderMainRestaurantsSuccess(response: HomeResponse, order: String) {
        Log.d("initArrangeChip", "onGetOrderMainRestaurantsSuccess 들어옴")
        chooseStoreAdapter = StoreAdapter(requireContext(), this@HomeFragment)
        chooseStoreData.clear()

        dismissLoadingDialog()

        if(response.isSuccess) {
            Log.d("initArrangeChip", "isSuccess 들어옴")
            // 신규 매장 순
            if(order == "new") {
                for (i in response.result.restaurantResult) {
                    if(i.imgUrl != null) {
                        if(i.ratingAvg.length > 2)
                            chooseStoreData.add(StoreData(i.id, i.imgUrl, i.name, "10-20분", i.ratingAvg.substring(0,3), i.reviewCount.toString(), "1.1km", i.deliveryFee+"원"))
                        else
                            chooseStoreData.add(StoreData(i.id, i.imgUrl, i.name, "10-20분", i.ratingAvg, i.reviewCount.toString(), "1.1km", i.deliveryFee+"원"))
                    }
                        else
                            chooseStoreData.add(StoreData(i.id, "https://user-images.githubusercontent.com/48541984/130389421-9118e255-0e59-4060-9746-c62098c0c913.jpg", i.name, "10-20분", i.ratingAvg.toString(), "(${i.reviewCount})", "1.1km", i.deliveryFee+"원"))
                }
                Log.d("initArrangeChip", "chooseStoreData 데이터는? $chooseStoreData")
                chooseStoreAdapter.storeDataArrayList = chooseStoreData
                binding.homeRecyclerViewChoose.adapter = chooseStoreAdapter
                chooseStoreAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onGetOrderMainRestaurantsFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 카테고리 아이템 클릭 시 화면 이동
    fun moveToCategoryDetailActivity(position: Int, categoryTextTitle: String) {
        val intent = Intent(requireContext(), StoreListActivity::class.java)
        intent.putExtra("position", position)
        intent.putExtra("categoryTextTitle", categoryTextTitle)
        startActivity(intent)
    }

    // 특정 가게로 화면 이동
    fun moveToStoreActivity(storeIndex: Int, storeStarRating: Float, storeReviewCount: Int) {
        val intent = Intent(requireContext(), StoreActivity::class.java)
        intent.putExtra("storeIndex", storeIndex)
        intent.putExtra("storeStarRating", storeStarRating)
        intent.putExtra("storeReviewCount", storeReviewCount)
        startActivity(intent)
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