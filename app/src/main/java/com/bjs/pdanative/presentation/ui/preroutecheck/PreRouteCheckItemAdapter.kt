package com.bjs.pdanative.presentation.ui.preroutecheck

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bjs.pdanative.R
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Created by deepak on {14/07/21}
 */
class PreRouteCheckItemAdapter(
    val context: Context,
    private val itemClickListener: ItemClickListener,
    private val itemList: ArrayList<PreRouteCheck>
) :
    RecyclerView.Adapter<PreRouteCheckItemAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // views initialization
        val tvText: TextView = view.findViewById(R.id.tv_desc)
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvFaultTitle: TextView = view.findViewById(R.id.tv_fault_text)
        val ivVehicleIcon: ImageView = view.findViewById(R.id.iv_vehicle_icon)
        val btnNo: Button = view.findViewById(R.id.btn_no)
        val btnYes: Button = view.findViewById(R.id.btn_yes)
        val llFailPass: LinearLayout = view.findViewById(R.id.ll_fail_pass)
        val llFail: LinearLayout = view.findViewById(R.id.ll_faults)
        val llFaultLogged: LinearLayout = view.findViewById(R.id.ll_logged_fault)
        val llAddFault: LinearLayout = view.findViewById(R.id.ll_add_fault)
        val tvFaultDesc: TextView = view.findViewById(R.id.tv_fault_desc)
        val spnFaultyComponent: Spinner = view.findViewById(R.id.spn_fault_component)
        val ivClose: ImageView = view.findViewById(R.id.iv_close)
        val btnAddFault: Button = view.findViewById(R.id.btn_log_fault)
        val etFaultNotes: EditText = view.findViewById(R.id.et_fault_additional_notes)
        val tvLoggedFault: TextView = view.findViewById(R.id.tv_fault_logged)
        val tvAdditionalNotes: TextView = view.findViewById(R.id.tv_additional_notes)
        val btnDelete: Button = view.findViewById(R.id.btn_delete)
        val btnEdit: Button = view.findViewById(R.id.btn_edit)
        val btnContinue: Button = view.findViewById(R.id.btn_done)


        // variable initialization
        var faultyComponentAdapter: ArrayAdapter<String>? = null
        var faultyComponentSelected: String? = null

        // populate data in faultComponent dropdown
        fun setUpFaultComponent() {
            faultyComponentAdapter =
                ArrayAdapter(
                    context,
                    R.layout.spinner_layout,
                    PreRouteCheckFaultData.getFaultyComponents()
                )
            spnFaultyComponent.adapter = faultyComponentAdapter
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pre_route_check, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = itemList[position]
        holder.apply {
            tvTitle.text = data.title
            tvText.text = data.description
            Glide.with(context).load(data.icon).into(ivVehicleIcon)
            setUpFaultComponent()
            when {
                data.yes -> {
                    llFailPass.visibility = View.VISIBLE
                    llFail.visibility = View.GONE
                }
                data.no -> {
                    llFailPass.visibility = View.GONE
                    tvFaultTitle.text = data.faultTitle
                    tvFaultDesc.text = data.faultDescription
                    llFail.visibility = View.VISIBLE
                    if (data.preCheckRouteFault != null) {
                        llAddFault.visibility = View.GONE
                        ivClose.visibility = View.GONE
                        llFaultLogged.visibility = View.VISIBLE
                        tvLoggedFault.text = data.preCheckRouteFault?.faultyComponent
                        tvAdditionalNotes.text = data.preCheckRouteFault?.additionalNote
                    } else {
                        llFaultLogged.visibility = View.GONE
                        ivClose.visibility = View.VISIBLE
                        llAddFault.visibility = View.VISIBLE
                    }
                }
                else -> {
                    llFailPass.visibility = View.VISIBLE
                    llFail.visibility = View.GONE
                    tvText.text = data.description
                }
            }

            btnYes.setOnClickListener {
                itemClickListener.onItemYesClick(position)
            }

            btnNo.setOnClickListener {
                itemClickListener.onItemNoClick(position)
            }

            ivClose.setOnClickListener {
                itemClickListener.onItemCloseClick(position)
            }
            btnContinue.setOnClickListener {
                itemClickListener.onItemContinueClick(position)
            }

            btnDelete.setOnClickListener {
                data.preCheckRouteFault = null
                data.no = false
                notifyItemChanged(position)
            }

            btnEdit.setOnClickListener {
                llFaultLogged.visibility = View.GONE
                llAddFault.visibility = View.VISIBLE
                ivClose.visibility=View.VISIBLE
                etFaultNotes.setText(data.preCheckRouteFault?.additionalNote)
                faultyComponentSelected = data.preCheckRouteFault?.faultyComponent
                // set dropdown value
                spnFaultyComponent.setSelection(
                    faultyComponentAdapter!!.getPosition(
                        faultyComponentSelected
                    )
                )
            }




            btnAddFault.setOnClickListener {
                val preCheckFault =
                    PreCheckRouteFault(faultyComponentSelected!!, etFaultNotes.text.toString())
                data.preCheckRouteFault = preCheckFault
                etFaultNotes.setText("")
                notifyItemChanged(position)
            }

            spnFaultyComponent.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    faultyComponentSelected = parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }


        }
    }


    override fun getItemCount(): Int {
        return itemList.size
    }


    interface ItemClickListener {
        fun onItemYesClick(position: Int)
        fun onItemNoClick(position: Int)
        fun onItemCloseClick(position: Int)
        fun onItemContinueClick(position: Int)
    }

}