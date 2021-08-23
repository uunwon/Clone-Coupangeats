package com.yunwoon.coupangeatsproject.util.menuDetailRecycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yunwoon.coupangeatsproject.R

// status = true => 필수 , radio button
// status = false => 선택 , check box
class MenuDetailAdapter(private var context: Context) : RecyclerView.Adapter<MenuDetailAdapter.ViewHolder>()  {
    var menuDetailDataArrayList = mutableListOf<MenuDetailData>()

    private lateinit var menuRadioAdapter: MenuRadioAdapter
    private lateinit var menuRadioData : MutableList<MenuRadioData>

    private lateinit var menuCheckAdapter: MenuCheckAdapter
    private lateinit var menuCheckData: MutableList<MenuCheckData>

    fun MenuDetailAdapter(context: Context, menuNecessaryStatus: Boolean) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuDetailAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu_detail, parent, false)
        menuRadioAdapter = MenuRadioAdapter(context)
        menuCheckAdapter = MenuCheckAdapter(context)

        Log.d("MenuDetailAdapter", "onCreateViewHolder 동작")
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = menuDetailDataArrayList.size

    override fun onBindViewHolder(holder: MenuDetailAdapter.ViewHolder, position: Int) {
        holder.bind(menuDetailDataArrayList[position])

        menuRadioData = menuDetailDataArrayList[position].menuRadioData
        menuRadioAdapter.menuRadioData = menuRadioData

        menuCheckData = menuDetailDataArrayList[position].menuCheckData
        menuCheckAdapter.menuCheckData = menuCheckData
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val menuDetailTitle: TextView = itemView.findViewById(R.id.menu_detail_title)
        private val menuDetailNecessary: TextView = itemView.findViewById(R.id.menu_detail_necessary)
        private val menuDetailRecyclerViewRadio: RecyclerView = itemView.findViewById(R.id.menu_detail_recycler_view_radio)
        private val menuDetailRecyclerViewCheck: RecyclerView = itemView.findViewById(R.id.menu_detail_recycler_view_check)

        fun bind(item: MenuDetailData) {
            if(item.menuDetailNecessary == 1) {
                menuDetailTitle.text = item.menuDetailTitle
                menuDetailNecessary.text = "필수항목"

                menuDetailRecyclerViewRadio.visibility = View.VISIBLE
                menuDetailRecyclerViewCheck.visibility = View.GONE

                menuDetailRecyclerViewRadio.layoutManager = LinearLayoutManager(context)
                menuDetailRecyclerViewRadio.adapter = menuRadioAdapter
            }
            else {
                menuDetailTitle.text = item.menuDetailTitle
                menuDetailNecessary.text = ""

                menuDetailRecyclerViewRadio.visibility = View.GONE
                menuDetailRecyclerViewCheck.visibility = View.VISIBLE

                menuDetailRecyclerViewCheck.layoutManager = LinearLayoutManager(context)
                menuDetailRecyclerViewCheck.adapter = menuCheckAdapter
            }
        }
    }
}