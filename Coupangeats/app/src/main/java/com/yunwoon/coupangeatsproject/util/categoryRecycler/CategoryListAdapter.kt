package com.yunwoon.coupangeatsproject.util.categoryRecycler

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
import com.yunwoon.coupangeatsproject.src.storelist.StoreListActivity
import java.io.IOException
import java.net.URL

class CategoryListAdapter (private var context: Context) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    var categoryData = mutableListOf<CategoryData>()

    fun CategoryAdapter(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = categoryData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryData[position])

        holder.itemView.setOnClickListener {
            (context as StoreListActivity).changeCategory(position)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val reviewImage: ImageView = itemView.findViewById(R.id.category_circle_image)
        private val reviewContent: TextView = itemView.findViewById(R.id.category_text_title)

        fun bind(item: CategoryData) {
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
                    reviewImage.setImageBitmap(bitmap)
                }
            }

            LoadImage.execute(item.categoryCircleImage)
            reviewContent.text = item.categoryTextTitle
        }
    }
}