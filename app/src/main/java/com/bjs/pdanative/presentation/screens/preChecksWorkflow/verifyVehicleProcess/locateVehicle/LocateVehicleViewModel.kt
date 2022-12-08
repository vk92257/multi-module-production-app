package com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.locateVehicle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bjs.pdanative.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class LocateVehicleViewModel() : ViewModel() {

    var state by mutableStateOf(LocateVehicleState())
        private set


    init {
        val locateVehicleState = LocateVehicleState()
        val list = state.directionDetailList

        list.add(
            DirectionDetail(
                message = "Walk forwards for 45 metres",
               direction = "top"
            )
        )
        list.add(
            DirectionDetail(
                message = "Walk forwards for 95 metres",
                direction = "right"
            )
        )
        list.add(
            DirectionDetail(
                message = "Walk forwards for 105 metres",
                direction = "left"

            )
        )
        list.add(
            DirectionDetail(
                message = "Walk forwards for 25 metres",
                direction = "down"

            )
        )
        list.add(
            DirectionDetail(
                message = "Walk forwards for 05 metres",
                direction = "left"
            )

        )
        locateVehicleState.also {
            it.directionDetailList = list
        }
        state = locateVehicleState
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onLocateVanClick() {

    }


}