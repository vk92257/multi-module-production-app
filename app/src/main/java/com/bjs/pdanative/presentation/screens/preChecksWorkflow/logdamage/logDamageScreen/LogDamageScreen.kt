package com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageScreen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.CustomBrush
import com.core_ui.components.header.TitleHeader
import com.bjs.pdanative.presentation.screens.common.camera.cameraScreen.CameraScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.deletelogdamage.DeleteLogDamageEntryScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.exitlogdamage.ExitLogDamageScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageFormScreen.LogFaultFormScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageScreen.component.LogDamageFooterItem
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageScreen.component.LogDamageHeaderItem
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageScreen.component.LogDamageItem
import com.bjs.pdanative.util.UiEvent


@Composable
@ExperimentalComposeUiApi
//@Preview(showBackground = true)
fun LogDamageScreen(
    viewModel: LogDamageViewModel = hiltViewModel(),
    navigateUp: () -> Boolean,
    scaffoldState: ScaffoldState,
    navigate: (UiEvent.Navigate) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
    val state = viewModel.state
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {

        }
    }
    if (!state.openCamera) {
        when {
            state.isExitFromScreen -> {
                navigateUp()
            }
            state.isItemDelete -> {
                DeleteLogDamageEntryScreen(
                    onDeleteClick = {
                        viewModel.onStateChange(
                            LogDamageScreenEvents.OnDeleteItemButtonClick(
                                state.updateItemPosition
                            )
                        )
                    },
                    onCancelClick = {
                        viewModel.onStateChange(
                            LogDamageScreenEvents.CancelDeleteConfirmationClick(state.updateItemPosition)
                        )
                    },
                    index = (state.updateItemPosition) + 1,
                    damageComponent = state.faultList[state.updateItemPosition].damagedComponent
                )
            }
            state.exitConfirmation -> {
                ExitLogDamageScreen(
                    onCancelClick = {
                        viewModel.onStateChange(
                            LogDamageScreenEvents.CancelDeleteConfirmationClick(state.updateItemPosition)
                        )
                    },
                    onExitClick = {
                        viewModel.onStateChange(LogDamageScreenEvents.ExitConfirmationScreenButtonClick)
                    }
                )
            }
            else -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    TitleHeader(title = stringResource(id = R.string.log_damage),
                        trailerIcon = painterResource(id = R.drawable.ic_close),
                        isShowHeadIcon = false,
                        onHeadIconClick = {},
                        onTrailerIconClick = { }
                    )

                    if (!state.openFaultFormScreen) {
                        LazyColumn(
                            Modifier
                                .background(brush = CustomBrush.vGradientGrayWhite)
                                .fillMaxSize()
                        ) {

                            // Header Image Item
                            item {
                                LogDamageHeaderItem()
                            }

                            // Log Damage Fault List
                            itemsIndexed(state.faultList) { index, item ->
                                LogDamageItem(
                                    item.damagedComponent,
                                    item.priority,
                                    item.additionalNotes,
                                    index,
                                    onDeleteClick = {
                                        viewModel.onStateChange(
                                            LogDamageScreenEvents.DeleteConfirmationClick(
                                                index
                                            )
                                        )
                                    },
                                    onEditClick = {
                                        viewModel.onStateChange(
                                            LogDamageScreenEvents.OnEditItemButtonClick(
                                                index
                                            )
                                        )

                                    }
                                )
                            }
                            // Footer Item
                            item {
                                LogDamageFooterItem({
                                    //  save and continue
                                    viewModel.onStateChange(
                                        LogDamageScreenEvents.OnSaveClick
                                    )
                                    // navigate to previous screen
                                    navigateUp()
                                }, {
                                    // add more item
                                    viewModel.onStateChange(LogDamageScreenEvents.OnAddMoreButtonClick)
                                })
                            }
                        }
                    } else {

                        // fault form layout
                        LogFaultFormScreen(
                            priority = state.logDamageFaultModel.priority,
                            damagedComponent = state.logDamageFaultModel.damagedComponent,
                            onCameraClick = {
                                keyboardController?.hide()
                                viewModel.onStateChange(
                                    LogDamageScreenEvents.OnImageCaptureButtonClick(
                                        true
                                    )
                                )

                            },
                            damagedComponentList = state.damagedComponentList,
                            priorityList = state.priorityList,
                            onCancelClick = {
                                viewModel.onStateChange(
                                    LogDamageScreenEvents.OnCancelButtonClick(
                                        state.updateItemPosition
                                    )
                                )
                            },
                            faultImages = state.logDamageFaultModel.faultImages,
                            onNextClick = {
                                viewModel.onStateChange(LogDamageScreenEvents.OnNextButtonClick)
                            },
                            onDamagedComponentSelected = {
                                viewModel.onStateChange(
                                    LogDamageScreenEvents.OnDamagedComponentSelected(
                                        it
                                    )
                                )
                            },
                            onAdditionalInfoChange = {
                                viewModel.onStateChange(
                                    LogDamageScreenEvents.OnAdditionalInfoChange(
                                        it
                                    )
                                )
                            },
                            onPrioritySelected = {
                                viewModel.onStateChange(
                                    LogDamageScreenEvents.OnPrioritySelected(
                                        it
                                    )
                                )
                            },
                            additionalNotes = state.logDamageFaultModel.additionalNotes,
                            softwareKeyboardController = keyboardController!!
                        )
                    }
                }
            }
        }

    } else {
        CameraScreen(
            navigateUp = {
                viewModel.onStateChange(LogDamageScreenEvents.OnImageCapturingComplete(it))
                viewModel.onStateChange(LogDamageScreenEvents.OnImageCaptureButtonClick(false))
            },
            scaffoldState = rememberScaffoldState(),
            isForSingleImage = false,
            imageList = state.logDamageFaultModel.faultImages,
            openFrontFacingCamera = false,
        )
    }

    BackHandler(state.openCamera) {
        if (state.openCamera) viewModel.onStateChange(
            LogDamageScreenEvents.OnImageCaptureButtonClick(
                false
            )
        )
        else dispatcher.onBackPressed()
    }

}










