package com.yunwoon.coupangeatsproject.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.yunwoon.coupangeatsproject.databinding.DialogSetAddressBinding

class SetAddressDialog()  : DialogFragment() {
    private var _binding: DialogSetAddressBinding? = null
    private val binding get() = _binding!!

    private lateinit var setAddressResult : SetAddressResult

    interface SetAddressResult {
        fun finish(dialogResult : Int)
    }

    fun setAddressDialogResult(addressResult : SetAddressResult) {
        this.setAddressResult = addressResult
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSetAddressBinding.inflate(inflater, container, false)
        val view = binding.root

        // layout background transperency
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.dialogTextConfirm.setOnClickListener {
            setAddressResult.finish(1)
            dismiss()
        }

        binding.dialogTextCancel.setOnClickListener {
            setAddressResult.finish(2)
            dismiss()
        }

        return view
    }
}