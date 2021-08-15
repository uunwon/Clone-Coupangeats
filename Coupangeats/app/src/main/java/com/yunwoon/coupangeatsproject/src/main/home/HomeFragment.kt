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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home){

    private val imageHomeAd : Array<Int> = arrayOf(R.drawable.image_home_ad_1, R.drawable.image_home_ad_2)
    private lateinit var scope : Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setToolBar(binding.homeToolbar)
        startViewFlipper()
    }

    override fun onDestroyView() {
        scope.cancel()
        super.onDestroyView()
    }

    // 검색 옵션 메뉴 생성
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

    // 광고 이미지 view flipper 구현
    private fun startViewFlipper() {
        runBlocking {
            scope = launch {
                for(image in imageHomeAd) {
                    setViewFlipper(image)
                }
            }
        }
    }

    private fun setViewFlipper(image : Int) {
        val imageView = ImageView(context)
        imageView.setBackgroundResource(image)

        binding.homeViewFlipper.addView(imageView)
        binding.homeViewFlipper.flipInterval = 3000
        binding.homeViewFlipper.isAutoStart = true
        binding.homeViewFlipper.startFlipping()
    }
}