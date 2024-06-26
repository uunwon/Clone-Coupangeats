package com.yunwoon.coupangeatsproject.src.store

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityStoreBinding
import com.yunwoon.coupangeatsproject.src.cart.CartActivity
import com.yunwoon.coupangeatsproject.src.cart.models.UserCartResponse
import com.yunwoon.coupangeatsproject.src.main.favorite.models.FavoriteStoreResponse
import com.yunwoon.coupangeatsproject.src.reviewlist.ReviewListActivity
import com.yunwoon.coupangeatsproject.src.reviewlist.models.ReviewListResponse
import com.yunwoon.coupangeatsproject.src.store.menu.MenuFragment
import com.yunwoon.coupangeatsproject.src.store.menu.MenuFragmentAdapter
import com.yunwoon.coupangeatsproject.src.store.models.FavoriteResponse
import com.yunwoon.coupangeatsproject.src.store.models.PostFavoriteRequest
import com.yunwoon.coupangeatsproject.src.store.models.StoreCategoryResponse
import com.yunwoon.coupangeatsproject.src.store.models.StoreResponse
import com.yunwoon.coupangeatsproject.src.store.optionmenu.OptionMenuActivity
import com.yunwoon.coupangeatsproject.util.smallReviewRecycler.SmallReviewAdapter
import com.yunwoon.coupangeatsproject.util.smallReviewRecycler.SmallReviewData
import java.io.IOException
import java.net.URL
import kotlin.math.abs

