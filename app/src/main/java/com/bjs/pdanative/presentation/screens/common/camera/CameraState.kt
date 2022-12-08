package com.bjs.pdanative.presentation.screens.common.camera

import androidx.camera.core.CameraSelector
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bjs.pdanative.domain.models.camera.ImageMetaData

/**
 * @Author: Vivek
 * @Date: 22/04/22
 */
data class CameraState(
    var minImages: Int = Int.MAX_VALUE,
    var currentImage: String = "",
    var imageList: SnapshotStateList<ImageMetaData> = mutableStateListOf(),
    var currentImageIndex: Int = 0,
    var imageCaptured: Boolean = false,
    var lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    var isShowDeleteImage: Boolean = false,
    var imageToDelete: Int = -1,
)
