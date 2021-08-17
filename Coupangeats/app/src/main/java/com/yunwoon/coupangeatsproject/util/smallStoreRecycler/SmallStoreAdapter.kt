package com.yunwoon.coupangeatsproject.util.smallStoreRecycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.src.main.home.HomeFragment

class SmallStoreAdapter(private val context: Context, private var homeFragment: HomeFragment) : RecyclerView.Adapter<SmallStoreAdapter.ViewHolder>()  {
    var smallStoreDataArrayList = mutableListOf<SmallStoreData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_small_store, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(smallStoreDataArrayList[position])
    }

    override fun getItemCount(): Int = smallStoreDataArrayList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val storeImage: ImageView = itemView.findViewById(R.id.small_store_image)
        private val storeTitle: TextView = itemView.findViewById(R.id.small_store_text_title)
        private val storeStarRating: TextView = itemView.findViewById(R.id.small_store_text_star_rating)
        private val storeReviewCount: TextView = itemView.findViewById(R.id.small_store_text_review_count)
        private val storeLocation: TextView = itemView.findViewById(R.id.small_store_text_location)
        private val storeDeliveryTip: TextView = itemView.findViewById(R.id.small_store_text_delivery_tip)

        fun bind(item: SmallStoreData) {
            storeImage.setImageBitmap(item.storeImage)
            storeTitle.text = item.storeTitle
            storeStarRating.text = item.storeStarRating
            storeReviewCount.text = item.storeReviewCount
            storeLocation.text = item.storeLocation
            storeDeliveryTip.text = item.storeDeliveryTip
        }
    }
}