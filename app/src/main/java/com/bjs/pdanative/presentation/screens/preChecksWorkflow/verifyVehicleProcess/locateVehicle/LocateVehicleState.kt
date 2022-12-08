package com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.locateVehicle

import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.locateVehicle.component.MapStyle
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties

data class LocateVehicleState(
    val properties: MapProperties = MapProperties(
        mapStyleOptions = MapStyleOptions(MapStyle.json)
    ),
    val isFalloutMap: Boolean = true,
    var currentLatitude: Double = 30.748955,
    var currentLongitude: Double = 76.8496128,
    var destinationLatitude: Double = 30.748955,
    var destinationLongitude: Double = 76.8496128,
    var vanLocation: String = "Main Warehouse",
    var distance: String = "< 1km",
    var directionDetailList: ArrayList<DirectionDetail> = arrayListOf(),
)