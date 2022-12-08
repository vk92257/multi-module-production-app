package com.bjs.pdanative.presentation.ui.lgvcheck

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bjs.pdanative.R
import com.bumptech.glide.Glide

class LGVCheckAdapter(
    val context: Context,
    private val itemList: ArrayList<LgvCheck>
) :
    RecyclerView.Adapter<LGVCheckAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivBackground: ImageView = view.findViewById(R.id.iv_background)
        val ivIcon: ImageView = view.findViewById(R.id.iv_icon)
        val ivIndex: ImageView = view.findViewById(R.id.iv_dot)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lgv_check, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = itemList[position]
        holder.apply {
            Glide.with(context).load(data.icon).into(ivIcon)
            if (data.selected) {
                ivIndex.visibility = View.VISIBLE
            } else {
                ivIndex.visibility = View.INVISIBLE
            }
            when {
                data.pass -> {
                    ivBackground.setImageResource(R.color.green)
                }
                data.fail -> {
                    ivBackground.setImageResource(R.color.red)
                }
                data.selected -> {
                    ivBackground.setImageResource(R.color.white)
                }
                else -> {
                    ivBackground.setImageResource(R.color.lgv_back_color)
                }
            }

        }


    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}