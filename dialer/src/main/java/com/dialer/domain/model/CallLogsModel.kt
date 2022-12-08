package com.dialer.domain.model

import com.core.data.local.callLogs.entity.CallLogsEntity
import com.core.data.local.callLogs.entity.ContactHistoryEntity
import com.core.util.ContactsDBConstants.DATE_DAY_MONTH_PATTERN
import com.core.util.Util
import com.core.util.Util.milliSecondsToMinSec
import com.dialer.util.CallType
import com.dialer.util.ContactType
import com.dialer.util.StringConstants
import com.google.gson.Gson

data class CallLogsPojo(
    var name: String = "Cameron Diaz",
    var number: String = "9123456789",
    var date: String = "2020-12-12",
    var time: String = "14:26",
    var type: CallType = CallType.INCOMING,
    var contactType: ContactType = ContactType.HOME,
    var duration: String = "10:50:00",
    var carrierReference: String = "BR0948-947547",
    var postcode: String = "BR1 1AA",
    var dropNumber: String = "1",
    var isOrder: Boolean = true,
    var callHistoryCount: Int = 26,
)


fun String.isCustomEmpty(value: String): String {
    return this.ifEmpty { value }
}

fun CallLogsEntity.toCallLogsPojo(): CallLogsPojo {


    val entity = this
    return CallLogsPojo().apply {
        number = entity.number
        name = entity.recipient ?: "Unknown"
        date = entity.dayAndYear
        time = entity.callDayTime ?: ""
        contactType = when (entity.typeOfContact) {
            StringConstants.Home -> ContactType.HOME
            StringConstants.Work -> ContactType.OFFICE
            StringConstants.Mobile -> ContactType.MOBILE
            else -> {
                ContactType.UNKNOWN
            }
        }
        type = when (entity.type) {
            CallType.CALLING.toString() -> CallType.CALLING
            CallType.MISSED.toString() -> CallType.MISSED
            CallType.ONGOING.toString() -> CallType.ONGOING
            CallType.REJECTED.toString() -> CallType.REJECTED
            CallType.OUTGOING.toString() -> CallType.OUTGOING
            CallType.INCOMING.toString() -> CallType.INCOMING
            else -> CallType.UNKNOWN
        }
        duration = entity.duration ?: ""
        carrierReference = entity.carrierReference ?: ""
        postcode = entity.postcode ?: ""
        dropNumber = entity.dropNumber.toString()
        isOrder = (carrierReference.isNotEmpty())
        callHistoryCount = entity.count ?: 0
    }

}


fun ContactHistoryEntity.toCallLogsPojo(): CallLogsPojo {

    val entity = this
    return CallLogsPojo().apply {
        number = entity.number.toString()
        date = Util.convertDate(entity.date ?: 0, DATE_DAY_MONTH_PATTERN)
        time = entity.callDayTime ?: ""

        type = when (entity.type) {
            CallType.CALLING.toString() -> CallType.CALLING
            CallType.MISSED.toString() -> CallType.MISSED
            CallType.ONGOING.toString() -> CallType.ONGOING
            CallType.REJECTED.toString() -> CallType.REJECTED
            CallType.OUTGOING.toString() -> CallType.OUTGOING
            CallType.INCOMING.toString() -> CallType.INCOMING
            else -> CallType.UNKNOWN
        }
        duration = milliSecondsToMinSec((entity.duration ?: "").toLong())
        isOrder = (carrierReference.isNotEmpty())
        callHistoryCount = entity.count ?: 0
    }

}


fun CallLogsPojo.toJsonString(): String {
    return Gson().toJson(this)
}

fun CallLogsPojo.jsonToCallPojo(callPojoJson: String): CallLogsPojo {
    return Gson().fromJson(callPojoJson, CallLogsPojo::class.java)
}



