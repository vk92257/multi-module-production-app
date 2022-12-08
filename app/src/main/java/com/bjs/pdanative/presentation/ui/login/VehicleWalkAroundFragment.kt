package com.bjs.pdanative.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentVehicleWalkaroundBinding
import com.bjs.pdanative.presentation.ui.base.BaseFragment
import com.bjs.pdanative.presentation.ui.camera.CameraViewModel
import com.bjs.pdanative.presentation.ui.camera.ImageAdapter
import com.bjs.pdanative.presentation.ui.camera.ImagesModel

/**
 * Created by deepak on {01/06/21}
 */
class VehicleWalkAroundFragment : BaseFragment(), ImageAdapter.ItemClickListener {
    private var _binding: FragmentVehicleWalkaroundBinding? = null
    private val binding get() = _binding!!
    private val cameraViewModel: CameraViewModel by activityViewModels()
    private lateinit var imageList: ArrayList<ImagesModel>
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehicleWalkaroundBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageList = cameraViewModel.capturedImageList.value ?: ArrayList()
        imageAdapter = ImageAdapter(
            requireContext(),
            imageList,
            this,
            isDeleteFromDialog = false,
            isDeleteButtonNeeded = true
        )

        binding.apply {
            fabBack.setOnClickListener { findNavController().navigateUp() }
            rlVehicleBarcode.setOnClickListener {
                cameraViewModel.setRequiredPhotos(1)
                findNavController().navigate(R.id.action_vehicleWalkAroundFragment_to_cameraFragment)
            }
            rvImages.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = imageAdapter
            }

            fabNext.setOnClickListener {
                findNavController().navigate(R.id.action_vehicleWalkAroundFragment_to_preRouteCheckFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
    }

    override fun onItemDeleteClick(position: Int) {
        imageList.removeAt(position)
        imageAdapter.notifyDataSetChanged()
    }

}