package com.bjs.pdanative.presentation.screens.lgv_check

import com.bjs.pdanative.domain.models.lgvCheck.LgvCheckInfo


//
// Created by Gaurav Chauhan on 17/05/22.
// Copyright (c) 2022 BJS. All rights reserved.
//h

sealed class LgvCheckUIEvents {
    data class OnFaultSelected(val lgvCheckInfo: LgvCheckInfo) : LgvCheckUIEvents()
    data class Navigate(val route: String) : LgvCheckUIEvents()
    data class ShowSnackBar(val message: String, val action: String? = null) : LgvCheckUIEvents()
    data class OnFailSelected(val position: Int) : LgvCheckUIEvents()
    data class OnPassSelected(
        val position: Int
    ) : LgvCheckUIEvents()

    data class GotoNextCheck(val lgvCheckInfo: LgvCheckInfo) : LgvCheckUIEvents()
}
