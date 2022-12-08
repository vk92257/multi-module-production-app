package com.bjs.pdanative.presentation.screens.loginWorkflow.notification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bjs.pdanative.domain.models.notification.NotificationData
import com.bjs.pdanative.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * @Author: Vivek
 * @Date: 10/03/22
 */
class NotificationViewModel() : ViewModel() {
    var state by mutableStateOf<List<NotificationData>>(emptyList())
        private set
    private val _UiState = Channel<UiEvent>()
    val uiEvent = _UiState.receiveAsFlow()


    init {
        val list = ArrayList<NotificationData>()
        val one = NotificationData(
            message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
        )
        val two =
            NotificationData(message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
        val three =
            NotificationData(message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum")
        val four =
            NotificationData(message = "Lorem ipsum dolor sit amet, consecrated adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
        val five =
            NotificationData(message = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni ")

        val six =
            NotificationData(message = "\"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, ")
        list.add(one)
        list.add(two)
        list.add(three)
        list.add(four)
        list.add(five)
        list.add(six)

        state = list
    }

    fun onEvent(event: NotificationEvents) {
        when (event) {
            is NotificationEvents.onNotificationItemClick -> {
                viewModelScope.launch {
                    _UiState.send(UiEvent.ShowSnackBar("${event.notificationItem.message}  And  ${event.notificationItem.time}"))
                }

            }
            is NotificationEvents.onCallToActionClick -> {
                viewModelScope.launch {
                    _UiState.send(UiEvent.ShowSnackBar("Clicked on Call To Action Button"))
                }
            }
            is NotificationEvents.onMarkAsReadClick -> {
                viewModelScope.launch {
                    _UiState.send(UiEvent.ShowSnackBar("Clicked on Mark As Read Button"))
                }
            }
            is NotificationEvents.onCloseClick -> {
                viewModelScope.launch {
                    _UiState.send(UiEvent.ShowSnackBar("Clicked on Close Button"))
                }
            }
        }

    }


}