package com.dialer.domain.repository

import com.core.data.local.contacts.entity.ContactEntity

interface CallDetailsEvents {
    fun onIncomingCall(contactEntity: ContactEntity, number: String)
    fun onOutgoingCall()
    fun onCallAccepted()
    fun onCallRejected()
    fun onCallHold()
    fun currentCallInfo()
    fun onSwapHoldCall(activeCallNumber: String)
    fun onCallEnded(value: Boolean, number: String)
    fun onCallTimeChange(time: String, number: String)
    fun secondIncomingCall(entity: ContactEntity, number: String)
    fun heldCallStatusChange(isHold: Boolean, number: String)
}