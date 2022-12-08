package com.bjs.pdanative.presentation.screens.loginWorkflow.verifyPasscode

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * @Author: Vivek
 * @Date: 25/10/21
 */
class VerifyPasscodeScreenViewModel : ViewModel() {
    val openDialog = mutableStateOf(false)
    val text = mutableStateOf("")
    val isPassCodeSent = mutableStateOf(false)

}