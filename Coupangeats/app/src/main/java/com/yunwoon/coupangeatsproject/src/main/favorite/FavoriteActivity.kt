package com.yunwoon.coupangeatsproject.src.main.favorite

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityFavoriteBinding
import com.yunwoon.coupangeatsproject.util.favoriteRecycler.FavoriteAdapter
import com.yunwoon.coupangeatsproject.util.favoriteRecycler.FavoriteData

class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>(ActivityFavoriteBinding::inflate) {

    private lateinit var flayoutManager: LinearLayoutManager

    private lateinit var favoriteAdapter: FavoriteAdapter
    private val favoriteData = mutableListOf<FavoriteData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolBar(binding.favoriteToolbar)

        setFavoriteView()

        binding.favoriteImageButtonBack.setOnClickListener  { finish() }
        binding.favoriteButtonDefault.setOnClickListener { finish() }
    }

    // 찜 가게 유무에 따라 뷰 설정
    private fun setFavoriteView() {
        if(true) {
            binding.anyFavoriteConstraintLayout.visibility = View.GONE
            binding.favoriteConstraintLayout.visibility = View.VISIBLE

            setFavoriteRecyclerView()
        } else {
            binding.anyFavoriteConstraintLayout.visibility = View.VISIBLE
            binding.favoriteConstraintLayout.visibility = View.GONE
        }
    }

    // 찜 가게 리사이클러뷰 세팅
    private fun setFavoriteRecyclerView() {
        flayoutManager = LinearLayoutManager(this)
        binding.favoriteRecyclerView.layoutManager = flayoutManager

        favoriteAdapter = FavoriteAdapter(this)

        binding.favoriteRecyclerView.isNestedScrollingEnabled = true
        binding.favoriteRecyclerView.adapter = favoriteAdapter

        val resources : Resources = this.resources
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_favorite_store)

        favoriteData.apply {
            add(FavoriteData(bitmap, "굽네치킨 상암점", "4.8", "(432)", "4.1km", "10-20분","배달비 2,000원"))
            add(FavoriteData(bitmap, "굽네치킨 상아점", "4.8", "(432)", "4.1km", "10-20분","무료배달"))
            add(FavoriteData(bitmap, "굽네치킨 상오점", "4.8", "(432)", "4.1km", "10-20분","배달비 2,000원"))

            favoriteAdapter.favoriteData = favoriteData
        }
   }

    // 옵션 메뉴 생성 - 수정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_modify, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite_modify -> {
                showCustomToast("수정하시죠")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}