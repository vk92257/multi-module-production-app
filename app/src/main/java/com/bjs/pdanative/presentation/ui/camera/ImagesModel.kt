package com.bjs.pdanative.presentation.ui.camera

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImagesModel(var imagePath: String, var imageUri: Uri) : Parcelable {
}