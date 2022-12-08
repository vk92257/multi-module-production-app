package com.bjs.pdanative.presentation.ui.lgvcheck

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.ui.camera.ImageAdapter
import com.bjs.pdanative.presentation.ui.camera.ImagesModel

class LGVCheckFaultAdapter(
    val context: Context,
    private val itemList: ArrayList<Fault>,
    private val listener: FaultItemClickListener
) :
    RecyclerView.Adapter<LGVCheckFaultAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFaultyComponent: TextView = view.findViewById(R.id.tv_faulty_component)
        val tvFault: TextView = view.findViewById(R.id.tv_fault)
        val tvPriority: TextView = view.findViewById(R.id.tv_priority)
        val tvAdditionalNotes: TextView = view.findViewById(R.id.tv_additional_notes)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
        val btnDelete: Button = view.findViewById(R.id.btn_delete)
        val btnEdit: Button = view.findViewById(R.id.btn_edit)
        val rvFaultImages: RecyclerView = view.findViewById(R.id.rv_fault_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fault_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = itemList[position]
        holder.apply {
            tvCount.text = (position + 1).toString()
            tvFaultyComponent.text = data.faultyComponent
            tvFault.text = data.fault
            tvPriority.text = data.priority
            tvAdditionalNotes.text = data.additionalNote
            rvFaultImages.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                val imageAdapter = ImageAdapter(
                    context,
                    data.faultImages as java.util.ArrayList<ImagesModel>,
                    object : ImageAdapter.ItemClickListener {

                        override fun onItemClick(position: Int) {

                        }

                        override fun onItemDeleteClick(position: Int) {

                        }
                    }, isDeleteFromDialog = false,
                    isDeleteButtonNeeded = false
                )
                adapter = imageAdapter
            }

            btnDelete.setOnClickListener {
                listener.onItemDeleteClick(position)
            }
            btnEdit.setOnClickListener {
                listener.onItemEditClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    interface FaultItemClickListener {
        fun onItemDeleteClick(pos: Int)
        fun onItemEditClick(pos: Int)
    }

}