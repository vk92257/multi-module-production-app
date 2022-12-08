package com.dialer.util

sealed class UiEvent {
    object OnCallEnded : UiEvent()
    data class ShowSnackBar(val message: String) : UiEvent()
}