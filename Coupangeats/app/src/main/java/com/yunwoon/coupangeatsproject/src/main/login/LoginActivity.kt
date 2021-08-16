package com.yunwoon.coupangeatsproject.src.main.login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityLoginBinding
import com.yunwoon.coupangeatsproject.src.main.login.models.LogInResponse
import com.yunwoon.coupangeatsproject.src.main.login.models.PostLogInRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginActivityView {

    private var isChecked = false
    private lateinit var matcher : Matcher

    private val EMAIL_ADDRESS = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val drawableBlue = resources.getDrawable(R.drawable.button_login_blue)
        val drawableWhite = resources.getDrawable(R.drawable.button_login_white)

        // 로그인 창 닫기
        binding.loginImageButtonClose.setOnClickListener { finish() }

        // 회원가입 창 띄우기
        binding.loginTextJoin.setOnClickListener {
            finish()
            this.startActivity(Intent(this, JoinActivity::class.java))
        }

        // 이메일 텍스트 포커스 (람다 표현식)
        binding.loginEditTextEmail.setOnFocusChangeListener { _, p1 ->
            if (p1) {
                binding.loginLinearLayoutEmail.setBackgroundDrawable(drawableBlue)
                binding.loginImageViewEmail.visibility = View.VISIBLE
            } else {
                binding.loginLinearLayoutEmail.setBackgroundDrawable(drawableWhite)
                binding.loginImageViewEmail.visibility = View.INVISIBLE
            }
        }

        // 이메일 텍스트 클리어
        binding.loginImageViewEmail.setOnClickListener {
            binding.loginEditTextEmail.text = null
        }

        // 비밀번호 텍스트 포커스
        binding.loginEditTextPassword.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if(p1) {
                    binding.loginLinearLayoutPassword.setBackgroundDrawable(drawableBlue)
                } else {
                    binding.loginLinearLayoutPassword.setBackgroundDrawable(drawableWhite)
                }
            }
        })

        // 비밀번호 텍스트 보이기
        binding.loginImageViewPassword.setOnClickListener {
            if(isChecked) {
                binding.loginImageViewPassword.setImageResource(R.drawable.ic_password_invisible)
                binding.loginEditTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                binding.loginImageViewPassword.setImageResource(R.drawable.ic_password_visible)
                binding.loginEditTextPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }

            isChecked = !isChecked
        }

        // 로그인 버튼 클릭
        binding.loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        matcher = EMAIL_ADDRESS.matcher(binding.loginEditTextEmail.text.toString())

        if(binding.loginEditTextEmail.text.toString() == "" || binding.loginEditTextPassword.text.toString() == "") {
            showCustomToast("이메일 혹은 비밀번호를 입력해주세요")
        }
        // 이메일 형식 체크 -> 실패
        else if(!matcher.matches()) {
            val dialogBottomLoginError = BottomLoginErrorDialog()

            GlobalScope.launch {
                delay(2000L)
                dialogBottomLoginError.dismiss()
            }
            dialogBottomLoginError.show(supportFragmentManager, "BottomLoginErrorDialog")
        }
        else {
            val email = binding.loginEditTextEmail.text.toString()
            val password = binding.loginEditTextPassword.text.toString()
            val postRequest = PostLogInRequest(email = "ckrt4815@gmail.com", password = "woals4815")
            showLoadingDialog(this)
            LoginService(this).tryPostLogIn(postRequest)
        }
    }

    override fun onPostLogInSuccess(response: LogInResponse) {
        dismissLoadingDialog()
        ApplicationClass.sEditor.putString("jwt", response.result.jwt).apply()
        response.result.jwt.let { showCustomToast(it) } // 요청 성공 메시지 띄우기
        finish()
    }

    override fun onPostLogInFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}