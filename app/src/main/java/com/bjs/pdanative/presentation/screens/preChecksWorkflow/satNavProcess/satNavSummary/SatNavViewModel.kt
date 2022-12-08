package com.bjs.pdanative.presentation.screens.preChecksWorkflow.satNavProcess.satNavSummary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bjs.pdanative.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class SatNavViewModel() : ViewModel() {
    var state by mutableStateOf(SatNavState())
        private set

    val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onStateChanged(event: SatNavEvents) {
        when (event) {


            is SatNavEvents.BackButtonClick -> {

            }
            is SatNavEvents.EditButtonClick -> {

            }
            is SatNavEvents.TapToRescanClick -> {

            }
            is SatNavEvents.OpenCameraClick -> {
                state = state.copy(
                    openCamera = event.openCamera
                )
            }
            is SatNavEvents.SaveAndContinueClick -> {

            }
            is SatNavEvents.AfterTakingNavPicture -> {
                state = state.copy(
                    imageList = event.imageList
                )
            }


        }
    }


}