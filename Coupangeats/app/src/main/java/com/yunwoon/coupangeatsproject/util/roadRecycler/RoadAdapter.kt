package com.yunwoon.coupangeatsproject.util.roadRecycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.config.ApplicationClass
import com.yunwoon.coupangeatsproject.src.address.AddressActivity

class RoadAdapter(private val context: Context) : RecyclerView.Adapter<RoadAdapter.ViewHolder>() {
    var roadDataArrayList = ArrayList<RoadData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoadAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_road, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = roadDataArrayList.size

    override fun onBindViewHolder(holder: RoadAdapter.ViewHolder, position: Int) {
        holder.bind(roadDataArrayList[position])

        holder.itemView.setOnClickListener {
            Log.d("AddressActivity", "RoadData $position 선택됨")
            val activity = context as AddressActivity

            ApplicationClass.sEditor.putString("temporaryAddress", roadDataArrayList[position].roadText).apply()
            ApplicationClass.sEditor.putString("temporaryAddressDetail", roadDataArrayList[position].roadTextDetail).apply()

            activity.moveToAddressDetailPage()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val roadText: TextView = itemView.findViewById(R.id.road_text)
        private val roadTextDetail: TextView = itemView.findViewById(R.id.road_text_detail)

        fun bind(item: RoadData) {
            roadText.text = item.roadText
            roadTextDetail.text = item.roadTextDetail
        }
    }
}