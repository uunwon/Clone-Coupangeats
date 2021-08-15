package com.yunwoon.coupangeatsproject.src.main.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yunwoon.coupangeatsproject.databinding.DialogBottomLoginBinding

class BottomLoginDialog : BottomSheetDialogFragment() {

    private var _binding: DialogBottomLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBottomLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 쿠팡 앱으로 이동 -> 구현 X
        binding.loginButtonCoupangApp.setOnClickListener {
            Log.d("BottomLoginDialog", "coupang app 연동 아님")
            dismiss()
        }

        // 로그인 창 이동
        binding.loginButtonCoupangId.setOnClickListener {
            dismiss()
            this.startActivity(Intent(context, LoginActivity::class.java))
        }

        // 회원가입 창 이동
        binding.loginTextJoin.setOnClickListener {
            dismiss()
            this.startActivity(Intent(context, JoinActivity::class.java))
        }
    }
}