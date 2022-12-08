package com.core.data.local.callLogs.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

@Parcelize
@Entity(
    primaryKeys = ["number", "dayAndYear"]
)
data class CallLogsEntity(
    var number: String = "",
    var count: Int? = 0,
    var dayAndYear: String = "",

    var carrierReference: String? = "",

    var postcode: String? = "",

    var contactHome: String? = "",

    var contactWork: String? = "",

    var dropNumber: Int? = 500,

    var recipient: String? = "",

    var orderNumber: String? = "",


    var type: String? = null,

    var date: Long? = null,

    var duration: String? = null,

    var callDayTime: String? = null,

    var typeOfContact: String? = ""


    ) : Parcelable
