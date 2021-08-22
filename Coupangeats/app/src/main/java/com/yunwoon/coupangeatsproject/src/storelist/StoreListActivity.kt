package com.yunwoon.coupangeatsproject.src.storelist

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityStoreListBinding
import com.yunwoon.coupangeatsproject.src.main.home.HomeFragment
import com.yunwoon.coupangeatsproject.src.main.home.models.CategoryResponse
import com.yunwoon.coupangeatsproject.src.main.home.models.HomeResponse
import com.yunwoon.coupangeatsproject.src.store.StoreActivity
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

    private lateinit var categoryAdapter: CategoryListAdapter
    private val categoryData = mutableListOf<CategoryData>()

    private lateinit var newStoreAdapter: SmallStoreAdapter
    private val newStoreData = mutableListOf<SmallStoreData>()

    private lateinit var storeListAdapter: StoreListAdapter
    private val storeListData = mutableListOf<StoreData>()

    private lateinit var reSources : Resources
    private lateinit var bitmap1 : Bitmap
    private lateinit var bitmap2 : Bitmap
    private lateinit var bitmap3 : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val position = intent.getIntExtra("position", 0)
        showCustomToast("$position")

        reSources = this.resources
        bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.test_home_store1)
        bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.test_home_store2)
        bitmap3 = BitmapFactory.decodeResource(resources, R.drawable.test_home_store3)

        setStoreListView()
        setCategoryRecyclerView()
        setNewRecyclerView()
        setStoreListRecyclerView()
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

    // 카테고리 세팅
    private fun setCategoryRecyclerView() {
        binding.storeListRecyclerViewCategory.layoutManager = clayoutManager
        binding.storeListRecyclerViewCategory.isNestedScrollingEnabled = true

        categoryAdapter = CategoryListAdapter(this)
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

    fun changeCategory(position : Int) {
        showCustomToast("$position 번째 카테고리 선택")
    }

    // 새로 들어왔어요 리스트 세팅
    private fun setNewRecyclerView() {
        binding.storeListRecyclerViewNew.layoutManager = nlayoutManager
        binding.storeListRecyclerViewNew.isNestedScrollingEnabled = true

        newStoreAdapter = SmallStoreAdapter(this, HomeFragment())
        binding.storeListRecyclerViewNew.adapter = newStoreAdapter

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

    // 가게 리스트 세팅
    private fun setStoreListRecyclerView() {
        binding.storeListRecyclerView.layoutManager = slayoutManager
        binding.storeListRecyclerView.isNestedScrollingEnabled = true

        storeListAdapter = StoreListAdapter(this)
        binding.storeListRecyclerView.adapter = storeListAdapter

        // showLoadingDialog(this)
        StoreListService(this).tryGetRestaurants()
    }

    override fun onGetRestaurantsSuccess(response: HomeResponse) {
        // dismissLoadingDialog()
        if(response.isSuccess) {
            for (i in response.result.restaurantResult) {
                storeListData.add(StoreData(bitmap1, bitmap2, bitmap3, i.name, "10-20분", i.ratingAvg.toString(), "(${i.reviewCount})", "1.1km", i.deliveryFee+"원"))
            }

            storeListAdapter.storeDataArrayList = storeListData
            storeListAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetRestaurantsFailure(message: String) {
        // dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    fun moveToStoreActivity(position: Int) {
        this.startActivity(Intent(this, StoreActivity::class.java))
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