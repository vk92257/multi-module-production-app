package com.bjs.pdanative.domain.models.lgvCheck

import com.bjs.pdanative.presentation.ui.camera.ImagesModel


//
// Created by Gaurav Chauhan on 12/05/22.
// Copyright (c) 2022 BJS. All rights reserved.
//


data class LgvCheckFault(
    val damagedFaultyComponent: String,
    val issue: String,
    val priority: String,
    val additionalNotes: String,
    val imagesList: List<ImagesModel> = emptyList()
)
