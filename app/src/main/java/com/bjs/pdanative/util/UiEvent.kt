package com.bjs.pdanative.util

/**
 * @Author: Vivek
 * @Date: 22/02/22
 */
sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
    data class ShowSnackBar(val message: String) : UiEvent()
}
