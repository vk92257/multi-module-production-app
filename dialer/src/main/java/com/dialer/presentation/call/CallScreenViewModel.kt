package com.dialer.presentation.call

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dialer.domain.model.CallPojo
import com.dialer.domain.model.jsonToCallPojo
import com.dialer.domain.model.swapCall
import com.dialer.domain.model.toCallPojo
import com.dialer.util.CallType
import com.dialer.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CallScreenViewModel : ViewModel() {
    var state by mutableStateOf(CallPojo())
        private set


    private val _UiEvents = Channel<UiEvent>()
    var uiEvent = _UiEvents.receiveAsFlow()


    fun onCallEvent(events: CallScreenEvents) {
        when (events) {
            is CallScreenEvents.OnIncomingCall -> {
                state = events.callInfo.toCallPojo(state, events.number)

                state = state.copy(
                    type = CallType.INCOMING,
                    isIncomingCall = true,
                )
            }

            is CallScreenEvents.OnCallAccept -> {
                state = state.copy(
                    type = CallType.ONGOING,
                    isIncomingCall = false,
                    showHoldView = (state.secondCall != null)
                )
            }

            is CallScreenEvents.OnCallEnded -> {
                /* if two call are running simultaneously */
                if (state.showHoldView && state.secondCall != null) {
                    /* if the end call is requested for the second call */
                    if (state.jsonToCallPojo(state.secondCall!!).number == events.currentCallNumber) {
                        state = state.copy(
                            secondCall = null,
                            showHoldView = false,
                        )
                    } else {
                        /*first call is requested the end call option we have to swap the call */
                        state = state.swapCall(
                            null, state.jsonToCallPojo(state.secondCall!!)
                        )
                        state = state.copy(
                            showHoldView = false
                        )
                    }
                } else {
                    /* one call is running we can destroy the activity*/
                    if (events.currentCallNumber == state.number) viewModelScope.launch {
                        _UiEvents.send(UiEvent.OnCallEnded)
                    }
                }
            }

            is CallScreenEvents.OnMuteTap -> {
                state = state.copy(
                    isMute = events.isMute
                )
            }

            is CallScreenEvents.OnSpeakerTap -> {
                state = state.copy(
                    isOnSpeaker = events.isOnSpeaker
                )
            }

            is CallScreenEvents.OnTimeChange -> {
                if (state.number == events.number) state = state.copy(
                    time = events.time
                ) else state = state.copy(
                    time = " "
                )
            }

            is CallScreenEvents.SecondIncomingCall -> {
                val newState = events.entity.toCallPojo(state, events.number)
                state = state.swapCall(state, newState)
                state = state.copy(
                    isIncomingCall = true
                )

            }
            is CallScreenEvents.AfterSwapCalls -> {
                state = state.secondCall?.let { state.swapCall(state, state.jsonToCallPojo(it)) }!!
            }


            is CallScreenEvents.OnHoldCallStateChange -> {
                state = state.copy(
                    isHoldPressed = events.isHold
                )
            }
        }
    }


    /***    [updateCallScreenState] this method is important for Updating the call Scree with current call state
     * This method will be called whenever the call screen is closed and opened by taping on the call notification */

    fun updateCallScreenState(callScreenState: CallPojo?) {
        if (callScreenState != null) {
            state = callScreenState
            state = state.copy(
                name = callScreenState.name
            )
        }
    }


}