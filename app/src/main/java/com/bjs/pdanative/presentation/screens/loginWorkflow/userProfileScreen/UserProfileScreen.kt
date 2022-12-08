package com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bjs.pdanative.domain.models.camera.ImageMetaData
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.screens.common.camera.cameraScreen.CameraScreen
import com.bjs.pdanative.presentation.screens.loginWorkflow.loggedInusers.LoggedInUserListScreen
import com.bjs.pdanative.util.UiEvent


/**
 * @Author: Vivek
 * @Date: 14/03/22
 */


@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileScreen(
    navigate: (UiEvent.Navigate) -> Unit,
    navigateUp: () -> Unit,
    scaffoldState: ScaffoldState,
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    images: List<ImageMetaData>
) {

    val state = viewModel.state
    val context = LocalContext.current
    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
    if (images.isNotEmpty()) viewModel.onStateChange(ProfileScreenEvents.AfterImageCaptured(images as ArrayList<ImageMetaData>))
    val spacing = LocalSpacing.current
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = false) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message
                    )
                }
                is UiEvent.Navigate -> {
//                    navigate(it)
                    viewModel.startWorkManager(context)


                }
                else -> {}
            }
        }
    }

    if (!state.isCameraOpen && !state.isUserListOpen) {
        ProfileScreen(
            id = state.loggedInUser.id,
            name = state.loggedInUser.name,
            suggestions = state.roleTypeList,
            dropDownPlaceHolder = state.loggedInUser.roleText,
            notificationCount = state.notificationCount.toString(),
            profileImage = state.loggedInUser.image,
            onNotificationClick = {
                viewModel.onStateChange(event = ProfileScreenEvents.OnNotificationClick("notification_click"))

            },
            onEditButtonClick = {
                viewModel.onStateChange(event = ProfileScreenEvents.OnEditClick)
            },
            onDropDownItemClick = {
                viewModel.onStateChange(ProfileScreenEvents.OnDropDownSelect(it))
            },
            onTermsAndConditionClick = {
                viewModel.onStateChange(ProfileScreenEvents.TermsAndConditionButtonClick(true))
            },
            onCheckChanged = {
                viewModel.onStateChange(ProfileScreenEvents.TermsAndConditionCheckBoxClick(it))
            },
            onContinueClick = {
                viewModel.onStateChange(ProfileScreenEvents.OnContinueClick)

            },
        )
    } else if (state.isCameraOpen) {
        CameraScreen(
            navigateUp = {
                viewModel.onStateChange(ProfileScreenEvents.AfterImageCaptured(it as ArrayList<ImageMetaData>))
                viewModel.onStateChange(ProfileScreenEvents.OpenCameraClick(false))
            },
            imageList = arrayListOf(),
            minImages = 1,
            isForSingleImage = true,
            openFrontFacingCamera = true,
        )
    } else if (state.isUserListOpen) {
        LoggedInUserListScreen(
            onNavigateUp = {},
            onNavigate = {},
            loggedInUserList = state.loggedInUsersList,
            onAddAnotherUserClick = {
                viewModel.onStateChange(ProfileScreenEvents.OnAddAnotherUserClick)
            },
            onContinueClick = {
                viewModel.onStateChange(ProfileScreenEvents.OnLoggedInUserContinueClick)
            },
            routeId = state.routeId,
            wareHouse = state.wareHouse,
            notification = state.notificationList,
            routeRequirementMessage = state.routeRequirementMessage,
            onNotificationClick = {
                viewModel.onStateChange(ProfileScreenEvents.OnNotificationClick(""))
            },
            changeRoleButtonClickListener = {

            },
            onSignOutClick = {

            },
            roleButtonClickListener = {

            }
        )
    }

    BackHandler(state.isCameraOpen) {
        if (state.isCameraOpen) viewModel.onStateChange(ProfileScreenEvents.OpenCameraClick(false))
        else dispatcher.onBackPressed()
    }

}


@Preview
@Composable
fun Preview() {
    ProfileScreen(
        navigate = {},
        navigateUp = {},
        scaffoldState = rememberScaffoldState(),
        viewModel = ProfileScreenViewModel(),
        images = listOf()
    )
}

