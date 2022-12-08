package com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.bjs.pdanative.domain.models.userProfile.LoggedInUser
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.presentation.screens.common.camera.CompressImageWorker
import com.bjs.pdanative.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * @Author: Vivek
 * @Date: 30/03/22
 */
class ProfileScreenViewModel : ViewModel() {

    var state by mutableStateOf(ProfileScreenState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onStateChange(event: ProfileScreenEvents) {
        when (event) {
            is ProfileScreenEvents.TermsAndConditionCheckBoxClick -> {
                state = state.copy(
                    loggedInUser = state.loggedInUser.copy(
                        isTermsAndConditionAccepted = event.value
                    )
                )
            }
            is ProfileScreenEvents.TermsAndConditionButtonClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Route.TermsAndCondition))
                }
            }
            is ProfileScreenEvents.OnContinueClick -> {
                val loggedInUser = state.loggedInUser
                val loggedInUserList = state.loggedInUsersList
                loggedInUserList.add(loggedInUser)
                state = state.copy(
                    isUserListOpen = true,
                    loggedInUser = LoggedInUser(),
                    loggedInUsersList = loggedInUserList
                )

                /*  val data = Data.Builder().apply {
                      putStringArray("userList", loggedInUserList.map { it.image }.toTypedArray())
                  }.build()

                  val compressImageWorker = OneTimeWorkRequestBuilder<CompressImageWorker>()
                      .setInputData(data)
                      .build()

                  val workManager = WorkManager.getInstance()*/


            }
            is ProfileScreenEvents.OnEditClick -> {
                state = state.copy(
                    isCameraOpen = true
                )

            }
            is ProfileScreenEvents.OnDropDownSelect -> {
                state = state.copy(
                    loggedInUser = state.loggedInUser.copy(
                        roleText = event.value
                    )
                )
            }

            is ProfileScreenEvents.OnNotificationClick -> {

                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Route.NotificationScreen))
                }


            }

            is ProfileScreenEvents.AfterImageCaptured -> {
                state = state.copy(
                    loggedInUser = state.loggedInUser.copy(
                        image = event.listOfImages.first().imageUrl
                    )
                )
            }

            /* Logged In User list screen Event */
            is ProfileScreenEvents.OpenUserList -> {
                state = state.copy(
                    isUserListOpen = event.openUserList
                )
            }
            is ProfileScreenEvents.OpenCameraClick -> {
                state = state.copy(
                    isCameraOpen = event.openCameraClick
                )
            }

            is ProfileScreenEvents.OnAddAnotherUserClick -> {
                state = state.copy(
                    isUserListOpen = false
                )
            }

            is ProfileScreenEvents.OnLoggedInUserContinueClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Route.DriverLoginConfirmationScreen))
                }
            }

        }
    }


    fun startWorkManager(context: Context) {
        val data = Data.Builder().apply {
            putStringArray("imageFile", state.loggedInUsersList.map { it.image }.toTypedArray())
        }.build()

        val compressImageWorker = OneTimeWorkRequestBuilder<CompressImageWorker>()
            .setInputData(data)
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueue(compressImageWorker)
    }


}