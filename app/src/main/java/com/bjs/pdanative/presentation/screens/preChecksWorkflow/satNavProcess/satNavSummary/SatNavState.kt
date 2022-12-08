package com.bjs.pdanative.presentation.screens.preChecksWorkflow.satNavProcess.satNavSummary

import com.bjs.pdanative.domain.models.camera.ImageMetaData

data class SatNavState(
    var title:String ="Sat Nav",
    var navAssetId: String ="21391487329",
    var openCamera : Boolean = false,
    var imageList: MutableList<ImageMetaData> = mutableListOf(),
    var minImages: Int = 1,
    var openImageView : Boolean = false
)
