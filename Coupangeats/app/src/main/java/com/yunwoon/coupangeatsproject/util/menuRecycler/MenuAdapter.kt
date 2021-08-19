package com.yunwoon.coupangeatsproject.util.menuRecycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R

class MenuAdapter(private val context: Context) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    var menuDataArrayList = mutableListOf<MenuData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = menuDataArrayList.size

    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
        holder.bind(menuDataArrayList[position])

        holder.itemView.setOnClickListener {
            Log.d("StoreActivity", "MenuData $position 선택됨")
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val menuTitle: TextView = itemView.findViewById(R.id.menu_title)
        private val menuPrice: TextView = itemView.findViewById(R.id.menu_price)
        private val menuDetail: TextView = itemView.findViewById(R.id.menu_detail)
        private val menuImage: ImageView = itemView.findViewById(R.id.menu_image)

        fun bind(item: MenuData) {
            menuTitle.text = item.menuTitle
            menuPrice.text = item.menuPrice
            menuDetail.text = item.menuDetail
            menuImage.setImageBitmap(item.menuImage)
        }
    }
}