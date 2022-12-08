package com.core.data.local.contacts.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class ContactEntity(

    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var number: String = "",
    var type: String? = null,
    var date: String? = null,
    var duration: String? = null,
    var callDayTime: String? = null,
    var carrierReference: String? = "",
    var postcode: String? = "",
    var isOrder: Boolean = false,
    var contactHome: String? = "",
    var contactWork: String? = "",
    var dropNumber: Int? = 500,
    var time: String? = "",
    var name: String? = "",
    var orderNumber: String? = "",
    var callerImage: String? = ""


) : Parcelable {
    fun copy(): ContactEntity {
        return ContactEntity(
            id,
            number,
            type,
            date,
            duration,
            callDayTime,
            carrierReference,
            postcode,
            isOrder,
            contactHome,
            contactWork,
            dropNumber,
            name,
            orderNumber
        )
    }
}