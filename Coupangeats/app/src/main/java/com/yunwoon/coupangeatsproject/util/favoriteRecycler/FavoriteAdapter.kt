package com.yunwoon.coupangeatsproject.util.favoriteRecycler

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
import java.io.IOException
import java.net.URL

class FavoriteAdapter (private val context: Context) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    var favoriteData = mutableListOf<FavoriteData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favoriteData[position])
    }

    override fun getItemCount(): Int = favoriteData.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val favoriteStoreImage : ImageView = itemView.findViewById(R.id.favorite_store_image)
        private val favoriteTitle : TextView = itemView.findViewById(R.id.favorite_text_title)
        private val favoriteStarRating : TextView = itemView.findViewById(R.id.favorite_text_star_rating)
        private val favoriteReviewCount : TextView = itemView.findViewById(R.id.favorite_text_review_count)
        private val favoriteLocation : TextView = itemView.findViewById(R.id.favorite_text_location)
        private val favoriteDeliveryTime : TextView = itemView.findViewById(R.id.favorite_text_delivery_time)
        private val favoriteDeliveryTip : TextView = itemView.findViewById(R.id.favorite_text_delivery_tip)

        fun bind(item: FavoriteData) {
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
                    favoriteStoreImage.setImageBitmap(bitmap)
                }
            }

            LoadImage.execute(item.favoriteStoreImage)

            favoriteTitle.text = item.favoriteTitle
            favoriteStarRating.text = item.favoriteStarRating
            favoriteReviewCount.text = item.favoriteReviewCount
            favoriteLocation.text = item.favoriteLocation
            favoriteDeliveryTime.text = item.favoriteDeliveryTime
            favoriteDeliveryTip.text = item.favoriteDeliveryTip
        }
    }
}