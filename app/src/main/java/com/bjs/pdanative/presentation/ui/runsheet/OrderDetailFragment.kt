package com.bjs.pdanative.presentation.ui.runsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentOrderDetailsBinding
import com.bjs.pdanative.presentation.ui.base.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


/**
 * Created by deepak on {10/08/21}
 */
abstract class OrderDetailFragment : BaseFragment(), OnMapReadyCallback {
    private var mapFragment: ScrollMapFragment? = null
    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as ScrollMapFragment
        mapFragment?.getMapAsync(this)
    }

  /*  override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        mMap.uiSettings.isZoomControlsEnabled = true
        mapFragment?.setListener(object : ScrollMapFragment.OnTouchListener {
            override fun onTouch() {
                binding.nestedScrollview.requestDisallowInterceptTouchEvent(true)
            }
        })

        // dummy lat/lng location
        val chandigarh = LatLng(30.741482, 76.768066)
        mMap.addMarker(
            MarkerOptions()
                .position(chandigarh)
                .title("Marker in chandigarh")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chandigarh, 15F))*/

//    }
}