package com.bjs.pdanative.presentation.screens.preChecksWorkflow.satNavProcess.satNavSummary

import com.bjs.pdanative.domain.models.camera.ImageMetaData

sealed class SatNavEvents {
    object EditButtonClick : SatNavEvents()
    object BackButtonClick : SatNavEvents()
    object TapToRescanClick : SatNavEvents()
    data class OpenCameraClick(val openCamera: Boolean) : SatNavEvents()
    object SaveAndContinueClick : SatNavEvents()
    data class AfterTakingNavPicture(val imageList: MutableList<ImageMetaData>) : SatNavEvents()
}
