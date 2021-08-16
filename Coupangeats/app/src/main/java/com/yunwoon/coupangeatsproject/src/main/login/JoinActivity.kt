package com.yunwoon.coupangeatsproject.src.main.login

import android.os.Bundle
import android.util.Log
import android.view.View
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityJoinBinding
import com.yunwoon.coupangeatsproject.src.main.login.models.JoinResponse
import com.yunwoon.coupangeatsproject.src.main.login.models.PostJoinRequest
import java.util.regex.Matcher
import java.util.regex.Pattern

class JoinActivity : BaseActivity<ActivityJoinBinding>(ActivityJoinBinding::inflate), JoinActivityView  {

    private lateinit var matcher : Matcher
    private val emailPattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )
    private val passwordPattern = Pattern.compile(
        // 영어 대소문자가 한개이상 들어가 있는가 ?  숫자가 한개이상 들어가 있는가 ?? 특수문자가 한개이상 들어가 있는가 ?
        // 영어부터 숫자 특수문자를 입력 받을것이고,  8개 이상 20개 이하의 텍스트를 받아야 한다.
        "^(?=.*[a-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&])[A-Za-z[0-9]\$@\$!%*#?&]{7,20}\$"
    )

    private var emailCheck = false
    private var passwordCheck1 = false
    private var passwordCheck2 = false
    private var nameCheck = false
    private var phoneCheck = false
    private var termCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 이메일 포커스
        binding.joinEditTextEmail.setOnFocusChangeListener { _, p1 ->
            if(!p1) {
                if(binding.joinEditTextEmail.text.toString() == "") {
                    emailCheck = false
                    binding.joinImageViewEmailCheck.visibility = View.GONE // 체크
                    binding.joinTextWrongEmail.visibility = View.VISIBLE // 경고문
                    binding.joinViewWrongEmail.visibility = View.VISIBLE // 뷰
                }
                else {
                    matcher = emailPattern.matcher(binding.joinEditTextEmail.text.toString())
                    if(!matcher.matches()) {
                        emailCheck = false
                        binding.joinImageViewEmailCheck.visibility = View.GONE // 체크
                        binding.joinTextWrongEmail.visibility = View.VISIBLE // 경고문
                        binding.joinViewWrongEmail.visibility = View.VISIBLE // 뷰
                    } else {
                        emailCheck = true
                        binding.joinImageViewEmailCheck.visibility = View.VISIBLE // 체크
                        binding.joinTextWrongEmail.visibility = View.GONE // 경고문
                        binding.joinViewWrongEmail.visibility = View.GONE // 뷰
                    }
                }
            }
        }

        // 비밀번호 포커스
        binding.joinEditTextPassword.setOnFocusChangeListener { _, p1 ->
            if(!p1) {
                matcher = passwordPattern.matcher(binding.joinEditTextPassword.text.toString())
                if(binding.joinEditTextPassword.text.toString() == "") {
                    passwordCheck1 = false
                    passwordCheck2 = false
                    binding.joinViewWrongPassword.visibility = View.VISIBLE // 뷰
                    binding.joinTextWrongPassword1.visibility = View.VISIBLE // 경고문1
                    binding.joinTextWrongPassword3.visibility = View.VISIBLE //경고문3
                    binding.joinTextCorrectPassword.visibility = View.GONE // 성공문
                    binding.joinTextWrongPassword11.visibility = View.GONE // 성공문1
                    binding.joinTextWrongPassword31.visibility = View.GONE // 성공문3
                    binding.joinImageViewPassword.setImageResource(R.drawable.ic_password_invisible)
                }
                else {
                    if(!matcher.matches()) { // 패턴에 걸리면
                        passwordCheck1 = false
                        binding.joinViewWrongPassword.visibility = View.VISIBLE // 뷰
                        binding.joinTextWrongPassword1.visibility = View.VISIBLE // 경고문1
                        binding.joinTextCorrectPassword.visibility = View.GONE // 성공문
                        binding.joinTextWrongPassword11.visibility = View.GONE // 성공문1
                        binding.joinImageViewPassword.setImageResource(R.drawable.ic_password_invisible)
                    } else {
                        passwordCheck1 = true
                        binding.joinTextWrongPassword1.visibility = View.GONE // 경고문1
                        binding.joinTextWrongPassword11.visibility = View.VISIBLE // 성공문1
                    }

                    if(binding.joinEditTextPassword.text.toString() == binding.joinEditTextEmail.text.toString()) { // 이메일과 동일하면
                        passwordCheck2 = false
                        binding.joinViewWrongPassword.visibility = View.VISIBLE // 뷰
                        binding.joinTextWrongPassword3.visibility = View.VISIBLE //경고문3
                        binding.joinTextCorrectPassword.visibility = View.GONE // 성공문
                        binding.joinTextWrongPassword31.visibility = View.GONE // 성공문3
                        binding.joinImageViewPassword.setImageResource(R.drawable.ic_password_invisible)
                    } else {
                        passwordCheck2 = true
                        binding.joinTextWrongPassword3.visibility = View.GONE //경고문3
                        binding.joinTextWrongPassword31.visibility = View.VISIBLE // 성공문3
                    }

                    if(passwordCheck1 && passwordCheck2) {
                        binding.joinViewWrongPassword.visibility = View.GONE // 뷰
                        binding.joinTextCorrectPassword.visibility = View.VISIBLE // 성공문
                        binding.joinImageViewPassword.setImageResource(R.drawable.ic_join_check)
                    }
                }
            }
        }

        // 이름 포커스
        binding.joinEditTextName.setOnFocusChangeListener { _, p1 ->
            if(!p1) {
                //이름의 작성 여부 확인
                if(binding.joinEditTextName.text.toString() == "") {
                    nameCheck = false
                    binding.joinImageViewNameCheck.visibility = View.GONE // 체크
                    binding.joinViewWrongName.visibility = View.VISIBLE // 뷰
                    binding.joinTextWrongName.visibility = View.VISIBLE // 경고문
                } else {
                    nameCheck = true
                    binding.joinImageViewNameCheck.visibility = View.VISIBLE // 체크
                    binding.joinViewWrongName.visibility = View.GONE // 뷰
                    binding.joinTextWrongName.visibility = View.GONE // 경고문
                }
            }
        }

        // 휴대폰 번호 포커스
        binding.joinEditTextPhone.setOnFocusChangeListener { _, p1 ->
            if(!p1) {
                // 휴대폰 번호 작성 여부 / 자릿수 체크
                if(binding.joinEditTextPhone.text.toString() == "") {
                    phoneCheck = false
                    binding.joinImageViewPhoneCheck.visibility = View.GONE // 체크
                    binding.joinViewWrongPhone.visibility = View.VISIBLE // 뷰
                    binding.joinTextWrongPhone.visibility = View.VISIBLE // 경고문
                }
                else {
                    phoneCheck = true
                    // 휴대폰 인증 버튼과 인증 메소드 구현!!!!!!!
                    binding.joinImageViewPhoneCheck.visibility = View.VISIBLE // 체크
                    binding.joinViewWrongPhone.visibility = View.GONE // 뷰
                    binding.joinTextWrongPhone.visibility = View.GONE // 경고문
                }
            }
        }

        // 모두 동의합니다 클릭
        binding.joinCheckBoxTermAll.setOnClickListener {
            if(termCheck) {
                termCheck = !termCheck
                binding.joinCheckBoxTerm1.isChecked = false
                binding.joinCheckBoxTerm2.isChecked = false
                binding.joinCheckBoxTerm3.isChecked = false
                binding.joinCheckBoxTerm4.isChecked = false
                binding.joinCheckBoxTerm5.isChecked = false
            } else {
                termCheck = !termCheck
                binding.joinCheckBoxTerm1.isChecked = true
                binding.joinCheckBoxTerm2.isChecked = true
                binding.joinCheckBoxTerm3.isChecked = true
                binding.joinCheckBoxTerm4.isChecked = true
                binding.joinCheckBoxTerm5.isChecked = true
            }
        }

        // 가입 버튼 클릭
        binding.joinButton.setOnClickListener {
            if(emailCheck && passwordCheck1 && passwordCheck2 && nameCheck && phoneCheck && termCheck) {
                join()
            }
            else
                showCustomToast("각 항목을 다시 검토해주세요")
        }
    }

    private fun join() {
        val email = binding.joinEditTextEmail.text.toString()
        val password = binding.joinEditTextPassword.text.toString()
        val name = binding.joinEditTextName.text.toString()
        val phone = binding.joinEditTextPhone.text.toString()

        val postRequest = PostJoinRequest(email = email, password = password, name = name, phoneNumber = phone)
        showLoadingDialog(this)
        JoinService(this).tryPostJoin(postRequest)
    }

    override fun onPostJoinSuccess(response: JoinResponse) {
        dismissLoadingDialog()
        showCustomToast(response.result.toString())
        finish()
        showCustomToast("회원가입에 성공했습니다")
    }

    override fun onPostJoinFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
        Log.d("오류", "$message")
    }
}