package com.yunwoon.coupangeatsproject.src.reviewlist

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityReviewListBinding
import com.yunwoon.coupangeatsproject.src.reviewlist.dialogs.ReviewArrangeDialog
import com.yunwoon.coupangeatsproject.src.reviewlist.models.ReviewListResponse
import com.yunwoon.coupangeatsproject.util.reviewRecycler.ReviewAdapter
import com.yunwoon.coupangeatsproject.util.reviewRecycler.ReviewData

class ReviewListActivity : BaseActivity<ActivityReviewListBinding>(ActivityReviewListBinding::inflate), ReviewListActivityView {

    // 가게 데이터 세팅
    private var storeIndex = 0
    private var storeName = ""
    private var reviewOrder = ""
    private var storeStarRating = 0f
    private var storeReviewCount = 0

    private var reviewArrangeDialogNumber = 1
    private lateinit var order : String
    private val dialogReviewArrange = ReviewArrangeDialog()

    private lateinit var rlayoutManager: LinearLayoutManager
    private lateinit var reviewAdapter: ReviewAdapter
    private val reviewData = mutableListOf<ReviewData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storeIndex = intent.getIntExtra("restaurantId", 0) // 가게 인덱스 받아옴
        storeName = intent.getStringExtra("storeName") // 가게 이름
        storeStarRating = intent.getFloatExtra("storeStarRating", 0f) // 가게 별점
        storeReviewCount = intent.getIntExtra("storeReviewCount", 0) // 가게 리뷰 수

        setReviewListView()

        binding.reviewListTextArrange.setOnClickListener { setReviewArrangeDialog() }
    }

    private fun setReviewListView() {
        binding.reviewListTextTitle.text = "${storeName} 리뷰"
        binding.reviewListTextStarRating.text = storeStarRating.toString()
        binding.reviewRatingBar.rating = storeStarRating
        binding.reviewListTextReviewCount.text = storeReviewCount.toString()

        rlayoutManager = LinearLayoutManager(this)
        rlayoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.reviewListRecyclerView.layoutManager = rlayoutManager
        binding.reviewListRecyclerView.isNestedScrollingEnabled = true

        reviewAdapter = ReviewAdapter(this)
        binding.reviewListRecyclerView.adapter = reviewAdapter

        setReviewRecyclerView("")
    }

    private fun setReviewRecyclerView(order: String) {
        showLoadingDialog(this)
        ReviewListService(this).tryGetReviews(storeIndex, order)
    }

    // 리뷰 받아오기
    override fun onGetReviewsSuccess(response: ReviewListResponse) {
        dismissLoadingDialog()
        reviewData.clear()

        if(response.isSuccess) { // 받아오기 성공
            for(i in response.result)
                reviewData.add(ReviewData(i.name, i.rating, i.createAt.substring(0,10), i.review))
        }
        reviewAdapter.reviewData = reviewData
        reviewAdapter.notifyDataSetChanged()
    }

    private fun setReviewArrangeDialog() {
        dialogReviewArrange.setReviewDialog(reviewArrangeDialogNumber)
        dialogReviewArrange.show(supportFragmentManager, "ReviewArrangeDialog")

        dialogReviewArrange.setReviewDialogResult(object : ReviewArrangeDialog.SetReviewResult{
            override fun setFilter(dialogResult: Int) {

                when(dialogResult){
                    1 -> { // 최신순
                        binding.reviewListTextArrange.setText(R.string.review_text_new)
                        reviewArrangeDialogNumber = 1
                        setReviewRecyclerView("recent")
                        // initChooseRecyclerView()
                    }
                    2 -> { // 리뷰도움순
                        binding.reviewListTextArrange.setText(R.string.review_text_help)
                        reviewArrangeDialogNumber = 2
                    }
                    3 -> { // 별점높은순
                        binding.reviewListTextArrange.setText(R.string.review_text_high)
                        reviewArrangeDialogNumber = 3
                        setReviewRecyclerView("rating")
                    }
                    4 -> { // 별점낮은순
                        binding.reviewListTextArrange.setText(R.string.review_text_low)
                        reviewArrangeDialogNumber = 4
                    }
                }
            }
        })
    }

    override fun onGetReviewsFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

}