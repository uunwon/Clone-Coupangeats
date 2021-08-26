package com.yunwoon.coupangeatsproject.src.storelist

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.chip.ChipGroup
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityStoreListBinding
import com.yunwoon.coupangeatsproject.src.main.home.HomeFragment
import com.yunwoon.coupangeatsproject.src.main.home.dialogs.ChipArrangeDialog
import com.yunwoon.coupangeatsproject.src.main.home.models.CategoryResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.HomeResponse
import com.yunwoon.coupangeatsproject.src.store.StoreActivity
import com.yunwoon.coupangeatsproject.src.storelist.models.NewStoreResponse
import com.yunwoon.coupangeatsproject.util.categoryRecycler.CategoryData
import com.yunwoon.coupangeatsproject.util.categoryRecycler.CategoryListAdapter
import com.yunwoon.coupangeatsproject.util.smallStoreRecycler.SmallStoreAdapter
import com.yunwoon.coupangeatsproject.util.smallStoreRecycler.SmallStoreData
import com.yunwoon.coupangeatsproject.util.storeRecycler.StoreData
import com.yunwoon.coupangeatsproject.util.storeRecycler.StoreListAdapter

class StoreListActivity : BaseActivity<ActivityStoreListBinding>(ActivityStoreListBinding::inflate), StoreListActivityView {
    private lateinit var appBarLayout: AppBarLayout

    private lateinit var clayoutManager : LinearLayoutManager // 수평 레이아웃 매니저
    private lateinit var nlayoutManager: LinearLayoutManager
    private lateinit var slayoutManager : LinearLayoutManager

    private var categoryPosition = 0

    private lateinit var categoryAdapter: CategoryListAdapter
    private val categoryData = mutableListOf<CategoryData>()

    private lateinit var newStoreAdapter: SmallStoreAdapter
    private val newStoreData = mutableListOf<SmallStoreData>()

    private lateinit var storeListAdapter: StoreListAdapter
    private val storeListData = mutableListOf<StoreData>()

    private lateinit var reSources : Resources
    private lateinit var bitmap1 : Bitmap

    private var filterCount = 0
    private val dialogChipArrange = ChipArrangeDialog()
    private var chipArrangeDialogNumber = 1
    private var params = ChipGroup.LayoutParams(ChipGroup.LayoutParams.WRAP_CONTENT, ChipGroup.LayoutParams.WRAP_CONTENT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryPosition = intent.getIntExtra("position", 0) // 카테고리 인덱스 받아옴
        binding.storeListTitle.text = intent.getStringExtra("categoryTextTitle")

        reSources = this.resources
        bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.test_home_store1)

        setStoreListView()
        setLinearLayout()
        setCategoryRecyclerView()
        setNewRecyclerView()
        setStoreListRecyclerView()

