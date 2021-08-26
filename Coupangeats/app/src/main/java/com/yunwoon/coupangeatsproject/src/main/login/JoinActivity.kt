package com.yunwoon.coupangeatsproject.src.main.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityJoinBinding
import com.yunwoon.coupangeatsproject.src.main.login.models.JoinResponse
import com.yunwoon.coupangeatsproject.src.main.login.models.PostJoinRequest
import com.yunwoon.coupangeatsproject.src.main.login.models.UsersResponse
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
    private var passwordCheck3 = false
    private var nameCheck = false
    private var phoneCheck = false
    private var termCheck = false

    private var emailCount = 0
    private var passwordCount = 0
    private var nameCount = 0
    private var phoneCount = 0

    private var phoneNumber : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 이메일 포커스
        binding.joinEditTextEmail.setOnFocusChangeListener { _, p1 ->
            if(p1) {
                if(emailCount == 0)
                    binding.joinViewFocusEmail.visibility = View.VISIBLE
            }
            else {
                emailCount++

                if(binding.joinEditTextEmail.text.toString() == "") {
                    emailCheck = false
                    binding.joinViewFocusEmail.visibility = View.GONE // 파란 뷰
                    binding.joinImageViewEmailCheck.visibility = View.GONE // 체크
                    binding.joinTextWrongEmail.visibility = View.VISIBLE // 경고문
                    binding.joinViewWrongEmail.visibility = View.VISIBLE // 뷰
                }
                else {
                    matcher = emailPattern.matcher(binding.joinEditTextEmail.text.toString())
                    if(!matcher.matches()) {
                        emailCheck = false
                        binding.joinViewFocusEmail.visibility = View.GONE // 파란 뷰
                        binding.joinImageViewEmailCheck.visibility = View.GONE // 체크
                        binding.joinTextWrongEmail.visibility = View.VISIBLE // 경고문
                        binding.joinViewWrongEmail.visibility = View.VISIBLE // 뷰
                    } else {
                        emailCheck = true
                        binding.joinViewFocusEmail.visibility = View.GONE // 파란 뷰
                        binding.joinImageViewEmailCheck.visibility = View.VISIBLE // 체크
                        binding.joinTextWrongEmail.visibility = View.GONE // 경고문
                        binding.joinViewWrongEmail.visibility = View.GONE // 뷰
                    }
                }
            }
        }

        binding.joinEditTextPassword.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                matcher = passwordPattern.matcher(binding.joinEditTextPassword.text.toString())

                if(binding.joinEditTextPassword.text.toString() == "") {
                    passwordCheck1 = false
                    passwordCheck3 = false

                    binding.joinViewFocusPassword.visibility = View.GONE
                    binding.joinTextWrongPassword10.visibility = View.GONE
                    binding.joinTextWrongPassword30.visibility = View.GONE

                    binding.joinViewWrongPassword.visibility = View.VISIBLE // 뷰
                    binding.joinTextWrongPassword1.visibility = View.VISIBLE // 경고문1
                    binding.joinTextWrongPassword3.visibility = View.VISIBLE //경고문3

                    binding.joinTextCorrectPassword.visibility = View.GONE // 성공문
                    binding.joinTextWrongPassword11.visibility = View.GONE // 성공문1
                    binding.joinTextWrongPassword31.visibility = View.GONE // 성공문3

                    binding.joinImageViewPassword.setImageResource(R.drawable.ic_password_eye)
                }
                else {
                    if(!matcher.matches()) { // 패턴에 걸리면
                        passwordCheck1 = false

                        binding.joinViewFocusPassword.visibility = View.GONE
                        binding.joinTextWrongPassword10.visibility = View.GONE
                        binding.joinTextWrongPassword30.visibility = View.GONE

                        binding.joinViewWrongPassword.visibility = View.VISIBLE // 뷰
                        binding.joinTextWrongPassword1.visibility = View.VISIBLE // 경고문1
                        binding.joinTextCorrectPassword.visibility = View.GONE // 성공문
                        binding.joinTextWrongPassword11.visibility = View.GONE // 성공문1
                        binding.joinImageViewPassword.setImageResource(R.drawable.ic_password_eye)
                    } else {
                        passwordCheck1 = true

                        binding.joinViewFocusPassword.visibility = View.GONE
                        binding.joinTextWrongPassword10.visibility = View.GONE
                        binding.joinTextWrongPassword30.visibility = View.GONE

                        binding.joinTextWrongPassword1.visibility = View.GONE // 경고문1
                        binding.joinTextWrongPassword11.visibility = View.VISIBLE // 성공문1
                    }

                    if(binding.joinEditTextPassword.text.toString() == binding.joinEditTextEmail.text.toString()) { // 이메일과 동일하면
                        passwordCheck3 = false

                        binding.joinViewFocusPassword.visibility = View.GONE
                        binding.joinTextWrongPassword10.visibility = View.GONE
                        binding.joinTextWrongPassword30.visibility = View.GONE

                        binding.joinViewWrongPassword.visibility = View.VISIBLE // 뷰
                        binding.joinTextWrongPassword3.visibility = View.VISIBLE //경고문3
                        binding.joinTextCorrectPassword.visibility = View.GONE // 성공문
                        binding.joinTextWrongPassword31.visibility = View.GONE // 성공문3
                        binding.joinImageViewPassword.setImageResource(R.drawable.ic_password_eye)
                    } else {
                        passwordCheck3 = true

                        binding.joinViewFocusPassword.visibility = View.GONE
                        binding.joinTextWrongPassword10.visibility = View.GONE
                        binding.joinTextWrongPassword30.visibility = View.GONE

                        binding.joinTextWrongPassword3.visibility = View.GONE //경고문3
                        binding.joinTextWrongPassword31.visibility = View.VISIBLE // 성공문3
                    }

                    if(passwordCheck1 && passwordCheck3) {
                        binding.joinViewFocusPassword.visibility = View.GONE
                        binding.joinTextWrongPassword10.visibility = View.GONE
                        binding.joinTextWrongPassword30.visibility = View.GONE

                        binding.joinTextWrongPassword11.visibility = View.GONE // 성공문1
                        binding.joinTextWrongPassword31.visibility = View.GONE // 성공문3

                        binding.joinViewWrongPassword.visibility = View.GONE // 뷰
                        binding.joinTextCorrectPassword.visibility = View.VISIBLE // 성공문
                        binding.joinImageViewPassword.setImageResource(R.drawable.ic_join_check)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {  }

        })
        // 비밀번호 포커스
        binding.joinEditTextPassword.setOnFocusChangeListener { _, p1 ->
            if(p1) {
                if(passwordCount == 0) {
                    binding.joinViewFocusPassword.visibility = View.VISIBLE
                    binding.joinTextWrongPassword10.visibility = View.VISIBLE
                    binding.joinTextWrongPassword30.visibility = View.VISIBLE
                }
            }
            else {
                passwordCount++

                if(binding.joinEditTextPassword.text.toString() == "") {
                    passwordCheck1 = false
                    passwordCheck3 = false

                    binding.joinViewFocusPassword.visibility = View.GONE
                    binding.joinTextWrongPassword10.visibility = View.GONE
                    binding.joinTextWrongPassword30.visibility = View.GONE

                    binding.joinViewWrongPassword.visibility = View.VISIBLE // 뷰
                    binding.joinTextWrongPassword1.visibility = View.VISIBLE // 경고문1
                    binding.joinTextWrongPassword3.visibility = View.VISIBLE //경고문3

                    binding.joinTextCorrectPassword.visibility = View.GONE // 성공문
                    binding.joinTextWrongPassword11.visibility = View.GONE // 성공문1
                    binding.joinTextWrongPassword31.visibility = View.GONE // 성공문3

                    binding.joinImageViewPassword.setImageResource(R.drawable.ic_password_invisible)
                }
            }
        }

        // 이름 포커스
        binding.joinEditTextName.setOnFocusChangeListener { _, p1 ->
            if(p1) {
                if(nameCount == 0)
                    binding.joinViewFocusName.visibility = View.VISIBLE
            }
            else {
                nameCount++

                //이름의 작성 여부 확인
                if(binding.joinEditTextName.text.toString() == "") {
                    nameCheck = false
                    binding.joinViewFocusName.visibility = View.GONE // 파란 뷰
                    binding.joinImageViewNameCheck.visibility = View.GONE // 체크
                    binding.joinViewWrongName.visibility = View.VISIBLE // 뷰
                    binding.joinTextWrongName.visibility = View.VISIBLE // 경고문
                } else {
                    nameCheck = true
                    binding.joinViewFocusName.visibility = View.GONE // 파란 뷰
                    binding.joinImageViewNameCheck.visibility = View.VISIBLE // 체크
                    binding.joinViewWrongName.visibility = View.GONE // 뷰
                    binding.joinTextWrongName.visibility = View.GONE // 경고문
                }
            }
        }

        // 휴대폰 번호 포커스
        binding.joinEditTextPhone.setOnFocusChangeListener { _, p1 ->
            if(p1) {
                if(phoneCount == 0)
                    binding.joinViewFocusPhone.visibility = View.VISIBLE
            }
            if(!p1) {
                phoneCount++

                // 휴대폰 번호 작성 여부 / 자릿수 체크
                if(binding.joinEditTextPhone.text.toString() == "" ||
                        binding.joinEditTextPhone.text.length < 11) {
                    phoneCheck = false
                    binding.joinViewFocusPhone.visibility = View.GONE // 파란 뷰
                    binding.joinImageViewPhoneCheck.visibility = View.GONE // 체크
                    binding.joinTextSamePhone.visibility = View.GONE // 같은 번호
                    binding.joinViewWrongPhone.visibility = View.VISIBLE // 뷰
                    binding.joinTextWrongPhone.visibility = View.VISIBLE // 경고문
                }
                else {
                    // 휴대폰 자릿수 완벽하면
                    // 같은 휴대폰 번호인지 확인
                   phoneNumber = binding.joinEditTextPhone.text.toString()
                    checkSamePhone()
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
            if(emailCheck && passwordCheck1 && passwordCheck3 && nameCheck && phoneCheck && termCheck) {
                join()
            }
            else
                showCustomToast("각 항목을 다시 검토해주세요")
        }
    }

    // 같은 이메일 체크
    private fun checkSamePhone() {
        showLoadingDialog(this)
        JoinService(this).tryGetUsers()
    }

    override fun onGetUsersSuccess(response: UsersResponse) {
        var phone = 0
        dismissLoadingDialog()

        for (p in response.result) {
            if(phoneNumber == p.phoneNumber) {

                phone++
                phoneCheck = false
                binding.joinViewFocusPhone.visibility = View.GONE // 파란 뷰
                binding.joinImageViewPhoneCheck.visibility = View.GONE // 체크
                binding.joinTextSamePhone.visibility = View.VISIBLE // 같은 번호
                binding.joinViewWrongPhone.visibility = View.VISIBLE // 뷰
                binding.joinTextWrongPhone.visibility = View.GONE // 경고문

                binding.joinTextSamePhone.text = "${p.email} 아이디(이메일)로 가입된 휴대폰 번호입니다."
            }
        }

        if(phone == 0) {
            Log.d("JoinActivity", "phone number 같은 거 없습니다, 입력 허용!")
            phoneCheck = true
            binding.joinViewFocusPhone.visibility = View.VISIBLE // 파란 뷰
            binding.joinImageViewPhoneCheck.visibility = View.VISIBLE // 체크
            binding.joinTextSamePhone.visibility = View.GONE // 같은 번호
            binding.joinViewWrongPhone.visibility = View.GONE // 뷰
            binding.joinTextWrongPhone.visibility = View.GONE // 경고문
        }
    }

    override fun onGetUsersFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 회원가입
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
        if(response.isSuccess) {
            showCustomToast(response.result.toString())
            finish()
            showCustomToast("회원가입에 성공했습니다")
        } else {
            showCustomToast("회원가입에 실패했습니다")
        }
    }

    override fun onPostJoinFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
        Log.d("오류", "$message")
    }
}