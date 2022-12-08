package com.bjs.pdanative.presentation.screens.loginWorkflow.login_route

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
 * @Date: 23/02/22
 */

class LoginRouteViewModel(): ViewModel() {
   var phoneNumber by mutableStateOf("")
    private set

    private val _UiEvent = Channel<UiEvent>()
    val uiEvent = _UiEvent.receiveAsFlow()

    fun onNextClick(){
        viewModelScope.launch {
            _UiEvent.send(UiEvent.Navigate(Route.VerifyPasscode))
        }
    }

}