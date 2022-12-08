package com.bjs.pdanative.presentation.ui.camera

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bjs.pdanative.R
import com.bumptech.glide.Glide
import java.util.*

/**
 * Created by deepak on {10/05/21}
 */
class ImageAdapter(
    val context: Context,
    val imageList: ArrayList<ImagesModel>,
    val itemClickListener: ItemClickListener,
    private val isDeleteFromDialog: Boolean,
    private val isDeleteButtonNeeded: Boolean
) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_image)
        val rlDelete: RelativeLayout = itemView.findViewById(R.id.rl_cancel)
        val ivDelete: ImageView = itemView.findViewById(R.id.iv_cancel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {
            try {
                Glide.with(context).load(imageList[position].imageUri).into(imageView)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (isDeleteButtonNeeded) {
                rlDelete.visibility = View.VISIBLE
            } else rlDelete.visibility = View.GONE


            rlDelete.setOnClickListener {
                if (isDeleteFromDialog) {
                    imageList.removeAt(position)
                    itemClickListener.onItemDeleteClick(position)
                    notifyDataSetChanged()
                } else {
                    itemClickListener.onItemDeleteClick(position)
                }
            }

            ivDelete.setOnClickListener {
                rlDelete.performClick()
            }

            itemView.setOnClickListener {
                itemClickListener.onItemClick(position)
            }
        }


    }

    override fun getItemCount(): Int {
        return imageList.size
    }


    interface ItemClickListener {
        fun onItemClick(position: Int)
        fun onItemDeleteClick(position: Int)

    }

}