package com.yunwoon.coupangeatsproject.util.orderRecycler

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R

class OrderingAdapter (private var context: Context) : RecyclerView.Adapter<OrderingAdapter.ViewHolder>()  {
    var orderData = mutableListOf<OrderData>()

    private lateinit var orderMenuAdapter: OrderMenuAdapter
    private lateinit var orderMenuData : MutableList<OrderMenuData>

    fun OrderAdapter(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ordered, parent, false)
        Log.d("OrderAdapter", "onCreateViewHolder 동작")
        orderMenuAdapter = OrderMenuAdapter(context)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = orderData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderData[position])

        orderMenuData = orderData[position].orderMenuData
        orderMenuAdapter.orderMenuData = orderMenuData
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var orderStoreTitle: TextView = itemView.findViewById(R.id.ordered_text_title)
        private var orderStoreImage: ImageView = itemView.findViewById(R.id.ordered_image_view)
        private var orderDate: TextView = itemView.findViewById(R.id.ordered_text_date)
        private var orderStatus: TextView = itemView.findViewById(R.id.ordered_text_status)
        private var orderStarRating: RatingBar = itemView.findViewById(R.id.ordered_text_star_rating)
        private var orderSum: TextView = itemView.findViewById(R.id.ordered_text_sum)
        private var orderRecyclerViewMenu: RecyclerView = itemView.findViewById(R.id.ordered_recycler_view_menu)

        private var orderDelivery : Button = itemView.findViewById(R.id.ordered_button_delivery) // 배달 현황 보기
        private var orderReOrder: Button = itemView.findViewById(R.id.ordered_button_reorder) // 재주문하기
        private var orderNewReview: Button = itemView.findViewById(R.id.ordered_button_new_review) // 리뷰 쓰기
        private var orderReview: Button = itemView.findViewById(R.id.ordered_button_review) // 작성한 리뷰 보기
        private var orderReceipt: TextView = itemView.findViewById(R.id.ordered_text_receipt) // 영수증 보기

        fun bind(item: OrderData) {
            orderStoreTitle.text = item.orderStoreTitle
            orderStoreImage.setImageBitmap(item.orderStoreImage)
            orderDate.text = item.orderDate
            orderStarRating.numStars = item.orderStarRating
            orderSum.text = item.orderSum

            // 메뉴 리사이클러뷰 연결
            orderRecyclerViewMenu.layoutManager = LinearLayoutManager(context)
            orderRecyclerViewMenu.adapter = orderMenuAdapter

            // 배달 상태는 !
            orderStatus.text = "메뉴 준비중"
            orderStatus.setTextColor(Color.parseColor("#18afff"))

            orderDelivery.visibility = View.VISIBLE
            orderReOrder.visibility = View.GONE
            orderNewReview.visibility = View.GONE
            orderReview.visibility = View.GONE

            // 영수증 보기
            orderReceipt.setOnClickListener { Log.d("OrderAdapter", "영수증 보기 버튼 클릭") }
        }
    }
}