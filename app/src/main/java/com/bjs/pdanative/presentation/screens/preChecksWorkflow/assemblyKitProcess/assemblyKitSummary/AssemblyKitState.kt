package com.bjs.pdanative.presentation.screens.preChecksWorkflow.assemblyKitProcess.assemblyKitSummary

import com.bjs.pdanative.domain.models.camera.ImageMetaData

data class AssemblyKitState(
    var title:String ="Sat Nav",
    var assemblyKitAssetId: String ="21391487329",
    var openCamera : Boolean = false,
    var imageList: MutableList<ImageMetaData> = mutableListOf(),
    var minImages: Int = 1,
    var openImageView : Boolean = false
)
