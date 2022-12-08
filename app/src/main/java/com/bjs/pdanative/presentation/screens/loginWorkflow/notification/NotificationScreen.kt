package com.bjs.pdanative.presentation.screens.loginWorkflow.notification;

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bjs.pdanative.presentation.components.WindowInfo
import com.bjs.pdanative.presentation.components.header.ToolBarHeader
import com.bjs.pdanative.presentation.components.rememberWindowInfo
import com.bjs.pdanative.presentation.screens.loginWorkflow.notification.component.NotificationComponent
import com.bjs.pdanative.util.UiEvent

/**
 * @Author: Vivek
 * @Date: 09/03/22
 */

@Composable
fun NotificationScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: NotificationViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    scaffoldState: ScaffoldState,
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(it.message)
                }

                is UiEvent.Navigate -> {
                    onNavigate(it)
                }

                is UiEvent.NavigateUp -> {

                }
            }
        }
    }
    val screenInfo = rememberWindowInfo()
    Column(modifier = Modifier.fillMaxSize()) {
        ToolBarHeader("Notifications") {
            navigateUp()
        }

        if (screenInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(viewModel.state) { item ->
                    NotificationComponent(
                        message = item.message,
                        onCallToActionClick = {
                            viewModel.onEvent(NotificationEvents.onCallToActionClick)
                        },
                        onMarkAsReadClick = {
                            viewModel.onEvent(NotificationEvents.onMarkAsReadClick)
                        }, onNotificationTab = {
                            viewModel.onEvent(NotificationEvents.onNotificationItemClick(item))
                        }
                    )
                }
            }
        } else {
            Row(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(viewModel.state) { item ->
                        NotificationComponent(
                            message = item.message,
                            onCallToActionClick = {
                                viewModel.onEvent(NotificationEvents.onCallToActionClick)
                            },
                            onMarkAsReadClick = {
                                viewModel.onEvent(NotificationEvents.onMarkAsReadClick)
                            }, onNotificationTab = {
                                viewModel.onEvent(NotificationEvents.onNotificationItemClick(item))
                            }
                        )
                    }
                }

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(viewModel.state) { item ->
                        NotificationComponent(message = item.message,
                            onCallToActionClick = {
                                viewModel.onEvent(NotificationEvents.onCallToActionClick)
                            },
                            onMarkAsReadClick = {
                                viewModel.onEvent(NotificationEvents.onMarkAsReadClick)
                            }, onNotificationTab = {
                                viewModel.onEvent(NotificationEvents.onNotificationItemClick(item))
                            })
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun Preview() {
    NotificationScreen(onNavigate = {},
        navigateUp = { },
        scaffoldState = rememberScaffoldState())

}