        binding.storeListChipStoreFilter.setOnClickListener { setChipArrangeDialog() }
        binding.storeListImageButtonBack.setOnClickListener { finish() }
    }

    private fun setStoreListView() {
        setSupportActionBar(binding.storeListToolbar)
        appBarLayout = binding.storeListAppBarLayout

        clayoutManager = LinearLayoutManager(this)
        clayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        nlayoutManager = LinearLayoutManager(this)
        nlayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        slayoutManager = LinearLayoutManager(this)
        slayoutManager.orientation = LinearLayoutManager.VERTICAL
    }

    private fun setLinearLayout() {
        binding.storeListRecyclerViewCategory.layoutManager = clayoutManager
        binding.storeListRecyclerViewCategory.isNestedScrollingEnabled = true

        binding.storeListRecyclerViewNew.layoutManager = nlayoutManager
        binding.storeListRecyclerViewNew.isNestedScrollingEnabled = true

        binding.storeListRecyclerView.layoutManager = slayoutManager
        binding.storeListRecyclerView.isNestedScrollingEnabled = true
    }

    // 카테고리 세팅
    private fun setCategoryRecyclerView() {
        categoryAdapter = CategoryListAdapter(this, categoryPosition)
        binding.storeListRecyclerViewCategory.adapter = categoryAdapter

        categoryData.add(CategoryData("https://user-images.githubusercontent.com/48541984/130348425-562f1565-7b95-49ff-b5c7-7700377b06a5.jpg", "신규 맛집"))
        categoryData.add(CategoryData("https://user-images.githubusercontent.com/48541984/130348314-9b8b6d34-2e29-4130-9c72-d84d3435beab.jpg", "1인분"))

        showLoadingDialog(this)
        StoreListService(this).tryGetCategories()
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

    fun changeCategory(position : Int, categoryTextTitle: String) {
        categoryPosition = position
        binding.storeListTitle.text = categoryTextTitle

        categoryData.clear()
        newStoreData.clear()
        storeListData.clear()

        setCategoryRecyclerView()
        setNewRecyclerView()
        setStoreListRecyclerView()
    }

    // 매장 정렬 chip 필터
    private fun setChipArrangeDialog() {
        dialogChipArrange.setChipDialog(chipArrangeDialogNumber)
        dialogChipArrange.show(supportFragmentManager, "ChipArrangeDialog")

        dialogChipArrange.setChipDialogResult(object : ChipArrangeDialog.SetChipResult{
            override fun setFilter(dialogResult: Int) {
                filterCount = 1

                when(dialogResult){
                    1 -> { // 추천순
                        binding.storeListChipStoreFilter.setChipBackgroundColorResource(R.color.white)
                        binding.storeListChipStoreFilter.setTextColor(reSources.getColor(R.color.black))
                        binding.storeListChipStoreFilter.setText(R.string.home_chip_recommend)

                        filterCount = 0
                        chipArrangeDialogNumber = 1
                    }
                    2 -> { // 주문많은순
                        params.setMargins(0,0,0,0)
                        binding.storeListChipStoreFilter.layoutParams = params
                        binding.storeListChipStoreFilter.setChipBackgroundColorResource(R.color.blue_300)
                        binding.storeListChipStoreFilter.setTextColor(reSources.getColor(R.color.white))
                        binding.storeListChipStoreFilter.setText(R.string.home_chip_order)

                        binding.storeListChipReset.visibility = View.VISIBLE
                        binding.storeListChipReset.setText("초기화 " + filterCount)

                        chipArrangeDialogNumber = 2
                    }
                    3 -> { // 가까운순
                        params.setMargins(0,0,0,0)
                        binding.storeListChipStoreFilter.layoutParams = params
                        binding.storeListChipStoreFilter.setChipBackgroundColorResource(R.color.blue_300)
                        binding.storeListChipStoreFilter.setTextColor(reSources.getColor(R.color.white))
                        binding.storeListChipStoreFilter.setText(R.string.home_chip_location)

                        binding.storeListChipReset.visibility = View.VISIBLE
                        binding.storeListChipReset.setText("초기화 " + filterCount)

                        chipArrangeDialogNumber = 3
                    }
                    4 -> { // 별점높은순
                        params.setMargins(0,0,0,0)
                        binding.storeListChipStoreFilter.layoutParams = params
                        binding.storeListChipStoreFilter.setChipBackgroundColorResource(R.color.blue_300)
                        binding.storeListChipStoreFilter.setTextColor(reSources.getColor(R.color.white))
                        binding.storeListChipStoreFilter.setText(R.string.home_chip_star_rating)

                        binding.storeListChipReset.visibility = View.VISIBLE
                        binding.storeListChipReset.setText("초기화 " + filterCount)

                        chipArrangeDialogNumber = 4
                    }
                    5 -> { // 신규매장순
                        params.setMargins(0,0,0,0)
                        binding.storeListChipStoreFilter.layoutParams = params
                        binding.storeListChipStoreFilter.setChipBackgroundColorResource(R.color.blue_300)
                        binding.storeListChipStoreFilter.setTextColor(reSources.getColor(R.color.white))
                        binding.storeListChipStoreFilter.setText(R.string.home_chip_new)

                        binding.storeListChipReset.visibility = View.VISIBLE
                        binding.storeListChipReset.setText("초기화 " + filterCount)

                        chipArrangeDialogNumber = 5
                    }
                }

                if(filterCount == 0) {
                    binding.storeListChipReset.visibility = View.GONE
                    params.setMargins(38,0,0,0)
                    binding.storeListChipStoreFilter.layoutParams = params
                }
            }
        })
    }

    // 새로 들어왔어요 리스트 세팅
    private fun setNewRecyclerView() {
        newStoreAdapter = SmallStoreAdapter(this, HomeFragment())
        binding.storeListRecyclerViewNew.adapter = newStoreAdapter

        StoreListService(this).tryGetNewRestaurants(categoryPosition, "new")
    }

    override fun onGetNewRestaurantsSuccess(response: NewStoreResponse, categoryId: Int, order: String) {
        dismissLoadingDialog()
        if(response.isSuccess && response.result.isNotEmpty()) {
            // 새로 들어왔어요
            for (i in response.result) {
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
        } else
            showCustomToast("신규 가게가 없습니다")
    }

    override fun onGetNewRestaurantsFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 가게 리스트 세팅
    private fun setStoreListRecyclerView() {
        storeListAdapter = StoreListAdapter(this)
        binding.storeListRecyclerView.adapter = storeListAdapter

        // showLoadingDialog(this)
        StoreListService(this).tryGetRestaurantswithCategory(categoryPosition)
    }

    // 카테고리별 가게 받아온 거!
    override fun onGetRestaurantswithCategorySuccess(response: HomeResponse, categoryId: Int) {
        dismissLoadingDialog()
        if(response.isSuccess && response.result.restaurantResult.isNotEmpty()) {
            for (i in response.result.restaurantResult) {
                if(i.imgUrl != null) {
                    if(i.ratingAvg.length > 2)
                        storeListData.add(StoreData(i.id, i.imgUrl, i.name, "10-20분", i.ratingAvg.substring(0,3), i.reviewCount.toString(), "1.1km", i.deliveryFee+"원"))
                    else
                        storeListData.add(StoreData(i.id, i.imgUrl, i.name, "10-20분", i.ratingAvg, i.reviewCount.toString(), "1.1km", i.deliveryFee+"원"))

                }
                else
                    storeListData.add(StoreData(i.id, "https://user-images.githubusercontent.com/48541984/130389421-9118e255-0e59-4060-9746-c62098c0c913.jpg", i.name, "10-20분", i.ratingAvg.toString(), "(${i.reviewCount})", "1.1km", i.deliveryFee+"원"))
            }

            storeListAdapter.storeDataArrayList = storeListData
            storeListAdapter.notifyDataSetChanged()
        } else {
            showCustomToast("가게 데이터가 없습니다")
        }
    }

    override fun onGetRestaurantswithCategoryFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    fun moveToStoreActivity(storeIndex: Int, storeStarRating: Float, storeReviewCount: Int) {
        val intent = Intent(this, StoreActivity::class.java)
        intent.putExtra("storeIndex", storeIndex)
        intent.putExtra("storeStarRating", storeStarRating)
        intent.putExtra("storeReviewCount", storeReviewCount)
        startActivity(intent)
    }

    // 옵션 메뉴 - 검색 기능 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_main_option_search -> {
                showCustomToast("검색하겠습니까?")
                // this.tartActivity(Intent(this, StoreActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}