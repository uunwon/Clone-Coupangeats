package com.yunwoon.coupangeatsproject.src.review

import android.os.Bundle
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityReviewBinding
import com.yunwoon.coupangeatsproject.src.review.models.PostReviewRequest
import com.yunwoon.coupangeatsproject.src.review.models.ReviewResponse

class ReviewActivity : BaseActivity<ActivityReviewBinding>(ActivityReviewBinding::inflate), ReviewActivityView {
    private var loginJwtToken = ApplicationClass.sSharedPreferences.getString("loginJwtToken", null)
    private var restaurantId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        restaurantId = intent.getIntExtra("restaurantId", 0) // 식당 인덱스 받아옴

        binding.reviewButton.setOnClickListener {
            if(binding.reviewRatingBar.numStars == 0) {
                showCustomToast("별점을 선택해주세요")
            } else {
                if(binding.reviewEditText.text.isEmpty()) {
                    showCustomToast("리뷰를 작성해주세요")
                } else {
                    if(loginJwtToken != null && restaurantId != 0) {
                        val postReviewRequest = PostReviewRequest(review = binding.reviewEditText.text.toString(),
                            rating = binding.reviewRatingBar.rating, restaurantId = restaurantId)
                        showLoadingDialog(this)
                        ReviewService(this).tryPostReview(loginJwtToken!!, postReviewRequest)
                    } else {
                        showCustomToast("식당 id 를 받지 못했습니다")
                    }
                }
            }
        }
    }

    override fun onPostReviewSuccess(response: ReviewResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            setResult(RESULT_OK)
            showCustomToast("리뷰를 생성했습니다")
            finish()
        }
    }

    override fun onPostReviewFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}