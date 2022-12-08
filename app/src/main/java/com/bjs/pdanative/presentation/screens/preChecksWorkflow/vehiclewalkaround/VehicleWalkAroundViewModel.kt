package com.bjs.pdanative.presentation.screens.preChecksWorkflow.vehiclewalkaround

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bjs.pdanative.domain.models.camera.ImageMetaData
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class VehicleWalkAroundViewModel : ViewModel() {
    var state by mutableStateOf(VehicleWalkAroundState())
        private set
    var _UiEvent = Channel<UiEvent>()
    var uiEvent = _UiEvent.receiveAsFlow()

    fun onStateChange(event: VehicleWalkAroundEvent) {
        when (event) {
            is VehicleWalkAroundEvent.OpenCamera -> {
                state = state.copy(isCameraOpen = event.isCameraOpen)
            }

            is VehicleWalkAroundEvent.OnCancelButtonClick -> {}
            is VehicleWalkAroundEvent.OnImageCapturingCompleted -> {
                state = state.copy(
                    isCameraOpen = false,
                    images = event.images
                )
                viewModelScope.launch {
                    _UiEvent.send(
                        UiEvent.Navigate(Route.VehicleWalkAroundSuccessful)
                    )
                }
            }
            is VehicleWalkAroundEvent.OpenLogDamage -> {
                viewModelScope.launch {
                    _UiEvent.send(
                        UiEvent.Navigate(Route.LogDamage)
                    )
                }
            }
        }
    }

}


data class VehicleWalkAroundState(
    var isCameraOpen: Boolean = false,
    var openLogDamage: Boolean = false,
    var images: ArrayList<ImageMetaData> = arrayListOf(),
)

sealed class VehicleWalkAroundEvent {
    data class OpenCamera(var isCameraOpen: Boolean) : VehicleWalkAroundEvent()
    object OnCancelButtonClick : VehicleWalkAroundEvent()
    data class OnImageCapturingCompleted(var images: ArrayList<ImageMetaData>) :
        VehicleWalkAroundEvent()

    object OpenLogDamage : VehicleWalkAroundEvent()
}