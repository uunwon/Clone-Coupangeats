package com.yunwoon.coupangeatsproject.util.categoryRecycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.src.main.home.HomeFragment

class CategoryAdapter(private val context: Context, private var homeFragment: HomeFragment) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    var categoryData = mutableListOf<CategoryData>()

    fun CategoryAdapter(context: Context, homeFragment: HomeFragment) {
        this.homeFragment = homeFragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = categoryData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryData[position])

        holder.itemView.setOnClickListener {
            homeFragment.moveToCategoryDetailActivity(position)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val reviewImage: ImageView = itemView.findViewById(R.id.category_circle_image)
        private val reviewContent: TextView = itemView.findViewById(R.id.category_text_title)

        fun bind(item: CategoryData) {
            reviewImage.setImageBitmap(item.categoryCircleImage)
            reviewContent.text = item.categoryTextTitle
        }
    }


}