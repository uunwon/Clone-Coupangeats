package com.yunwoon.coupangeatsproject.src.reviewlist.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yunwoon.coupangeatsproject.databinding.DialogReviewArrangeBinding

class ReviewArrangeDialog : BottomSheetDialogFragment() {

    private var _binding: DialogReviewArrangeBinding? = null
    private val binding get() = _binding!!

    private lateinit var setReviewResult : SetReviewResult
    private var filterNumber: Int = 0

    interface SetReviewResult {
        fun setFilter (dialogResult: Int)
    }

    fun setReviewDialog (filterNumber : Int) {
        this.filterNumber = filterNumber
    }

    fun setReviewDialogResult (reviewResult: SetReviewResult) {
        this.setReviewResult = reviewResult
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("ReviewArrangeDal", "$filterNumber")

        when(filterNumber) {
            1 -> { // 최신순
                binding.reviewImageNew.visibility = View.VISIBLE
                binding.reviewImageHelp.visibility = View.GONE
                binding.reviewImageHigh.visibility = View.GONE
                binding.reviewImageLow.visibility = View.GONE
            }
            2 -> { // 리뷰도움순
                binding.reviewImageNew.visibility = View.GONE
                binding.reviewImageHelp.visibility = View.VISIBLE
                binding.reviewImageHigh.visibility = View.GONE
                binding.reviewImageLow.visibility = View.GONE
            }
            3 -> { // 별점높은순
                binding.reviewImageNew.visibility = View.GONE
                binding.reviewImageHelp.visibility = View.GONE
                binding.reviewImageHigh.visibility = View.VISIBLE
                binding.reviewImageLow.visibility = View.GONE
            }
             4 -> { // 별점낮은순
                 binding.reviewImageNew.visibility = View.GONE
                 binding.reviewImageHelp.visibility = View.GONE
                 binding.reviewImageHigh.visibility = View.GONE
                 binding.reviewImageLow.visibility = View.VISIBLE
             }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogReviewArrangeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.reviewTextArrange.setOnClickListener { dismiss() }

        // 최신순
        binding.reviewLinearNew.setOnClickListener {
            setReviewResult.setFilter(1)
            dismiss()
        }

        // 리뷰 도움 순
        binding.reviewLinearHelp.setOnClickListener {
            setReviewResult.setFilter(2)
            dismiss()
        }

        // 별점 높은 순
        binding.reviewLinearHigh.setOnClickListener {
            setReviewResult.setFilter(3)
            dismiss()
        }

        // 별점 낮은 순
        binding.reviewLinearLow.setOnClickListener {
            setReviewResult.setFilter(4)
            dismiss()
        }

        return view
    }
}