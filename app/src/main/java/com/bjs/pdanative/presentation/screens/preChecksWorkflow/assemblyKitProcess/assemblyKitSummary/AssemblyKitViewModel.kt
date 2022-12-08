package com.bjs.pdanative.presentation.screens.preChecksWorkflow.assemblyKitProcess.assemblyKitSummary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bjs.pdanative.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class AssemblyKitViewModel() : ViewModel() {
    var state by mutableStateOf(AssemblyKitState())
        private set

    val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onStateChanged(event: AssemblyKitEvents) {
        when (event) {


            is AssemblyKitEvents.BackButtonClick -> {

            }
            is AssemblyKitEvents.EditButtonClick -> {

            }
            is AssemblyKitEvents.TapToRescanClick -> {

            }
            is AssemblyKitEvents.OpenCameraClick -> {
                state = state.copy(
                    openCamera = event.openCamera
                )
            }
            is AssemblyKitEvents.SaveAndContinueClick -> {

            }
            is AssemblyKitEvents.AfterTakingPicture -> {
                state = state.copy(
                    imageList = event.imageList
                )
            }


        }
    }


}