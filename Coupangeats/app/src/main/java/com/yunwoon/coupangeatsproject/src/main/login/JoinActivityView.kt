package com.yunwoon.coupangeatsproject.src.main.login

import com.yunwoon.coupangeatsproject.src.main.login.models.JoinResponse

interface JoinActivityView {

    fun onPostJoinSuccess(response: JoinResponse)

    fun onPostJoinFailure(message: String)
}