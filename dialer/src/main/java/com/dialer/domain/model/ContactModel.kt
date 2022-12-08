package com.dialer.domain.model

import com.core.data.local.contacts.entity.ContactEntity
import com.dialer.util.CallType
import com.dialer.util.ContactType

data class ContactPojo(
    var name: String = "Cameron Diaz",
    var number: String = "9123456789",
    var contactHome: String = "9123456789",
    var contactPhone: String = "9123456789",
    var contactWork: String = "9123456789",
    var date: String = "2020-12-12 ",
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


fun ContactPojo.toContactEntity(): ContactEntity {
    return ContactEntity(

    )
}


fun ContactEntity.toContactPojo(): ContactPojo {
    val contactEntity = this
    return ContactPojo().apply { ->
        name = contactEntity.name ?: ""
        contactHome = contactEntity.contactHome ?: ""
        contactWork = contactEntity.contactWork ?: ""
        contactHome = contactEntity.contactHome ?: ""
        number = contactEntity.number
        date = contactEntity.callDayTime ?: ""
        time = contactEntity.time ?: ""
        duration = contactEntity.duration ?: ""
        carrierReference = contactEntity.carrierReference ?: ""
        postcode = contactEntity.postcode ?: ""
        dropNumber = " ${contactEntity.dropNumber ?: 500}"
        isOrder = contactEntity.isOrder

    }
}
