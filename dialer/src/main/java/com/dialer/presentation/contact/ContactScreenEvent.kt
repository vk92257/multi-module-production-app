package com.dialer.presentation.contact

sealed class ContactScreenEvent {
    data class OnSearchEvent(val text: String) : ContactScreenEvent()
}