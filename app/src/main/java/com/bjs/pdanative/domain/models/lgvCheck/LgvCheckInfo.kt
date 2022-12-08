package com.bjs.pdanative.domain.models.lgvCheck


//
// Created by Gaurav Chauhan on 12/05/22.
// Copyright (c) 2022 BJS. All rights reserved.
//

data class LgvCheckInfo(
    var checkTitle: String,
    var checkDescription: String,
    var checkTip: String? = null,
    var checkIcon: Int,
    var selected: Boolean = false,
    var fail: Boolean = false,
    var pass: Boolean = false,
    var faultTitle: String,
    var faultList: List<LgvCheckFault> = emptyList(),
    var lgvCheckId: Int
)
