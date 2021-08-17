package com.yunwoon.coupangeatsproject.src.main.login

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.databinding.DialogBottomLoginErrorBinding

class BottomLoginErrorDialog(private var errorCode: Int) : BottomSheetDialogFragment() {
    private var _binding: DialogBottomLoginErrorBinding? = null
    private val binding get() = _binding!!

    fun BottomLoginErrorDialog(errorCode: Int) {
        this.errorCode = errorCode
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBottomLoginErrorBinding.inflate(inflater, container, false)
        val view = binding.root

        when (errorCode) {
            0 -> binding.loginTextError.setText(R.string.login_text_error)
            1 -> binding.loginTextError.setText(R.string.login_text_error1)
            2 -> binding.loginTextError.setText(R.string.login_text_error2)
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.Transparency_DialogTheme)
    }
}