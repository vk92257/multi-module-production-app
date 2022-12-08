package com.bjs.pdanative.presentation.screens.preChecksWorkflow.assemblyKitProcess.assemblyKitSummary

import com.bjs.pdanative.domain.models.camera.ImageMetaData

sealed class AssemblyKitEvents {
    object EditButtonClick : AssemblyKitEvents()
    object BackButtonClick : AssemblyKitEvents()
    object TapToRescanClick : AssemblyKitEvents()
    data class OpenCameraClick(val openCamera: Boolean) : AssemblyKitEvents()
    object SaveAndContinueClick : AssemblyKitEvents()
    data class AfterTakingPicture(val imageList: MutableList<ImageMetaData>) : AssemblyKitEvents()
}
