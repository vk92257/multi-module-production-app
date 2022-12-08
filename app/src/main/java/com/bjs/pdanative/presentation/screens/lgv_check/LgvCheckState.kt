package com.bjs.pdanative.presentation.screens.lgv_check

import com.bjs.pdanative.domain.models.lgvCheck.LgvCheckInfo

//
// Created by Gaurav Chauhan on 12/05/22.
// Copyright (c) 2022 BJS. All rights reserved.
//

data class LgvCheckState(

    val checks: MutableList<LgvCheckInfo> = emptyList<LgvCheckInfo>().toMutableList(),
    val selected: Int = 0,
    val failSelected: Boolean = false,
    val passSelected: Boolean = false,
    val lgvCheck: LgvCheckInfo

)
