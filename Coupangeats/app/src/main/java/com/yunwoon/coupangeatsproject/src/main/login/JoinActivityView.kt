package com.yunwoon.coupangeatsproject.src.main.login

import com.yunwoon.coupangeatsproject.src.main.login.models.JoinResponse
import com.yunwoon.coupangeatsproject.src.main.login.models.UsersResponse

interface JoinActivityView {

    fun onGetUsersSuccess(response: UsersResponse)

    fun onGetUsersFailure(message: String)

    fun onPostJoinSuccess(response: JoinResponse)

    fun onPostJoinFailure(message: String)
}