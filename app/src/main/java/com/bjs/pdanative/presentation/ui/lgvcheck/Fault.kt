package com.bjs.pdanative.presentation.ui.lgvcheck

import com.bjs.pdanative.presentation.ui.camera.ImagesModel


data class Fault(
    var faultyComponent: String,
    var fault: String,
    var priority: String,
    var additionalNote: String,
    var faultImages:  MutableList<ImagesModel> = ArrayList()
)
