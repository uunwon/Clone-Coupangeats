package com.yunwoon.coupangeatsproject.util.categoryRecycler

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.src.storelist.StoreListActivity
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException
import java.net.URL

class CategoryListAdapter (private var context: Context, private var categoryPosition: Int) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    var categoryData = mutableListOf<CategoryData>()
    lateinit var resources : Resources

    fun CategoryAdapter(context: Context, categoryPosition: Int) {
        this.context = context
        this.categoryPosition = categoryPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        resources = context.resources

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = categoryData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryData[position])

        holder.itemView.findViewById<CircleImageView>(R.id.category_circle_image)

        holder.itemView.setOnClickListener {
            (context as StoreListActivity).changeCategory(position)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val categoryImage: CircleImageView = itemView.findViewById(R.id.category_circle_image)
        private val categoryTitle: TextView = itemView.findViewById(R.id.category_text_title)
        private val categoryView : View = itemView.findViewById(R.id.category_view)

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
                    categoryImage.setImageBitmap(bitmap)
                }
            }

            LoadImage.execute(item.categoryCircleImage)
            categoryTitle.text = item.categoryTextTitle

            if(categoryPosition == adapterPosition) {
                categoryImage.borderColor = resources.getColor(R.color.blue_300)
                categoryTitle.setTextColor(resources.getColor(R.color.blue_300))
                categoryImage.borderWidth = 10
                categoryView.visibility = View.VISIBLE
            }

            /* reviewImage.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val position = adapterPosition

                    for(i in categoryData) {
                        if()
                    }
                }

            })  */
        }
    }
}