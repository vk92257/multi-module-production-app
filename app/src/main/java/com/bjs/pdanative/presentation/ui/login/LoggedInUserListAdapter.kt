package com.bjs.pdanative.presentation.ui.login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bjs.pdanative.R
import com.bumptech.glide.Glide

/**
 * Created by deepak on {11/05/21}
 */
class LoggedInUserListAdapter(
    val context: Context,
    private val itemClickListener: ItemClickListener,
    private val userList: ArrayList<User>
) :
    RecyclerView.Adapter<LoggedInUserListAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvChangeRole: TextView = view.findViewById(R.id.tv_change_role)
        val ivProfile: ImageView = view.findViewById(R.id.cv_profile)
        val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        val tvRole: TextView = itemView.findViewById(R.id.tv_role)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_logged_in_users, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvRole.text = userList[position].jobTitle
        Glide.with(context).load(userList[position].userImage).into(holder.ivProfile)
        val number = userList[position].phoneNo!!.substring(userList[position].phoneNo!!.length - 4)
        holder.tvPhone.text = "*** *** $number"
        holder.tvChangeRole.setOnClickListener {
            itemClickListener.onItemClick(userList[position])
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    interface ItemClickListener {
        fun onItemClick(user: User)
    }

}