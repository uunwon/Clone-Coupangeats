package com.yunwoon.coupangeatsproject.src.main.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseFragment
import com.yunwoon.coupangeatsproject.databinding.FragmentHomeBinding

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home){

    private val imageHomeAd : Array<Int> = arrayOf(R.drawable.image_home_ad_1, R.drawable.image_home_ad_2)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        moveViewFlipper()

        setToolBar(binding.homeToolbar)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        moveViewFlipper()
    }

    private fun moveViewFlipper() {
        for(image in imageHomeAd) {
            setViewFlipper(image)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_option, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_main_option_search -> {
                showCustomToast("검색 누름")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setViewFlipper(image : Int) {
        val imageView = ImageView(context)
        imageView.setBackgroundResource(image)

        binding.homeViewFlipper.addView(imageView)
        binding.homeViewFlipper.flipInterval = 3000
        binding.homeViewFlipper.isAutoStart = true
    }

}