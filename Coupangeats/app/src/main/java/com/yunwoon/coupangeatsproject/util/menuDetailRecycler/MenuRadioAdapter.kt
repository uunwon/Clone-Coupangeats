package com.yunwoon.coupangeatsproject.util.menuDetailRecycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R

class MenuRadioAdapter(private var context: Context) : RecyclerView.Adapter<MenuRadioAdapter.ViewHolder>() {
    var menuRadioData = mutableListOf<MenuRadioData>()
    private var checkedRadioButton: CompoundButton? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu_detail_radio, parent, false)
        Log.d("MenuRadioAdapter", "onCreateViewHolder 동작")
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = menuRadioData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menuRadioData[position])

        holder.itemView.findViewById<RadioButton>(R.id.menu_detail_radio_button).setOnCheckedChangeListener(checkedChangeListener)
        if(holder.itemView.findViewById<RadioButton>(R.id.menu_detail_radio_button).isChecked)
            checkedRadioButton = holder.itemView.findViewById<RadioButton>(R.id.menu_detail_radio_button)

    }

    private val checkedChangeListener = CompoundButton.OnCheckedChangeListener { compoundButton, b ->
        checkedRadioButton?.apply { setChecked(false) }
        checkedRadioButton = compoundButton.apply { setChecked(isChecked) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var menuDetailRadioButton: RadioButton = itemView.findViewById(R.id.menu_detail_radio_button)
        private val menuDetailRadioText: TextView = itemView.findViewById(R.id.menu_detail_radio_text)

        fun bind(item: MenuRadioData) {
            Log.d("MenuRadioAdapter", "ViewHolder 동작")
            menuDetailRadioButton.isChecked = item.menuDetailRadioStatus
            menuDetailRadioText.text = item.menuDetailRadioText
        }
    }
}