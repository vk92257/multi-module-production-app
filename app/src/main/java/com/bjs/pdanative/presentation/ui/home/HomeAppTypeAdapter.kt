package com.bjs.pdanative.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bjs.hgv.utils.ToastUtil
import com.bjs.pdanative.R


class HomeAppTypeAdapter(
    val context: Context,
    private val itemList: ArrayList<HomeApp>,
    val onItemClick: (Int, Int) -> Unit
) :
    RecyclerView.Adapter<HomeAppTypeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rvApps: RecyclerView = view.findViewById(R.id.rv_app_launchers)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_apps, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = itemList[position]
        holder.apply {
            val adapter = AppLauncherListAdapter(
                context,
                data.appListData as ArrayList<AppInfo>
            ) { appListItemPosition, appInfo ->
                onItemClick(position, appListItemPosition)
            }
            rvApps.addItemDecoration(SpacesItemDecoration(10))
            rvApps.layoutManager = GridLayoutManager(context, 3)
            rvApps.adapter = adapter
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}