package com.yunwoon.coupangeatsproject.util.menuDetailRecycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass

class MenuCheckAdapter(private var context: Context) : RecyclerView.Adapter<MenuCheckAdapter.ViewHolder>()  {
    var menuCheckData = mutableListOf<MenuCheckData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu_detail_check, parent, false)
        Log.d("MenuCheckAdapter", "onCreateViewHolder 동작")
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = menuCheckData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menuCheckData[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var menuDetailCheckBox: CheckBox = itemView.findViewById(R.id.menu_detail_check_box)
        private val menuDetailCheckText: TextView = itemView.findViewById(R.id.menu_detail_check_text)

        fun bind(item: MenuCheckData) {
            menuDetailCheckBox.isChecked = item.menuDetailCheckBox
            menuDetailCheckText.text = item.menuDetailCheckText

            menuDetailCheckBox.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val position = adapterPosition

                    Log.d("MenuRadioAdapter", "체크박스 ${menuCheckData[position].menuDetailCheckId} 클릭함")
                    ApplicationClass.sEditor.putInt("checkOptionId", menuCheckData[position].menuDetailCheckId).apply()
                }
            })
        }
    }
}