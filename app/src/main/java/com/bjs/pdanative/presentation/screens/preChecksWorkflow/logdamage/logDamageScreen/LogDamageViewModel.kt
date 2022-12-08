package com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.bjs.pdanative.domain.models.camera.ImageMetaData
import com.bjs.pdanative.domain.models.logDamage.LogDamageFaultModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class LogDamageViewModel : ViewModel() {

    var state by mutableStateOf(LogDamageState())
        private set
    private val _uIEvent = Channel<LogDamageScreenEvents>()
    val uiEvent = _uIEvent.receiveAsFlow()


    fun onStateChange(event: LogDamageScreenEvents) {
        when (event) {

            /*Log Damage Form Events */
            is LogDamageScreenEvents.OnDamagedComponentSelected -> {
                state = state.copy(
                    logDamageFaultModel = state.logDamageFaultModel.copy(
                        damagedComponent = event.damagedComponent
                    )
                )
            }

            is LogDamageScreenEvents.OnPrioritySelected -> {
                state = state.copy(
                    logDamageFaultModel = state.logDamageFaultModel.copy(
                        priority = event.priority
                    )
                )
            }

            is LogDamageScreenEvents.OnAdditionalInfoChange -> {
                state = state.copy(
                    logDamageFaultModel = state.logDamageFaultModel.copy(
                        additionalNotes = event.additionalInfo
                    )
                )
            }

            is LogDamageScreenEvents.OnImageCaptureButtonClick -> {
                state = state.copy(
                    openCamera = event.isCameraOpen,
                )

            }

            is LogDamageScreenEvents.OnCancelButtonClick -> {
                state = state.copy(
                    exitConfirmation = true,
                    updateItemPosition = event.position
                )
            }

            is LogDamageScreenEvents.ExitConfirmationScreenButtonClick -> {
                state = state.copy(
                    updateItemPosition = -1,
                    isItemDelete = false,
                ).also {
                    if (it.faultList.isNotEmpty()) {
                        it.openFaultFormScreen = false
                        it.logDamageFaultModel = LogDamageFaultModel()
                        it.exitConfirmation = false
                    } else {
                        // exit from screen
                        it.isExitFromScreen=true
                    }
                }
            }

            is LogDamageScreenEvents.OnNextButtonClick -> {
                val logDamageFaultModel1 = state.logDamageFaultModel
                state = state.copy(
                    faultList = state.faultList.toMutableStateList().apply {
                        if (state.updateItemPosition != -1) {
                            set(state.updateItemPosition, logDamageFaultModel1)
                        } else {
                            add(logDamageFaultModel1)
                        }
                    },
                    logDamageFaultModel = LogDamageFaultModel(),
                    openFaultFormScreen = false,
                    updateItemPosition = -1,
                    isItemDelete = false
                )

            }
            /*--------------------------------*/


            /* Camera screen result events */
            is LogDamageScreenEvents.OnImageCapturingComplete -> {
                val list = event.imageList as ArrayList<ImageMetaData>
                state = state.copy(
                    logDamageFaultModel = state.logDamageFaultModel.copy(
                        faultImages = list
                    )
                )
            }
            /*--------------------------------*/


            /* Log damage list screen events*/
            is LogDamageScreenEvents.OnEditItemButtonClick -> {
                state = state.copy(
                    openFaultFormScreen = true,
                    logDamageFaultModel = state.faultList[event.position],
                    updateItemPosition = event.position,
                    isItemDelete = false
                )
            }

            is LogDamageScreenEvents.OnAddMoreButtonClick -> {
                state = state.copy(
                    openFaultFormScreen = true,
                    isItemDelete = false,
                    exitConfirmation = false,
                    updateItemPosition = -1
                )
            }

            is LogDamageScreenEvents.OnSaveClick->{
                // save faults in room db and pop the screen


            }

            is LogDamageScreenEvents.DeleteConfirmationClick -> {
                state = state.copy(
                    isItemDelete = true,
                    updateItemPosition = event.position
                )
            }

            is LogDamageScreenEvents.CancelDeleteConfirmationClick -> {
                state = state.copy(
                    isItemDelete = false,
                    exitConfirmation = false,
                    updateItemPosition = event.position
                )
            }

            is LogDamageScreenEvents.OnDeleteItemButtonClick -> {
                // delete and update states
                state = state.copy(
                    faultList = state.faultList.toMutableStateList().apply {
                        removeAt(event.position)
                        state.isItemDelete = false
                    }.also {
                        if (it.size == 0) {
                            state.openFaultFormScreen = true
                            state.updateItemPosition = -1
                        }
                    },
                )
            }
            /*--------------------------------*/


        }
    }
}