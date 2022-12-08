package com.bjs.pdanative.domain.models.logDamage

import com.bjs.pdanative.domain.models.camera.ImageMetaData

data class LogDamageFaultModel(
    var damagedComponent: String = "",
    var priority: String = "",
    var additionalNotes: String = "",
    var faultImages: ArrayList<ImageMetaData> = arrayListOf<ImageMetaData>()
)