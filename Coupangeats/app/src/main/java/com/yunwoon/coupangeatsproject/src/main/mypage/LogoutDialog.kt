package com.yunwoon.coupangeatsproject.src.main.mypage

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.databinding.DialogLogoutBinding

class LogoutDialog  : DialogFragment() {
    private var _binding: DialogLogoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogLogoutBinding.inflate(inflater, container, false)
        val view = binding.root

        // layout background transperency
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.logoutTextCancel.setOnClickListener {
            dismiss()
        }

        binding.logoutTextConfirm.setOnClickListener {
            ApplicationClass.sEditor.putString("loginJwtToken", null).apply()
            val intent = Intent().putExtra("code", 1)
            targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)

            dismiss()
            // savedInstanceState?.putInt("code", 1)
        }

        return view
    }
}