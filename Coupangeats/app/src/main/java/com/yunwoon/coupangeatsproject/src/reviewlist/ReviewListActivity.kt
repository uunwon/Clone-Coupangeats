package com.yunwoon.coupangeatsproject.src.reviewlist

import android.os.Bundle
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityReviewBinding
import com.yunwoon.coupangeatsproject.src.reviewlist.models.ReviewListResponse

class ReviewListActivity : BaseActivity<ActivityReviewBinding>(ActivityReviewBinding::inflate), ReviewListActivityView {

    private var storeIndex = 0
    private var reviewOrder = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storeIndex = intent.getIntExtra("restaurantId", 0) // 가게 인덱스 받아옴
        setReviewList()
    }

    private fun setReviewList() {
        showLoadingDialog(this)
        ReviewListService(this).tryGetReviews(storeIndex, reviewOrder)
    }

    // 리뷰 받아오기
    override fun onGetReviewsSuccess(response: ReviewListResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) { // 받아오기 성공

        }
    }

    override fun onGetReviewsFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

}