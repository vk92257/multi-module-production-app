package com.bjs.pdanative.presentation.screens.common.camera

import com.bjs.pdanative.domain.models.camera.ImageMetaData
import java.io.File

/**
 * @Author: Vivek
 * @Date: 11/04/22
 */
sealed class CameraUIAction {
    object OnCameraClick : CameraUIAction()
    data class AfterPhotoTaken(val photoUri: String, val timeStamp: String) : CameraUIAction()
    data class OnListImageClick(val index: Int) : CameraUIAction()
    object OnSwitchCameraClick : CameraUIAction()
    data class OnImageItemClick(val position: Int) : CameraUIAction()
    data class OnImageItemDeleteClick(val position: Int) : CameraUIAction()
    object OnDoneClick : CameraUIAction()
    data class OnRetryClick(val position: Int) : CameraUIAction()
    object OnConfirmClick : CameraUIAction()
    object ImageCapturingComplete : CameraUIAction()
    data class OnCompletingImageCapturing(val listOfImages: ArrayList<ImageMetaData>) :
        CameraUIAction()

    object OnDeleteCancelClick : CameraUIAction()
    data class OnDeleteConfirmClick(val position: Int) : CameraUIAction()


}
