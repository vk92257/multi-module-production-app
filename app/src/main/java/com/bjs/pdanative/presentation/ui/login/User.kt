package com.bjs.pdanative.presentation.ui.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var jobTitle: String?,
    var phoneNo: String?,
    var passcode: String?,
    var userImage: String?
) : Parcelable
