package com.bjs.pdanative.presentation.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bjs.pdanative.R

open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showOneButtonErrorOrSuccessDialog(
        onFinish: (t: Boolean) -> Unit,
        title: String,
        description: String,
        isError: Boolean,
        btnText: String,
    ) {
        val fullscreenDialog =
            Dialog(requireActivity(), R.style.ThemeOverlay_AppCompat_Dialog_Alert)
        fullscreenDialog.setContentView(R.layout.dialog_error)
        fullscreenDialog.setCanceledOnTouchOutside(false)
        fullscreenDialog.setCancelable(false)
        val btnFinish = fullscreenDialog.findViewById<Button>(R.id.btn_finish)
        val btnReset = fullscreenDialog.findViewById<Button>(R.id.btn_reset_pin)
        val tvErrorTitle = fullscreenDialog.findViewById<TextView>(R.id.tv_title)
        val tvDesc = fullscreenDialog.findViewById<TextView>(R.id.tv_desc)
        val ivError = fullscreenDialog.findViewById<ImageView>(R.id.iv_error)
        tvDesc.text = description
        tvErrorTitle.text = title
        btnReset.visibility = View.GONE
        btnFinish.text = btnText
        if (isError) {
            ivError.setImageResource(R.drawable.ic_error)
            ivError.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
            tvErrorTitle.setTextColor(requireActivity().resources.getColor(R.color.red))
        } else {
            ivError.setImageResource(R.drawable.ic_tick)
            ivError.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            tvErrorTitle.setTextColor(requireActivity().resources.getColor(R.color.green))
        }
        btnFinish.setOnClickListener {
            onFinish.invoke(true)
            fullscreenDialog.dismiss()
        }
        fullscreenDialog.show()
    }


    fun showTwoButtonErrorOrSuccessDialog(
        onFinish: (t: Boolean) -> Unit,
        title: String,
        errorMessage: String,
        isError: Boolean,
        btnSuccessText: String,
        btnErrorText: String,
    ) {
        val fullscreenDialog =
            Dialog(requireActivity(), R.style.ThemeOverlay_AppCompat_Dialog_Alert)
        fullscreenDialog.setContentView(R.layout.dialog_error)
        fullscreenDialog.setCanceledOnTouchOutside(false)
        fullscreenDialog.setCancelable(false)
        val btnFinish = fullscreenDialog.findViewById<Button>(R.id.btn_finish)
        val btnReset = fullscreenDialog.findViewById<Button>(R.id.btn_reset_pin)
        val tvErrorTitle = fullscreenDialog.findViewById<TextView>(R.id.tv_title)
        val tvDesc = fullscreenDialog.findViewById<TextView>(R.id.tv_desc)
        val ivError = fullscreenDialog.findViewById<ImageView>(R.id.iv_error)
        tvDesc.text = errorMessage
        tvErrorTitle.text = title
        if (isError) {
            btnReset.visibility = View.VISIBLE
            ivError.setImageResource(R.drawable.ic_error)
            ivError.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
            tvErrorTitle.setTextColor(requireActivity().resources.getColor(R.color.red))
        } else {
            btnReset.visibility = View.GONE
            ivError.setImageResource(R.drawable.ic_tick)
            ivError.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            tvErrorTitle.setTextColor(requireActivity().resources.getColor(R.color.green))
        }
        btnFinish.setOnClickListener {
            onFinish.invoke(true)
            fullscreenDialog.dismiss()
        }
        fullscreenDialog.show()
    }

}

