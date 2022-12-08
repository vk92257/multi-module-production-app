package com.bjs.pdanative.presentation.screens.common.camera

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bjs.pdanative.common.HOUR_MINUTE_SECONDS
import com.bjs.pdanative.common.WEEK_DAY_MONTH_YEAR
import com.bjs.pdanative.domain.models.camera.ImageMetaData
import com.bjs.pdanative.util.getDateAndTime
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * @Author: Vivek
 * @Date: 22/04/22
 */
class CameraViewModel : ViewModel() {
    var state by mutableStateOf(CameraState())
        private set
    private val _cameraUiAction = Channel<CameraUIAction>()
    val cameraUiEvent = _cameraUiAction.receiveAsFlow()


    /**
     * Updating the existing list with the new list that passed in the parameter*/
    fun updateImageList(imageList: ArrayList<ImageMetaData>) {
        state = state.copy(
            imageList = imageList.toMutableStateList(),
            imageCaptured = true,
            currentImageIndex = imageList.lastIndex,
        )
    }

    fun updateImageMinCount(minImage: Int) {
        state = state.copy(
            minImages = minImage
        )
    }

    fun onStateChange(event: CameraUIAction) {
        when (event) {
            is CameraUIAction.OnListImageClick -> {
                state = state.copy(
                    currentImageIndex = event.index
                )
            }
            is CameraUIAction.OnSwitchCameraClick -> {

            }
            is CameraUIAction.AfterPhotoTaken -> {
                val list = state.imageList
                val imageMetaData = ImageMetaData(
                    imageUrl = event.photoUri.toString(),
                    timeStamp = event.timeStamp,
                    date = getDateAndTime(
                        timeStamp = event.timeStamp,
                        format = WEEK_DAY_MONTH_YEAR
                    ),
                    time = getDateAndTime(
                        timeStamp = event.timeStamp,
                        format = HOUR_MINUTE_SECONDS
                    )
                )
                list.add(imageMetaData)
                state = state.copy(
                    imageList = list,
                    imageCaptured = true,
                    currentImageIndex = list.lastIndex
                )
            }

            is CameraUIAction.OnImageItemDeleteClick -> {
                /* val list = state.imageList
                 list.removeAt(event.position)
                 state = if (list.isEmpty())
                     state.copy(
                         imageList = list,
                         imageCaptured = false,
                     )
                 else state.copy(
                     imageList = list,
                     currentImageIndex = state.imageList.lastIndex
                 )*/

                state = state.copy(
                    isShowDeleteImage = true,
                    imageToDelete = event.position
                )

            }
            is CameraUIAction.OnDeleteConfirmClick -> {
                val list = state.imageList
                list.removeAt(event.position)
                state = if (list.isEmpty())
                    state.copy(
                        imageList = list,
                        imageCaptured = false,
                        imageToDelete = -1,
                        isShowDeleteImage = false,
                    )
                else state.copy(
                    imageList = list,
                    currentImageIndex = state.imageList.lastIndex,
                    imageToDelete = -1,
                    isShowDeleteImage = false,
                )
            }
            is CameraUIAction.OnDeleteCancelClick -> {
                state = state.copy(
                    isShowDeleteImage = false
                )
            }

            is CameraUIAction.OnDoneClick -> {

            }
            is CameraUIAction.OnImageItemClick -> {

            }
            is CameraUIAction.OnRetryClick -> {
                val list = state.imageList
                list.removeAt(event.position)
                state = state.copy(
                    imageCaptured = false,
                    imageList = list,
                    currentImageIndex = state.imageList.lastIndex
                )
            }
            is CameraUIAction.OnConfirmClick -> {
                state = state.copy(
                    imageCaptured = false
                )

                if (state.imageList.size == state.minImages) {
                    onStateChange(CameraUIAction.ImageCapturingComplete)
                }

            }
            is CameraUIAction.ImageCapturingComplete -> {
                viewModelScope.launch {
                    _cameraUiAction.send(
                        CameraUIAction.OnCompletingImageCapturing(state.imageList.toMutableList() as ArrayList<ImageMetaData>)
                    )
                    state = CameraState()
                }
            }

            else -> {

            }
        }
    }


}