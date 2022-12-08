package com.bjs.pdanative.presentation.screens.loginWorkflow.termsAndCondition

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * @Author: Vivek
 * @Date: 14/03/22
 */

class TermsAndConditionViewModel() : ViewModel() {


    var state by mutableStateOf<TermsAndConditionState?>(null)
        private set
    private val _UiEvent = Channel<UiEvent>()
    var uiEvent = _UiEvent.receiveAsFlow()


    fun onAgreeClick() {
        viewModelScope.launch {
            _UiEvent.send(UiEvent.Navigate(Route.ProfileScreen))
        }
    }


}