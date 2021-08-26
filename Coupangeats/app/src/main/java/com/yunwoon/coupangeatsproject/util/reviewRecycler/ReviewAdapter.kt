package com.yunwoon.coupangeatsproject.util.reviewRecycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R

class ReviewAdapter(private var context: Context) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    var reviewData = mutableListOf<ReviewData>()

    fun ReviewAdapter(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = reviewData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewData[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val reviewUserName: TextView = itemView.findViewById(R.id.review_text_user_name)
        private val reviewStarRating: RatingBar = itemView.findViewById(R.id.review_rating_bar)
        private val reviewDate: TextView = itemView.findViewById(R.id.review_text_date)
        private val reviewContent: TextView = itemView.findViewById(R.id.review_text_content)

        fun bind(item: ReviewData) {
            reviewUserName.text = item.reviewUserName
            reviewStarRating.rating = item.reviewStarRating.toFloat()
            reviewDate.text = item.reviewDate
            reviewContent.text = item.reviewContent
        }
    }
}