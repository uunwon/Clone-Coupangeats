package com.yunwoon.coupangeatsproject.util.storeRecycler

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.src.main.home.HomeFragment
import java.io.IOException
import java.net.URL

class StoreAdapter(private val context: Context, private var homeFragment: HomeFragment) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {
    var storeDataArrayList = mutableListOf<StoreData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_store, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(storeDataArrayList[position])

        holder.itemView.setOnClickListener {
            homeFragment.moveToStoreActivity(storeDataArrayList[position].storeIndex)
        }
    }

    override fun getItemCount(): Int = storeDataArrayList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val storeImage: ImageView = itemView.findViewById(R.id.store_image_view1)
        private val storeTitle: TextView = itemView.findViewById(R.id.store_text_title)
        private val storeDeliveryTime: TextView = itemView.findViewById(R.id.store_text_delivery_time)
        private val storeStarRating: TextView = itemView.findViewById(R.id.store_text_star_rating)
        private val storeReviewCount: TextView = itemView.findViewById(R.id.store_text_review_count)
        private val storeLocation: TextView = itemView.findViewById(R.id.store_text_location)
        private val storeDeliveryTip: TextView = itemView.findViewById(R.id.store_text_delivery_tip)

        fun bind(item: StoreData) {
            // 이미지 변환
            val LoadImage = object : AsyncTask<String, Int, Bitmap?>() {
                var bitmap : Bitmap? = null

                override fun doInBackground(vararg p0: String?): Bitmap? {
                    try {
                        val inputStream = URL(p0[0]).openStream()
                        bitmap = BitmapFactory.decodeStream(inputStream)
                    } catch (e : IOException) {
                        e.printStackTrace()
                    }
                    return bitmap
                }

                override fun onPostExecute(result: Bitmap?) {
                    storeImage.setImageBitmap(bitmap)
                }
            }

            LoadImage.execute(item.storeImage)

            storeTitle.text = item.storeTitle
            storeDeliveryTime.text = item.storeDeliveryTime
            storeStarRating.text = item.storeStarRating
            storeReviewCount.text = item.storeReviewCount
            storeLocation.text = item.storeLocation
            storeDeliveryTip.text = item.storeDeliveryTip
        }
    }
}