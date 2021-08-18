package com.yunwoon.coupangeatsproject.src.main.mypage

import com.yunwoon.coupangeatsproject.src.main.mypage.models.MyPageResponse

interface MyPageFragmentView {
    fun onGetMyPageSuccess(response: MyPageResponse)

    fun onGetMyPageFailure(message: String)
}