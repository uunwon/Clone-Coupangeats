package com.yunwoon.coupangeatsproject.src.main.home.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yunwoon.coupangeatsproject.databinding.DialogChipArrangeBinding

class ChipArrangeDialog : BottomSheetDialogFragment() {

    private var _binding: DialogChipArrangeBinding? = null
    private val binding get() = _binding!!

    private lateinit var setChipResult : SetChipResult
    private var filterNumber: Int = 0

    interface SetChipResult {
        fun setFilter (dialogResult: Int)
    }

    fun setChipDialog (filterNumber : Int) {
        this.filterNumber = filterNumber
    }

    fun setChipDialogResult (chipResult: SetChipResult) {
        this.setChipResult = chipResult
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("ChipArrangeDal", "$filterNumber")

        when(filterNumber) {
            1 -> { // 추천순
                binding.chipImageRecommend.visibility = View.VISIBLE
                binding.chipImageOrder.visibility = View.GONE
                binding.chipImageLocation.visibility = View.GONE
                binding.chipImageStarRating.visibility = View.GONE
                binding.chipImageNew.visibility = View.GONE
            }
            2 -> { // 주문많은순
                binding.chipImageRecommend.visibility = View.GONE
                binding.chipImageOrder.visibility = View.VISIBLE
                binding.chipImageLocation.visibility = View.GONE
                binding.chipImageStarRating.visibility = View.GONE
                binding.chipImageNew.visibility = View.GONE
            }
            3 -> { // 가까운순
                binding.chipImageRecommend.visibility = View.GONE
                binding.chipImageOrder.visibility = View.GONE
                binding.chipImageLocation.visibility = View.VISIBLE
                binding.chipImageStarRating.visibility = View.GONE
                binding.chipImageNew.visibility = View.GONE
            }
             4 -> { // 별점높은순
                 binding.chipImageRecommend.visibility = View.GONE
                 binding.chipImageOrder.visibility = View.GONE
                 binding.chipImageLocation.visibility = View.GONE
                 binding.chipImageStarRating.visibility = View.VISIBLE
                 binding.chipImageNew.visibility = View.GONE
             }
            5 -> { // 신규매장순
                binding.chipImageRecommend.visibility = View.GONE
                binding.chipImageOrder.visibility = View.GONE
                binding.chipImageLocation.visibility = View.GONE
                binding.chipImageStarRating.visibility = View.GONE
                binding.chipImageNew.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogChipArrangeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.chipTextArrange.setOnClickListener { dismiss() }

        // 추천순
        binding.chipLinearRecommend.setOnClickListener {
            setChipResult.setFilter(1)
            dismiss()
        }

        // 주문많은순
        binding.chipLinearOrder.setOnClickListener {
            setChipResult.setFilter(2)
            dismiss()
        }

        // 가까운순
        binding.chipLinearLocation.setOnClickListener {
            setChipResult.setFilter(3)
            dismiss()
        }

        // 별점높은순
        binding.chipLinearStarRating.setOnClickListener {
            setChipResult.setFilter(4)
            dismiss()
        }

        // 신규매장순
        binding.chipLinearNew.setOnClickListener {
            setChipResult.setFilter(5)
            dismiss()
        }

        return view
    }
}