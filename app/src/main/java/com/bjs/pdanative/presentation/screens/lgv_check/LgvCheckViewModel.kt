package com.bjs.pdanative.presentation.screens.lgv_check

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bjs.pdanative.domain.models.lgvCheck.LgvCheckInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


//
// Created by Gaurav Chauhan on 17/05/22.
// Copyright (c) 2022 BJS. All rights reserved.
//

@HiltViewModel
class LgvCheckViewModel @Inject constructor(private val list: MutableList<LgvCheckInfo>) :
    ViewModel() {


    private val _uiEvents = Channel<LgvCheckUIEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()


    var state by mutableStateOf(
        LgvCheckState(
            lgvCheck = list[0], checks = list, selected = 0
        )

    )


    fun onEvent(event: LgvCheckUIEvents) {

        when (event) {

            is LgvCheckUIEvents.OnFaultSelected -> {

                state = state.copy(lgvCheck = event.lgvCheckInfo)
            }

            is LgvCheckUIEvents.OnFailSelected -> {

                state.checks[state.selected].apply {
                    pass = false
                    fail = true
                }
                state = if (state.selected < state.checks.lastIndex) {
                    state.copy(
                        lgvCheck = state.checks[state.selected + 1],
                        selected = state.selected + 1,
                        checks = state.checks
                    )
                } else {
                    state.copy(
                        lgvCheck = state.checks[state.selected],
                        selected = state.selected,
                        checks = state.checks,
                        failSelected = true,
                        passSelected = false
                    )
                }

            }

            is LgvCheckUIEvents.OnPassSelected -> {

                state.checks[state.selected].apply {
                    pass = true
                    fail = false
                }

                if (event.position < state.checks.lastIndex) {
                    state = state.copy(
                        lgvCheck = state.checks[state.selected + 1],
                        selected = state.selected + 1,
                        checks = state.checks,

                        )
                } else {
                    state = state.copy(
                        lgvCheck = state.checks[state.selected],
                        selected = state.selected,
                        checks = state.checks,
                        failSelected = false,
                        passSelected = true
                    )
                }


            }

            is LgvCheckUIEvents.GotoNextCheck -> {

                state = state.copy(lgvCheck = event.lgvCheckInfo)
            }

            else -> {}
        }


    }


    fun sendUiEvent(event: LgvCheckUIEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }


}