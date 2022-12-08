package com.bjs.pdanative.domain.models.notification

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @Author: Vivek
 * @Date: 10/03/22
 */

@Parcelize
data class NotificationData(
    val message : String = "",
    val index : String = "",
val time : String = "",
) : Parcelable