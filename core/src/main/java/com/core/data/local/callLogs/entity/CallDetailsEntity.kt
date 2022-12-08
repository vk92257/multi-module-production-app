package com.core.data.local.callLogs.entity

import android.os.Parcelable
import com.core.util.ContactsDBConstants.DATE_PATTERN
import com.core.util.Util
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CallDetailsEntity(
    var carrierReference: String = "",
    var postcode: String = "",
    var contactHome: String = "",
    var contactWork: String = "",
    var dropNumber: Int = 500,
    var recipient: String = "",
    var orderNumber: String = "",
    var name: String = "",
    var number: String = "",
    var type: String = "",
    var date: String = "",
    var duration: String = "",
    var callDayTime: String = "",
    var count: Int = 0,
    var typeOfContact: String = ""
) : Parcelable {
    override fun toString(): String {
        return number + "" + date.toLong().let { Util.convertDate(it, DATE_PATTERN) }
    }
}