class StoreActivity : BaseActivity<ActivityStoreBinding>(ActivityStoreBinding::inflate),
    AppBarLayout.OnOffsetChangedListener, StoreActivityView {
    private val loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)

    private lateinit var appBarLayout: AppBarLayout
    private lateinit var bottomAppBarLayout: AppBarLayout

    private lateinit var menu : Menu
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
    private var menuCategoryId = ArrayList<Int>()

    private var storeIndex = 0
    private var storeStarRating = 0f
    private var storeReviewCount = 0
    private var storeName = ""

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
        storeStarRating = intent.getFloatExtra("storeStarRating", 0f) // 가게 별점
        storeReviewCount = intent.getIntExtra("storeReviewCount", 0) // 가게 리뷰수

        initStoreView()
        setStoreData()
        setStoreCategoriesData()
        setSmallReviewRecyclerView()

        binding.storeTextDetail.setOnClickListener {
            this.startActivity(Intent(this, StoreDetailActivity::class.java))
        }

        binding.storeImageButtonBack.setOnClickListener { finish() }

        binding.cartToolbar.setOnClickListener {
            // 카트로 이동
            if(loginJwtToken != null)
                this.startActivity(Intent(this, CartActivity::class.java))
            else
                showCustomToast("로그인이 필요한 서비스입니다")
        }

        binding.storeTextReviewGo.setOnClickListener {
            // 리뷰 페이지로 이동
            var intent = Intent(this, ReviewListActivity::class.java)
            intent.putExtra("restaurantId", storeIndex)
            intent.putExtra("storeName", storeName)
            intent.putExtra("storeStarRating", storeStarRating)
            intent.putExtra("storeReviewCount", storeReviewCount)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        if(loginJwtToken != null)
            StoreService(this).tryGetCart(loginJwtToken)
    }

    override fun onGetUserCartSuccess(response: UserCartResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            if(response.result.carts.isNotEmpty())
                binding.storeAppBarLayoutBottom.visibility = View.VISIBLE
            else
                binding.storeAppBarLayoutBottom.visibility = View.GONE
        }
    }

    override fun onGetUserCartFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 찜 여부 확인
    private fun setStoreFavorite() {
        if(loginJwtToken != null)
            StoreService(this).tryGetFavorite(loginJwtToken)
    }

    override fun onGetFavoriteSuccess(storeResponse: FavoriteStoreResponse) {
        dismissLoadingDialog()
        if(storeResponse.isSuccess && storeResponse.result.isNotEmpty()) {
            for (i in storeResponse.result) {
                if(storeIndex == i.restaurantId)
                    menu.getItem(1).setIcon(R.drawable.ic_my_page_test2)
            }
        }
    }

    override fun onGetFavoriteFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // store 화면 세팅
    private fun initStoreView() {
        setSupportActionBar(binding.toolbar)
        appBarLayout = binding.appBarLayout
        bottomAppBarLayout = binding.storeAppBarLayoutBottom

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

            storeName = response.result.restaurantResult[0].name
            binding.storeTextTitle.text = response.result.restaurantResult[0].name
            binding.storeTextToolbar.text = response.result.restaurantResult[0].name
            binding.storeTextDeliveryTip.text = "${response.result.restaurantResult[0].delieveryFee}원"
            binding.storeTextLestDeliveryTip.text = "${response.result.restaurantResult[0].minOrderPrice}원"
            binding.storeTextStarRating.text = storeStarRating.toString()
            binding.storeTextReviewCount.text = storeReviewCount.toString()
        } else {
            showCustomToast("가게를 받아오지 못했습니다!")
        }
    }

    override fun onGetStoreFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 옵션 메뉴 세팅
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_store, menu)
        if(menu != null) {
            this.menu = menu
            menuIconDrawable1 = menu.getItem(0).icon
            menuIconDrawable2 = menu.getItem(1).icon
        }
        menuIconDrawable1.mutate()
        menuIconDrawable2.mutate()

        setStoreFavorite()

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
            menu.getItem(1).setIcon(R.drawable.ic_my_page_test2)
        } else
            showCustomToast("이미 누르셨습니다")
    }

    override fun onPostFavoriteFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (appBarLayout != null) {
            menuIconDrawable2 = menu.getItem(1).icon
            menuIconDrawable2.mutate()

            when {
                //  State Expanded
                verticalOffset == 0 -> {
                    binding.storeImageButtonBack.setBackgroundResource(R.drawable.ic_toolbar_back_white)
                    binding.storeTextToolbar.setTextColor(resources.getColor(R.color.transparency))
                    menuIconDrawable1.colorFilter = whiteFilter
                    menuIconDrawable2.colorFilter = whiteFilter
                    window.statusBarColor = resources.getColor(R.color.black)
                    window.decorView.systemUiVisibility = 0
                }
                //  State Collapsed
                abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                    binding.storeImageButtonBack.setBackgroundResource(R.drawable.ic_toolbar_back)
                    binding.storeTextToolbar.setTextColor(resources.getColor(R.color.black))
                    menuIconDrawable1.colorFilter = blackFilter
                    menuIconDrawable2.colorFilter = redFilter
                    window.statusBarColor = resources.getColor(R.color.white)
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

        StoreService(this).tryGetReviews(storeIndex, "")
    }


    override fun onGetReviewsSuccess(response: ReviewListResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            for(i in response.result) {
                smallReviewData.add(SmallReviewData("https://user-images.githubusercontent.com/48541984/130749327-a7a39747-11c4-4135-9b24-baaa7fe9e7db.png", i.review, i.rating.toFloat()))
            }
            smallReviewAdapter.smallReviewDataArrayList = smallReviewData
            smallReviewAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetReviewsFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 메뉴 안으로 들어가기 --> 옵션 선택하는 페이지로!
    fun moveOptionMenuActivity(menuIndex : Int, menuImage: String, menuTitle: String, menuDetail: String, menuPrice: String) {
        val intent = Intent(this, OptionMenuActivity::class.java)
        intent.putExtra("menuIndex", menuIndex)
        intent.putExtra("menuImage", menuImage)
        intent.putExtra("menuTitle", menuTitle)
        intent.putExtra("menuDetail", menuDetail)
        intent.putExtra("menuPrice", menuPrice)

        this.startActivity(intent)
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
                menuCategoryId.add(i.id)
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
            verticalFragmentAdapter.addFragment(MenuFragment(menuCategoryTab[i], storeIndex, menuCategoryId[i]))

        verticalFragmentAdapter.setType(verticalFragmentAdapter.TYPE_VERTICAL_VIEWPAGER)

        /* binding.storeViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val view = (binding.storeViewPager[0] as RecyclerView).layoutManager?.findViewByPosition(position)
                view?.post {
                    val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
                    val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    view.measure(wMeasureSpec, hMeasureSpec)
                    if (binding.storeViewPager.layoutParams.height != view.measuredHeight) {
                        binding.storeViewPager.layoutParams = (binding.storeViewPager.layoutParams).also { lp -> lp.height = view.measuredHeight }
                    }
                }
            }
        }) */

        binding.storeViewPager.isNestedScrollingEnabled = true
        binding.storeViewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.storeViewPager.adapter = verticalFragmentAdapter

        TabLayoutMediator(binding.storeTabLayout, binding.storeViewPager){tab, position ->
            tab.text = menuCategoryTab[position]  // 탭 이름 생성
        }.attach()
    }
}