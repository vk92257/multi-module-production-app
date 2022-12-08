package com.bjs.pdanative.domain.models.camera

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @Author: Vivek
 * @Date: 23/05/22
 */

@Parcelize
data class ImageMetaData(
    var imageUrl: String  = "",
    var timeStamp: String = "",
    var date: String = "",
    var time: String = ""
): Parcelable
