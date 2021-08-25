package com.yunwoon.coupangeatsproject.util.cartRecycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R
import com.yunwoon.coupangeatsproject.util.MenuCountDialog

class CartAdapter(private var context: Context, private var supportFragmentManager : FragmentManager) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    var cartData = mutableListOf<CartData>()
    var cartOptionData = mutableListOf<String>()
    var cartCount = 0

    fun CartAdapter(context: Context, supportFragmentManager: FragmentManager) {
        this.context = context
        this.supportFragmentManager = supportFragmentManager
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
        // private val cartCounts: Button = itemView.findViewById(R.id.cart_button_delete)
        private val cartSpinner: TextView = itemView.findViewById(R.id.cart_text_count)
        private val cartButton: Button = itemView.findViewById(R.id.cart_button_count)

        fun bind(item: CartData) {
            cartMain.text = item.cartMain
            cartPrice.text = item.cartPrice
            cartSpinner.text = item.cartCount.toString()

            if(item.cartOption.isNotEmpty()) {
                cartOption.visibility = View.VISIBLE
                for(i in item.cartOption) {
                    if(cartCount++ == 0)
                        cartOption.text = "$i"
                    else
                        cartOption.text = "${cartOption.text} \n$i"
                }
            } else {
                cartOption.visibility = View.GONE
            }

            cartButton.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val dialogMenuCount = MenuCountDialog()
                    dialogMenuCount.isCancelable = true
                    dialogMenuCount.show(supportFragmentManager, "MenuCountDialog")
                }
            })
        }
    }
}