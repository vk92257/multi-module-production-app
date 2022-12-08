package com.bjs.pdanative.presentation.ui.lgvcheck

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bjs.pdanative.R
import com.bumptech.glide.Glide

class LGVCheckSummaryAdapter(
    val context: Context,
    private val itemList: ArrayList<LgvCheck>
) :
    RecyclerView.Adapter<LGVCheckSummaryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFault: TextView = view.findViewById(R.id.tv_fault_text)
        val tvFaultResult: TextView = view.findViewById(R.id.tv_fault_result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lgv_check_summary, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = itemList[position]
        holder.apply {
            tvFault.text = data.title
            if (data.pass) {
                tvFaultResult.text = context.getString(R.string.pass)
                tvFaultResult.setTextColor(Color.parseColor("#58C574"))
            } else {
                tvFaultResult.text = context.getString(R.string.fail)
                tvFaultResult.setTextColor(Color.parseColor("#FE5442"))
            }
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}