package com.bjs.pdanative.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.BottomSheetHomeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class HomeBottomSheetMenuFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            rlDevice.setOnClickListener {
                if (llDeviceTools.isVisible) {
                    llDeviceTools.visibility = View.GONE
                    ivDevice.setImageResource(R.drawable.ic_keyboard_down)
                } else {
                    llDeviceTools.visibility = View.VISIBLE
                    ivDevice.setImageResource(R.drawable.ic_arrow_up)
                }
            }

            rlOther.setOnClickListener {
                if (llOther.isVisible) {
                    llOther.visibility = View.GONE
                    ivOther.setImageResource(R.drawable.ic_keyboard_down)
                } else {
                    llOther.visibility = View.VISIBLE
                    ivOther.setImageResource(R.drawable.ic_arrow_up)
                }
            }

            rlSlider.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        //Get the bottom_sheet of the system
        val view: View = dialog?.findViewById(R.id.design_bottom_sheet)!!
        //Set the view height
        view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        //Get behavior
        val behavior = BottomSheetBehavior.from(view)
        //Set the pop-up height
        behavior.peekHeight = 3000
        //Set the expanded state
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        // setting the bottom sheet callback for interacting with state changes and sliding
        behavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(view: View, i: Int) {
                // do something when state changes
            }

            override fun onSlide(view: View, v: Float) {
                // do something when slide happens
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}