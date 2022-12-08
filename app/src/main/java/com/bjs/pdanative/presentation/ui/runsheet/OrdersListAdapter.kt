package com.bjs.pdanative.presentation.ui.runsheet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bjs.pdanative.R

class OrdersListAdapter(
    private val context: Context,
    var list: ArrayList<OrderListDummyData>,
    val onItemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    private inner class OrderItemsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val viewModel = list[position]

            itemView.setOnClickListener {
                onItemClick(position)
            }
        }
    }


    private inner class BreakItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            itemView.setOnClickListener {
                onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ONE) {
            return OrderItemsViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_order_list, parent, false)
            )
        }
        return BreakItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_break, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].viewType == VIEW_TYPE_ONE) {
            (holder as OrderItemsViewHolder).bind(position)
        } else {
            (holder as BreakItemViewHolder).bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }


}