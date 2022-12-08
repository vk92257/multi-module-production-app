package com.bjs.pdanative.presentation.ui.lgvcheck

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
import com.bjs.pdanative.presentation.ui.camera.CameraFragment
import com.bjs.pdanative.presentation.ui.camera.ImageAdapter
import com.bjs.pdanative.presentation.ui.camera.ImagesModel
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Created by deepak on {11/07/21}
 */
class LGVCheckItemAdapter(
    val context: Context,
    private val itemClickListener: ItemClickListener,
    private val itemList: ArrayList<LgvCheck>
) :
    RecyclerView.Adapter<LGVCheckItemAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        // views initialization
        val tvText: TextView = view.findViewById(R.id.tv_desc)
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvFaultText: TextView = view.findViewById(R.id.tv_fault_text)
        val ivVehicleIcon: ImageView = view.findViewById(R.id.iv_vehicle_icon)
        val ivBackground: RelativeLayout = view.findViewById(R.id.rl_vehicle_back)
        val llFailPass: LinearLayout = view.findViewById(R.id.ll_fail_pass)
        val llFail: LinearLayout = view.findViewById(R.id.ll_faults)
        val btnFail: Button = view.findViewById(R.id.btn_fail)
        val btnPass: Button = view.findViewById(R.id.btn_pass)
        val btnAddFault: Button = view.findViewById(R.id.btn_add_fault)
        val btnAddAnotherFault: Button = view.findViewById(R.id.btn_log_another_fault)
        val btnContinue: Button = view.findViewById(R.id.btn_continue)
        val rvLoggedFaults: RecyclerView = view.findViewById(R.id.rv_faults)
        val ivClose: ImageView = view.findViewById(R.id.iv_close)
        val ivFaultImage: FloatingActionButton = view.findViewById(R.id.fab_photo)
        val btnLogFault: Button = view.findViewById(R.id.btn_log_fault)
        val spnFaultyComponent: Spinner = view.findViewById(R.id.spn_fault_component)
        val spnFault: Spinner = view.findViewById(R.id.spn_fault)
        val spnPriority: Spinner = view.findViewById(R.id.spn_priority)
        val etFaultNotes: EditText = view.findViewById(R.id.et_fault_additional_notes)
        val llLogFaults: LinearLayout = view.findViewById(R.id.ll_faults_logs)
        val rvFaultImages: RecyclerView = view.findViewById(R.id.rv_fault_image)
        val scrollView: NestedScrollView = view.findViewById(R.id.scroll)


        // variable initialization
        var faultyComponentSelected: String? = null
        var faultSelected: String? = null
        var prioritySelected: String? = null
        var faultyComponentAdapter: ArrayAdapter<String>? = null
        var faultAdapter: ArrayAdapter<String>? = null
        var priorityAdapter: ArrayAdapter<String>? = null


        fun showDefaultLayout() {
            btnAddFault.visibility = View.GONE
            llFail.visibility = View.GONE
            llLogFaults.visibility = View.GONE
            btnFail.visibility = View.VISIBLE
            btnPass.visibility = View.VISIBLE
            llFailPass.visibility = View.VISIBLE
        }


        // populate data in faultyComponent dropdown
        fun setUpFaultyComponent() {
            faultyComponentAdapter =
                ArrayAdapter(
                    context,
                    R.layout.spinner_layout,
                    LgvCheckFaultData.getFaultyComponents()
                )
            spnFaultyComponent.adapter = faultyComponentAdapter
            addFault()
        }


        // populate data in fault dropdown
        private fun addFault() {
            faultAdapter =
                ArrayAdapter(context, R.layout.spinner_layout, LgvCheckFaultData.getFaults())
            spnFault.adapter = faultAdapter
            addPriority()
        }

        // populate data in priority dropdown
        private fun addPriority() {
            priorityAdapter =
                ArrayAdapter(context, R.layout.spinner_layout, LgvCheckFaultData.getPriority())
            spnPriority.adapter = priorityAdapter
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_lgvcheck_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var isFaultUpdate = false
        var faultPosition = 0
        val data = itemList[position]
        var imageAdapter: ImageAdapter? = null
        var faultImageList: ArrayList<ImagesModel> = ArrayList()

        holder.apply {
            tvFaultText.text = data.faultTitle
            tvTitle.text = data.title
            Glide.with(context).load(data.icon).into(ivVehicleIcon)
            setUpFaultyComponent()
            rvFaultImages.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


            // show layout on basis of default, pass and fail
            when {
                data.pass -> {
                    ivBackground.setBackgroundResource(R.drawable.background_lgv_image_success_circle)
                    tvText.text = data.description
                    ivVehicleIcon.setColorFilter(ContextCompat.getColor(context, R.color.black))
                    showDefaultLayout()
                }
                data.fail -> {
                    ivBackground.setBackgroundResource(R.drawable.background_lgv_image_fail_circle)
                    ivVehicleIcon.setColorFilter(ContextCompat.getColor(context, R.color.white))
                    tvText.text = data.faultDescription
                    btnFail.visibility = View.GONE
                    btnPass.visibility = View.GONE
                    llFail.visibility = View.GONE
                    llFailPass.visibility = View.VISIBLE


                    // show list and set adapter when faults logged > 0
                    if (data.faultList!!.size > 0) {
                        btnAddFault.visibility = View.GONE
                        llLogFaults.visibility = View.VISIBLE
                        scrollView.fullScroll(ScrollView.FOCUS_UP)

                        // logged faults list
                        rvLoggedFaults.apply {
                            layoutManager = LinearLayoutManager(context)
                            val faultAdapter = LGVCheckFaultAdapter(
                                context,
                                data.faultList as ArrayList<Fault>,
                                object : LGVCheckFaultAdapter.FaultItemClickListener {
                                    override fun onItemDeleteClick(pos: Int) {
                                        data.faultList!!.removeAt(pos)
                                        ivClose.performClick()
                                    }

                                    override fun onItemEditClick(pos: Int) {
                                        llFailPass.visibility = View.GONE
                                        llFail.visibility = View.VISIBLE
                                        val fault = data.faultList!![pos]
                                        etFaultNotes.setText(fault.additionalNote)
                                        faultyComponentSelected = fault.faultyComponent
                                        faultSelected = fault.fault
                                        prioritySelected = fault.priority
                                        // set dropdown value
                                        spnFaultyComponent.setSelection(
                                            faultyComponentAdapter!!.getPosition(
                                                faultyComponentSelected
                                            )
                                        )
                                        spnFault.setSelection(
                                            faultAdapter!!.getPosition(
                                                faultSelected
                                            )
                                        )
                                        spnPriority.setSelection(
                                            priorityAdapter!!.getPosition(
                                                prioritySelected
                                            )
                                        )
                                        isFaultUpdate = true
                                        faultPosition = pos
                                        faultImageList =
                                            data.faultList!![faultPosition].faultImages as ArrayList<ImagesModel>
                                        imageAdapter = ImageAdapter(
                                            context,
                                            faultImageList,

                                            // image captured list callback
                                            object : ImageAdapter.ItemClickListener {
                                                override fun onItemClick(position: Int) {

                                                }

                                                override fun onItemDeleteClick(position: Int) {
                                                    faultImageList.removeAt(position)
                                                    imageAdapter!!.notifyDataSetChanged()
                                                }
                                            },
                                            isDeleteFromDialog = false,
                                            isDeleteButtonNeeded = true
                                        )
                                        rvFaultImages.adapter = imageAdapter
                                    }
                                }
                            )
                            adapter = faultAdapter
                        }
                    } else {
                        btnAddFault.visibility = View.VISIBLE
                        llLogFaults.visibility = View.GONE
                    }


                    // fault image list
                    faultImageList = if (isFaultUpdate && data.faultList!!.size > 0) {
                        data.faultList!![faultPosition].faultImages as ArrayList<ImagesModel>
                    } else {
                        ArrayList()
                    }
                    imageAdapter = ImageAdapter(
                        context,
                        faultImageList,
                        // image captured list callback
                        object : ImageAdapter.ItemClickListener {
                            override fun onItemClick(position: Int) {

                            }

                            override fun onItemDeleteClick(position: Int) {
                                faultImageList.removeAt(position)
                                imageAdapter!!.notifyDataSetChanged()
                            }
                        }, isDeleteFromDialog = false,
                        isDeleteButtonNeeded = true
                    )
                    rvFaultImages.adapter = imageAdapter
                }
                else -> {
                    ivBackground.setBackgroundResource(R.drawable.background_lgv_image_circle)
                    ivVehicleIcon.setColorFilter(
                        ContextCompat.getColor(
                            context,
                            R.color.black
                        )
                    )
                    tvText.text = data.description
                    showDefaultLayout()
                }

            }
            ivFaultImage.setOnClickListener {
                CameraFragment.setListener(object :
                    CameraFragment.Companion.ItemListenerFromActivity {
                    override fun getImageList(imageList: ArrayList<ImagesModel>) {
                        if (imageAdapter != null) {
                            faultImageList.clear()
                            faultImageList.addAll(imageList)
                            imageAdapter!!.notifyDataSetChanged()
                        }
                    }
                })
                itemClickListener.onItemFaultImagesClick(
                    position,
                    faultImageList
                )
            }

            btnPass.setOnClickListener {
                itemClickListener.onItemPassClick(position)
            }

            btnFail.setOnClickListener {
                itemClickListener.onItemFailClick(position)
            }

            btnAddFault.setOnClickListener {
                llFailPass.visibility = View.GONE
                llFail.visibility = View.VISIBLE
            }

            ivClose.setOnClickListener {
                etFaultNotes.setText("")
                if (data.faultList!!.size > 0) {
                    notifyItemChanged(position)
                } else {
                    itemClickListener.onItemCloseClick(position)
                }
            }

            btnLogFault.setOnClickListener {
                val fault = Fault(
                    faultyComponentSelected!!,
                    faultSelected!!,
                    prioritySelected!!,
                    etFaultNotes.text.toString(),
                    faultImageList
                )
                if (isFaultUpdate) {
                    itemList[position].faultList!![faultPosition] = fault
                } else {
                    itemList[position].faultList!!.add(fault)
                }
                etFaultNotes.setText("")
                notifyItemChanged(position)
            }

            btnAddAnotherFault.setOnClickListener {
                btnAddFault.performClick()
            }

            btnContinue.setOnClickListener {
                itemClickListener.onItemContinueClick(position)
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
            spnFault.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    faultSelected = parent?.getItemAtPosition(position).toString()

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
            spnPriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    prioritySelected = parent?.getItemAtPosition(position).toString()
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
        fun onItemPassClick(position: Int)
        fun onItemFailClick(position: Int)
        fun onItemCloseClick(position: Int)
        fun onItemContinueClick(position: Int)
        fun onItemFaultImagesClick(position: Int, imageList: ArrayList<ImagesModel>)
    }

}