package com.dialer.presentation.call

import com.core.data.local.contacts.entity.ContactEntity

sealed class CallScreenEvents {
    data class OnIncomingCall(val callInfo: ContactEntity, val number: String) : CallScreenEvents()
    object OnCallAccept : CallScreenEvents()
    data class OnCallEnded(val currentCallNumber: String) : CallScreenEvents()
    data class OnMuteTap(val isMute: Boolean) : CallScreenEvents()
    data class OnSpeakerTap(val isOnSpeaker: Boolean) : CallScreenEvents()
    data class OnTimeChange(val time: String, val number: String) : CallScreenEvents()
    data class SecondIncomingCall(val entity: ContactEntity, val number: String) :
        CallScreenEvents()

    data class AfterSwapCalls(val activeCallNumber: String) : CallScreenEvents()
    data class OnHoldCallStateChange(val isHold: Boolean, val number: String) : CallScreenEvents()

}