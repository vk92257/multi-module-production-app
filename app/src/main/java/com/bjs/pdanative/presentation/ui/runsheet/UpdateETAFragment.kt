package com.bjs.pdanative.presentation.ui.runsheet

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bjs.pdanative.databinding.FragmentUpdateEtaDialogBinding
import com.bjs.pdanative.common.AllUtils
import com.bjs.pdanative.presentation.ui.base.BaseFragment
import java.util.*

class UpdateETAFragment : BaseFragment() {
    private var _binding: FragmentUpdateEtaDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var calendar: Calendar
    private var mHour: Int = 0
    private var mMinute: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateEtaDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        mHour = calendar.get(Calendar.HOUR_OF_DAY)
        binding.apply {
            toolbar.btnBack.visibility = View.GONE
            tvTime.text = AllUtils.convertDate(System.currentTimeMillis(), "HH:mm")
            tvTime.setOnClickListener {
                TimePickerDialog(
                    requireContext(),
                    timePickerDialogListener,
                    mHour,
                    mMinute,
                    true
                ).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // Launch Time Picker Dialog
    private val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            mHour = hourOfDay
            mMinute = minute
            binding.tvTime.text = String.format("%02d:%02d", mHour, mMinute)
            calendar.set(Calendar.HOUR, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
        }

}