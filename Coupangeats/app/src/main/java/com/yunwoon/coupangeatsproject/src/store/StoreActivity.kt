package com.yunwoon.coupangeatsproject.src.store

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityStoreBinding
import com.yunwoon.coupangeatsproject.src.store.menu.MenuFragment
import com.yunwoon.coupangeatsproject.src.store.menu.MenuFragmentAdapter
import com.yunwoon.coupangeatsproject.src.store.models.FavoriteResponse
import com.yunwoon.coupangeatsproject.src.store.models.PostFavoriteRequest
import com.yunwoon.coupangeatsproject.src.store.models.StoreCategoryResponse
import com.yunwoon.coupangeatsproject.src.store.models.StoreResponse
import com.yunwoon.coupangeatsproject.util.smallReviewRecycler.SmallReviewAdapter
import com.yunwoon.coupangeatsproject.util.smallReviewRecycler.SmallReviewData
import java.io.IOException
import java.net.URL
import kotlin.math.abs


class StoreActivity : BaseActivity<ActivityStoreBinding>(ActivityStoreBinding::inflate),
    AppBarLayout.OnOffsetChangedListener, StoreActivityView {
    private val loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)

    private lateinit var appBarLayout: AppBarLayout
    private var storeIndex = 0

    private lateinit var menuIconDrawable1: Drawable
    private lateinit var menuIconDrawable2: Drawable

    private lateinit var whiteFilter: PorterDuffColorFilter
    private lateinit var blackFilter: PorterDuffColorFilter
    private lateinit var redFilter: PorterDuffColorFilter

    private lateinit var verticalFragmentAdapter: MenuFragmentAdapter

    private lateinit var rlayoutManager: LinearLayoutManager // 수평 레이아웃 매니저

    private lateinit var smallReviewAdapter: SmallReviewAdapter
    private val smallReviewData = mutableListOf<SmallReviewData>()

    private var menuCategoryTab = mutableListOf<String>()

    val tabLayoutTextArray = arrayOf("추천메뉴","세트메뉴","엽기메뉴")

    // 이미지 변환
    val LoadImage = object : AsyncTask<String, Int, Bitmap?>() {
        var bitmap : Bitmap? = null

        override fun doInBackground(vararg p0: String?): Bitmap? {
            try {
                val inputStream = URL(p0[0]).openStream()
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e : IOException) {
                e.printStackTrace()
            }
            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            binding.storeImageViewMain.setImageBitmap(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storeIndex = intent.getIntExtra("storeIndex", 0) // 가게 인덱스 받아옴
        Log.d("StoreActivity", "$storeIndex")

        initStoreView()
        setStoreData()
        setStoreCategoriesData()
        setSmallReviewRecyclerView()

        binding.storeTextDetail.setOnClickListener {
            this.startActivity(Intent(this, StoreDetailActivity::class.java))
        }

        binding.storeImageButtonBack.setOnClickListener { finish() }
    }

    // store 화면 세팅
    private fun initStoreView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        setSupportActionBar(binding.toolbar)
        appBarLayout = binding.appBarLayout

        whiteFilter = PorterDuffColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
        blackFilter = PorterDuffColorFilter(resources.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP)
        redFilter = PorterDuffColorFilter(resources.getColor(R.color.red_900), PorterDuff.Mode.SRC_ATOP)
    }

    // 해당 가게 데이터 가져오기
    private fun setStoreData() {
        showLoadingDialog(this)
        StoreService(this).tryGetStore(storeIndex)
    }

    override fun onGetStoreSuccess(response: StoreResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            if(response.result.imgResult != null)
                LoadImage.execute(response.result.imgResult[0].imgUrl)

            binding.storeTextTitle.text = response.result.restaurantResult[0].name
            binding.storeTextToolbar.text = response.result.restaurantResult[0].name
            binding.storeTextDeliveryTip.text = "${response.result.restaurantResult[0].delieveryFee}원"
            binding.storeTextLestDeliveryTip.text = "${response.result.restaurantResult[0].minOrderPrice}원"
        } else {
            showCustomToast("가게를 받아오지 못했습니다!")
        }
    }

    override fun onGetStoreFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
        Log.d("오류", "$message")
    }

    // 옵션 메뉴 세팅
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_store, menu)
        if(menu != null) {
            menuIconDrawable1 = menu.getItem(0).icon
            menuIconDrawable2 = menu.getItem(1).icon
        }
        menuIconDrawable1.mutate()
        menuIconDrawable2.mutate()

        appBarLayout.addOnOffsetChangedListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_store_share -> {
                showCustomToast("공유되었습니다")
            }
            R.id.menu_store_favorite -> {
                if(loginJwtToken != null) {
                    showLoadingDialog(this)
                    val postFavoriteRequest = PostFavoriteRequest(restaurantId = storeIndex)
                    StoreService(this).tryPostFavorite(loginJwtToken, postFavoriteRequest)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPostFavoriteSuccess(response: FavoriteResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            showCustomToast(response.result)
        } else
            showCustomToast("이미 누르셨습니다")
    }

    override fun onPostFavoriteFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (appBarLayout != null) {
            when {
                //  State Expanded
                verticalOffset == 0 -> {
                    binding.storeImageButtonBack.setBackgroundResource(R.drawable.ic_toolbar_back_white)
                    binding.storeTextToolbar.setTextColor(resources.getColor(R.color.transparency))
                    menuIconDrawable1.colorFilter = whiteFilter
                    menuIconDrawable2.colorFilter = whiteFilter
                    window.decorView.systemUiVisibility = 0
                }
                //  State Collapsed
                abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                    binding.storeImageButtonBack.setBackgroundResource(R.drawable.ic_toolbar_back)
                    binding.storeTextToolbar.setTextColor(resources.getColor(R.color.black))
                    menuIconDrawable1.colorFilter = blackFilter
                    menuIconDrawable2.colorFilter = redFilter
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }
    }

    // 작은 리뷰 RecyclerView 세팅
    private fun setSmallReviewRecyclerView() {
        rlayoutManager = LinearLayoutManager(this)
        rlayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        binding.storeRecyclerViewReview.layoutManager = rlayoutManager
        binding.storeRecyclerViewReview.isNestedScrollingEnabled = true

        smallReviewAdapter = SmallReviewAdapter(this)
        binding.storeRecyclerViewReview.adapter = smallReviewAdapter

        val resources: Resources = this.resources
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_small_review)

        smallReviewData.apply {
            add(SmallReviewData(bitmap, "이번엔 도우가 좀 질겼어요. 그래도 피자는 여기로 정착!", "★★★★★"))
            add(SmallReviewData(bitmap, "너무 맛나요~ 피자는 이 집이 최고에요!!", "★★★"))
            add(SmallReviewData(bitmap, "맛있어서 순삭. 배달 빨라서 피자 뜨끈뜨끈해서 좋아요", "★★★★"))
        }

        smallReviewAdapter.smallReviewDataArrayList = smallReviewData
    }

    fun moveActivity(position : Int) {
        this.startActivity(Intent(this, MenuActivity::class.java))
    }

    // 한 가게의 카테고리 받아오기
    private fun setStoreCategoriesData() {
        StoreService(this).tryGetStoreCategories(storeIndex)
    }

    override fun onGetStoreCategoriesSuccess(response: StoreCategoryResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            var index = 0
            for(i in response.result) {
                menuCategoryTab.add(index++, i.categoryName)
            }
            setStoreViewPager()
        } else {
            showCustomToast("가게 카테고리를 받아오지 못했습니다!")
        }
    }

    override fun onGetStoreCategoriesFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    private fun setStoreViewPager() {
        verticalFragmentAdapter = MenuFragmentAdapter(this)
        for(i in 0 until menuCategoryTab.size)
            verticalFragmentAdapter.addFragment(MenuFragment(menuCategoryTab[i], storeIndex, i+1))

        verticalFragmentAdapter.setType(verticalFragmentAdapter.TYPE_VERTICAL_VIEWPAGER)

        binding.storeViewPager.isNestedScrollingEnabled = true
        binding.storeViewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.storeViewPager.adapter = verticalFragmentAdapter

        TabLayoutMediator(binding.storeTabLayout, binding.storeViewPager){tab, position ->
            tab.text = menuCategoryTab[position]  // 탭 이름 생성
        }.attach()
    }
}