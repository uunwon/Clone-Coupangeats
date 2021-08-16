package com.yunwoon.coupangeatsproject.src.main.login

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.yunwoon.coupangeatsproject.databinding.DialogLoginErrorBinding

class LoginErrorDialog : DialogFragment() {
    private var _binding: DialogLoginErrorBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogLoginErrorBinding.inflate(inflater, container, false)
        val view = binding.root

        // layout background transperency
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.loginTextConfirm.setOnClickListener {
            dismiss()
        }

        return view
    }
}