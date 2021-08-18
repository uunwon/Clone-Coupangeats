package com.yunwoon.coupangeatsproject.util.smallReviewRecycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R

class SmallReviewAdapter(private val context: Context) : RecyclerView.Adapter<SmallReviewAdapter.ViewHolder>()  {
    var smallReviewDataArrayList = mutableListOf<SmallReviewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_small_review, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(smallReviewDataArrayList[position])
    }

    override fun getItemCount(): Int = smallReviewDataArrayList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val reviewImage: ImageView = itemView.findViewById(R.id.small_review_image_view)
        private val reviewText: TextView = itemView.findViewById(R.id.small_review_text)
        private val reviewStarRating: TextView = itemView.findViewById(R.id.small_review_text_star_rating)

        fun bind(item: SmallReviewData) {
            reviewImage.setImageBitmap(item.reviewImage)
            reviewText.text = item.reviewText
            reviewStarRating.text = item.reviewStarRating
        }
    }
}