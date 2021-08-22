package com.yunwoon.coupangeatsproject.util.storeRecycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.src.storelist.StoreListActivity

class StoreListAdapter(private val context: Context) : RecyclerView.Adapter<StoreListAdapter.ViewHolder>() {
    var storeDataArrayList = mutableListOf<StoreData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_store, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(storeDataArrayList[position])

        holder.itemView.setOnClickListener {
            (context as StoreListActivity).moveToStoreActivity(position)
        }
    }

    override fun getItemCount(): Int = storeDataArrayList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val storeImage1: ImageView = itemView.findViewById(R.id.store_image_view1)
        private val storeImage2: ImageView = itemView.findViewById(R.id.store_image_view2)
        private val storeImage3: ImageView = itemView.findViewById(R.id.store_image_view3)
        private val storeTitle: TextView = itemView.findViewById(R.id.store_text_title)
        private val storeDeliveryTime: TextView = itemView.findViewById(R.id.store_text_delivery_time)
        private val storeStarRating: TextView = itemView.findViewById(R.id.store_text_star_rating)
        private val storeReviewCount: TextView = itemView.findViewById(R.id.store_text_review_count)
        private val storeLocation: TextView = itemView.findViewById(R.id.store_text_location)
        private val storeDeliveryTip: TextView = itemView.findViewById(R.id.store_text_delivery_tip)

        fun bind(item: StoreData) {
            storeImage1.setImageBitmap(item.storeImage1)
            storeImage2.setImageBitmap(item.storeImage2)
            storeImage3.setImageBitmap(item.storeImage3)

            storeTitle.text = item.storeTitle
            storeDeliveryTime.text = item.storeDeliveryTime
            storeStarRating.text = item.storeStarRating
            storeReviewCount.text = item.storeReviewCount
            storeLocation.text = item.storeLocation
            storeDeliveryTip.text = item.storeDeliveryTip
        }
    }
}