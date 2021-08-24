package com.yunwoon.coupangeatsproject.util.cartRecycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R

class CartAdapter(private var context: Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    var cartData = mutableListOf<CartData>()

    fun CartAdapter(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = cartData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cartData[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cartMain: TextView = itemView.findViewById(R.id.cart_text_main)
        private val cartOption: TextView = itemView.findViewById(R.id.cart_text_option)
        private val cartPrice: TextView = itemView.findViewById(R.id.cart_text_price)
        private val cartCounts: Button = itemView.findViewById(R.id.cart_button_delete)

        fun bind(item: CartData) {
            cartMain.text = item.cartMain
            cartPrice.text = item.cartPrice
            cartCounts.text = item.cartCount.toString()

            if(item.cartOption != "") {
                cartOption.visibility = View.VISIBLE
                cartOption.text = item.cartOption
            } else {
                cartOption.visibility = View.GONE
            }
        }
    }
}