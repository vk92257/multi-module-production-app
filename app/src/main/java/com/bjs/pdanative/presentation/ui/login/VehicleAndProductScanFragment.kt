package com.bjs.pdanative.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentVehicleProductBinding
import com.bjs.pdanative.presentation.ui.barcodescanner.BarcodeScanningViewModel
import com.bjs.pdanative.presentation.ui.base.BaseFragment

/**
 * Created by deepak on {01/06/21}
 */
class VehicleAndProductScanFragment : BaseFragment() {
    private var _binding: FragmentVehicleProductBinding? = null
    private val binding get() = _binding!!
    private val barcodeScanningViewModel: BarcodeScanningViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehicleProductBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.apply {
            fabNext.setOnClickListener {
                findNavController().navigate(R.id.action_vehicleAndProductScanFragment_to_vehicleWalkAroundFragment)
            }

            rlItemBarcode.setOnClickListener {
                barcodeScanningViewModel.setBarcodeScan(BarcodeScanningViewModel.BarCodeScanType.PRODUCT)
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_vehicleAndProductScanFragment_to_barcodeScanningFragment)
            }

            rlVehicleBarcode.setOnClickListener {
                barcodeScanningViewModel.setBarcodeScan(BarcodeScanningViewModel.BarCodeScanType.VEHICLE)
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_vehicleAndProductScanFragment_to_barcodeScanningFragment)
            }

            fabBack.setOnClickListener {
                barcodeScanningViewModel.setVehicleBarCodeScanValue(null)
                barcodeScanningViewModel.setProductBarCodeScanValue(null)
            }
        }

        barcodeScanningViewModel.vehicleBarCodeScanValue.observe(viewLifecycleOwner,
            {
                if (it != null) {
                    binding.rlVehicleBarcode.visibility = View.GONE
                    binding.rlVehicleVerified.visibility = View.VISIBLE
                } else {
                    binding.rlVehicleBarcode.visibility = View.VISIBLE
                    binding.rlVehicleVerified.visibility = View.GONE
                }
            })

        barcodeScanningViewModel.productBarCodeScanValue.observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                binding.rlItemBarcode.visibility = View.GONE
                   binding.rlItemVerified.visibility = View.VISIBLE
                } else {
                    binding.rlItemBarcode.visibility = View.VISIBLE
                    binding.rlItemVerified.visibility = View.GONE
                }
            })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                binding.fabBack.performClick()
            }
        }


}