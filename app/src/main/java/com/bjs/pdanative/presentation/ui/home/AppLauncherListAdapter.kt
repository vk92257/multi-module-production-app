package com.bjs.pdanative.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bjs.pdanative.R

class AppLauncherListAdapter(
    val ctx: Context,
    val appList: ArrayList<AppInfo>,
    val onItemClick: (Int, AppInfo) -> Unit
) : RecyclerView.Adapter<AppLauncherListAdapter.AppLauncherViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AppLauncherViewHolder {
        val viewHolder: AppLauncherViewHolder?
        val inflater = LayoutInflater.from(viewGroup.context)
        val rightView =
            inflater.inflate(R.layout.item_app_launcher, viewGroup, false)
        viewHolder = AppLauncherViewHolder(rightView)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    inner class AppLauncherViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val ivAppImage: ImageView = itemView.findViewById(R.id.iv_app)
        val tvAppTitle: TextView = itemView.findViewById(R.id.tv_app_title)
        val cardView: CardView = itemView.findViewById(R.id.cardview)

    }

    override fun onBindViewHolder(holder: AppLauncherViewHolder, position: Int) {
        val appListModel: AppInfo = appList[position]
        holder.tvAppTitle.text = appListModel.label
        holder.ivAppImage.setImageDrawable(appListModel.icon)
        holder.itemView.setOnClickListener {
            val animZoomIn = AnimationUtils.loadAnimation(
                ctx,
                R.anim.zoom_in
            )
            // assigning that animation to
            // the image and start animation
            holder.cardView.startAnimation(animZoomIn)
            holder.tvAppTitle.startAnimation(animZoomIn)
            onItemClick(position, appList[position])
        }

        holder.ivAppImage.setOnClickListener {
            holder.itemView.performClick()
        }
    }

}