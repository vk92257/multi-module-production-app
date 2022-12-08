package com.dialer.domain.model

import android.os.Parcelable
import com.core.data.local.contacts.entity.ContactEntity
import com.dialer.util.CallType
import com.dialer.util.ContactType
import com.google.gson.Gson


@kotlinx.parcelize.Parcelize
data class CallPojo(
    val name: String = "",
    val number: String = "",
    val date: String = "",
    var time: String = "",
    var type: CallType = CallType.OUTGOING,
    val contactType: ContactType = ContactType.HOME,
    val duration: String = "",
    val carrierReference: String = "",
    val postcode: String = "",
    val dropNumber: String = "",
    val isOrder: Boolean = false,
    var isHoldPressed: Boolean = false,
    val isOnSpeaker: Boolean = false,
    val isMute: Boolean = false,
    var isSecondIncomingCall: Boolean = false,
    val isVideoCall: Boolean = false,
    val isCallEnded: Boolean = false,
    val isCallConnected: Boolean = false,
    val isCallStarted: Boolean = false,
    val isCallRejected: Boolean = false,
    var isIncomingCall: Boolean = true,
    val callerImage: String = "https://www.gstatic.com/webp/gallery/1.jpg",
    var secondCall: String? = null,
    var showHoldView: Boolean = false


) : Parcelable


fun CallPojo.swapCall(currentCall: CallPojo?, newCall: CallPojo): CallPojo {
    newCall.secondCall = currentCall!!.toJsonString()
    return newCall
}


fun CallPojo.checkCallType(contactEntity: ContactEntity, number: String): ContactType {
    return when (number) {
        contactEntity.number -> ContactType.MOBILE
        contactEntity.contactWork -> ContactType.OFFICE
        contactEntity.contactHome -> ContactType.HOME
        else -> {
            ContactType.UNKNOWN
        }
    }
}


fun CallPojo.toJsonString(): String {
    return Gson().toJson(this)
}

fun CallPojo.jsonToCallPojo(callPojoJson: String): CallPojo {
    return Gson().fromJson(callPojoJson, CallPojo::class.java)
}

fun ContactEntity.toCallPojo(callPojo: CallPojo, userNumber: String): CallPojo {
    val item = this
    var dropNumber: String? = null
    var postCode: String? = null
    if (item.dropNumber != 500) dropNumber = item.dropNumber.toString()
    if (item.postcode?.isNotEmpty() == true) postCode = item.postcode

    return callPojo.copy(
        name = (item.name) ?: userNumber,
        number = userNumber,
        contactType = callPojo.checkCallType(item, number),
        carrierReference = item.carrierReference ?: "",
        isOrder = item.isOrder,
        date = item.date ?: "",
        time = item.time ?: "",
        postcode = postCode ?: userNumber,
        duration = item.duration ?: "",
        dropNumber = dropNumber ?: "",

        )

}
