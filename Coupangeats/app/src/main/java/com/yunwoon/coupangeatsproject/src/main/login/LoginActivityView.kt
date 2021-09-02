package com.yunwoon.coupangeatsproject.src.main.login

import com.yunwoon.coupangeatsproject.src.main.login.models.JoinResponse
import com.yunwoon.coupangeatsproject.src.main.login.models.LogInResponse

interface LoginActivityView {
    fun onPostLogInSuccess(response: LogInResponse)

    fun onPostLogInFailure(message: String)
}