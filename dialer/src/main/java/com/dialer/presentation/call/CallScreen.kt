package com.dialer.presentation.call

import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.telecom.CallAudioState
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.core.data.local.contacts.entity.ContactEntity
import com.dialer.R
import com.dialer.data.call_service.CallServiceHelper
import com.dialer.domain.model.CallPojo
import com.dialer.domain.repository.CallDetailsEvents
import com.dialer.presentation.contact.BottomSheet
import com.dialer.util.UiEvent
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun CallScreen(
    modifier: Modifier = Modifier,
    viewModel: CallScreenViewModel = hiltViewModel(),
    onCallEnded: () -> Unit = {},
    callScreenState: CallPojo?,
    navConroller: NavHostController = rememberNavController(),
) {

    val context = LocalContext.current
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager/*by remember {

        mutableStateOf(context.getSystemService(Context.AUDIO_SERVICE) as AudioManager)
    }*/
    val dialerUserAction = CallServiceHelper.provideDialerUserAction()

    audioManager.mode = AudioManager.MODE_IN_CALL

    LaunchedEffect(key1 = Unit) {
        viewModel.updateCallScreenState(callScreenState)
        viewModel.uiEvent.collectLatest {
            when (it) {
                is UiEvent.OnCallEnded -> {
                    onCallEnded()
                }
                is UiEvent.ShowSnackBar -> {

                }
            }
        }
    }

    BottomSheet(content = { paddingValues, openCloseBottomSheet ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    color = colorResource(id = R.color.veryDarkGray)
                )
        ) {
            CallCallerInfo(
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp, vertical = 40.dp
                    )
                    .align(
                        alignment = Alignment.TopCenter
                    ), callPojo = viewModel.state
            )


            if (viewModel.state.isIncomingCall) {
                CallSlider(
                    modifier = Modifier
                        .padding(
                            horizontal = 20.dp, vertical = 130.dp
                        )
                        .align(
                            alignment = Alignment.BottomCenter
                        ),
                    onSlideLeft = {
                        dialerUserAction.rejectCall(viewModel.state.number)
                    },
                    onSlideRight = {
                        dialerUserAction.acceptCall(viewModel.state.number)
                    },
                )
            } else {
                CallControlButtons(modifier = Modifier
                    .padding(
                        horizontal = 20.dp, vertical = 130.dp
                    )
                    .align(
                        alignment = Alignment.BottomCenter
                    ), callPojo = viewModel.state, onAddCallClick = {
                    navConroller.navigate("Dialer_screen")
                }, callEndClick = {
                    dialerUserAction.rejectCall(viewModel.state.number)
                }, onHoldClick = {
                    dialerUserAction.holdCall(viewModel.state.number)
                }, onMuteClick = {
                    if (viewModel.state.isMute) {
                        audioManager.isMicrophoneMute = false
                        viewModel.onCallEvent(CallScreenEvents.OnMuteTap(false))
                    } else {
                        audioManager.isMicrophoneMute = true
                        viewModel.onCallEvent(CallScreenEvents.OnMuteTap(true))
                    }

                }, onNumPadClick = {
                    openCloseBottomSheet()
                                   }, onSpeakerClick = {
                    /* audioManager.mode = AudioManager.MODE_IN_CALL*/
                    val isSpeakerOn = audioManager.isSpeakerphoneOn
                    val earPiece = CallAudioState.ROUTE_WIRED_OR_EARPIECE
                    val speaker = CallAudioState.ROUTE_SPEAKER
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                        dialerUserAction.changeSpeakerPhoneState(if (isSpeakerOn) earPiece else speaker)
                    } else {
                        audioManager.isSpeakerphoneOn = !isSpeakerOn
                    }
                    viewModel.onCallEvent(CallScreenEvents.OnSpeakerTap(!isSpeakerOn))
                },

                    onSwapCallClick = {
                        dialerUserAction.swapCall(viewModel.state.number)
                    })
            }



            if (viewModel.state.showHoldView && viewModel.state.secondCall != null) viewModel.state.secondCall?.let { secondCall ->
                HoldView(modifier = Modifier
                    .align(
                        alignment = Alignment.BottomCenter
                    )
                    .clickable {
                        dialerUserAction.swapCall(viewModel.state.number)
                    },
                    callPojo = Gson().fromJson(secondCall, CallPojo::class.java),
                    onDisconnectClick = {
                        dialerUserAction.rejectCall(it)
                    })
            }
        }
    })


    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val callDetailsEvents = object : CallDetailsEvents {
        override fun onIncomingCall(contactEntity: ContactEntity, number: String) {
            viewModel.onCallEvent(CallScreenEvents.OnIncomingCall(contactEntity, number))
        }

        override fun onOutgoingCall() {

        }

        override fun secondIncomingCall(entity: ContactEntity, number: String) {
            viewModel.onCallEvent(CallScreenEvents.SecondIncomingCall(entity, number))
        }

        override fun heldCallStatusChange(isHold: Boolean, number: String) {
            viewModel.onCallEvent(CallScreenEvents.OnHoldCallStateChange(isHold, number))
        }

        override fun onCallAccepted() {
            viewModel.onCallEvent(CallScreenEvents.OnCallAccept)
        }

        override fun onCallRejected() {
        }

        override fun onCallHold() {
        }

        override fun currentCallInfo() {
        }

        override fun onSwapHoldCall(activeCallNumber: String) {
            viewModel.onCallEvent(CallScreenEvents.AfterSwapCalls(activeCallNumber))
        }


        override fun onCallTimeChange(time: String, number: String) {
            viewModel.onCallEvent(CallScreenEvents.OnTimeChange(time, number))
        }

        override fun onCallEnded(value: Boolean, number: String) {
            if (value) {
                viewModel.onCallEvent(CallScreenEvents.OnCallEnded(number))
            } else {

            }
        }

    }

// If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
// Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {

                }
                Lifecycle.Event.ON_START -> {
                }
                Lifecycle.Event.ON_RESUME -> {
                    CallServiceHelper.registerCallDetailsEventsListener(callDetailsEvents)

                }
                Lifecycle.Event.ON_PAUSE -> {
                    CallServiceHelper.unregisterCallDetailsEventsListener()
                }
                Lifecycle.Event.ON_STOP -> {
                }
                Lifecycle.Event.ON_DESTROY -> {
                    CallServiceHelper.unregisterCallDetailsEventsListener()
                }
                else -> {
                }
            }
        }

// Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

// When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


}




