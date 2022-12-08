package com.core.data.local.callLogs.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

@Parcelize
@Entity
data class ContactHistoryEntity(
    var name: String? = null,
    var id: Long? = null,
    var number: Long? = null,
    var type: String? = null,
    @PrimaryKey(autoGenerate = false) var date: Long? = null,
    var duration: String? = null,
    var callDayTime: String? = null,
    var count: Int? = 0,
) : Parcelable

