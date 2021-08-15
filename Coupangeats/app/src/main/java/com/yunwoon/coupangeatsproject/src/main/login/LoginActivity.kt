package com.yunwoon.coupangeatsproject.src.main.login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate)  {

    private var isChecked = false

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

        // 이메일 텍스트 포커스
        binding.loginEditTextEmail.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if(p1) {
                    binding.loginLinearLayoutEmail.setBackgroundDrawable(drawableBlue)
                    binding.loginImageViewEmail.visibility = View.VISIBLE
                } else {
                    binding.loginLinearLayoutEmail.setBackgroundDrawable(drawableWhite)
                    binding.loginImageViewEmail.visibility = View.INVISIBLE
                }
            }
        })

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
    }
}