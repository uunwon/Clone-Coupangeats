package com.yunwoon.coupangeatsproject.util.orderRecycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R

class OrderMenuAdapter(private var context: Context) : RecyclerView.Adapter<OrderMenuAdapter.ViewHolder>()  {
    var orderMenuData = mutableListOf<OrderMenuData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ordered_menu, parent, false)
        Log.d("OrderMenuAdapter", "onCreateViewHolder 동작")
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = orderMenuData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderMenuData[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var orderMenuIndex : TextView= itemView.findViewById(R.id.ordered_menu_index)
        private var orderMenuTitle: TextView = itemView.findViewById(R.id.ordered_menu_title)

        fun bind(item: OrderMenuData) {
            orderMenuIndex.text = item.orderMenuIndex.toString()
            orderMenuTitle.text = item.orderMenuTitle
        }
    }
}