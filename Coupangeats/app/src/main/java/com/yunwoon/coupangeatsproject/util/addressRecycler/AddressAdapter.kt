package com.yunwoon.coupangeatsproject.util.addressRecycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R

class AddressAdapter(private var context: Context) : RecyclerView.Adapter<AddressAdapter.ViewHolder>()  {
    var addressData = mutableListOf<AddressData>()

    fun AddressAdapter(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = addressData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(addressData[position])

        holder.itemView.setOnClickListener {
            holder.itemView.findViewById<ImageView>(R.id.address_image_view_check)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val addressCategoryImage: ImageView = itemView.findViewById(R.id.address_image_view)
        private val addressTitle: TextView = itemView.findViewById(R.id.address_text_title)
        private val addressDetail: TextView = itemView.findViewById(R.id.address_text_detail)
        private val addressCheckImage: ImageView = itemView.findViewById(R.id.address_image_view_check)

        fun bind(item: AddressData) {

            // 체크 이미지 관련 처리하기
            when(item.addressCategory) {
                "집" -> {
                    addressCategoryImage.setImageResource(R.drawable.ic_home_view_unselected)
                    addressTitle.text = item.addressCategory
                    addressDetail.text = item.addressTitle
                }
                "회사" -> {
                    addressCategoryImage.setImageResource(R.drawable.ic_company_address)
                    addressTitle.text = item.addressCategory
                    addressDetail.text = item.addressTitle
                }
                "기타" -> {
                    addressCategoryImage.setImageResource(R.drawable.ic_location)
                    addressTitle.text = item.addressDetail
                    addressDetail.text = item.addressTitle
                }
            }
        }
    }
}