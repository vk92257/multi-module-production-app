package com.bjs.pdanative.presentation.screens.preChecksWorkflow.assemblyKitProcess.assemblyKitSummary

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bjs.pdanative.R
import com.bjs.pdanative.domain.models.camera.ImageMetaData
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.GradientButton
import com.core_ui.components.header.TitleHeader
import com.bjs.pdanative.presentation.screens.common.ImageListView
import com.bjs.pdanative.presentation.screens.common.SatNavPictureSummaryComponent
import com.bjs.pdanative.presentation.screens.common.SatNavTakePictureComponent
import com.bjs.pdanative.presentation.screens.common.camera.cameraScreen.CameraScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.satNavProcess.satNavSummary.component.SatNavSummaryComponents
import com.bjs.pdanative.util.UiEvent


@Composable
fun AssemblyKitSummaryScreen(
    navigateUp: () -> Unit,
    scaffoldState: ScaffoldState,
    navigate: (UiEvent.Navigate) -> Unit,
    viewModel: AssemblyKitViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val spacer = LocalSpacing.current
    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
    val scrollState = rememberScrollState()
    if (!state.openCamera) {
        Column {
            TitleHeader(
                isShowHeadIcon = true,
                onHeadIconClick = {
                    viewModel.onStateChanged(AssemblyKitEvents.BackButtonClick)
//                    navigateUp()
                },
                title = stringResource(R.string.sat_nav),
                isShowTrailerIcon = false,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        state = scrollState,
                    )
                    .background(
                        brush = CustomBrush.vGradientGrayWhite
                    ),
            ) {
                Spacer(modifier = Modifier.padding(spacer.spaceLargeExtreme))
                SatNavSummaryComponents(
                    modifier = Modifier
                        .padding(
                            horizontal = 6.dp,
                        )
                        .wrapContentHeight(),
                    image = painterResource(id = R.drawable.ic_bjs_bag),
                    navAssetId = state.assemblyKitAssetId,
                    onEditButtonClicked = {},
                    onScanClicked = {}
                )

                if (state.imageList.isEmpty()) {
                    SatNavTakePictureComponent(
                        modifier = Modifier.align(
                            alignment = Alignment.CenterHorizontally
                        ),
                        imageCount = state.minImages,
                        onCameraClick = {
                            viewModel.onStateChanged(AssemblyKitEvents.OpenCameraClick(true))
                        },
                    )
                } else {
                    SatNavPictureSummaryComponent(
                        modifier = Modifier.align(
                            alignment = Alignment.CenterHorizontally
                        ),
                        imageCount = state.minImages,
                        onCameraClick = {
                            viewModel.onStateChanged(AssemblyKitEvents.OpenCameraClick(true))
                        },
                    )
                    Spacer(modifier = Modifier.size(spacer.spaceMedium))
                    ImageListView(
                        imageList = state.imageList as ArrayList<ImageMetaData>
                    )
                    Spacer(modifier = Modifier.size(spacer.spaceMedium))
                    GradientButton(
                        text = stringResource(id = R.string.save_continue),
                        onclick = {
                            navigate(UiEvent.Navigate(Route.SatNavAssemblyKitVerificationCompletedScreen))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(60.dp),
                        gradient = CustomBrush.lGradientGreenAndLightGreen,
                        iconVisible = false
                    )
                    Spacer(modifier = Modifier.size(spacer.spaceLargeExtreme))
                }
            }
        }
    } else if (state.openCamera) {
        CameraScreen(
            navigateUp = {
                viewModel.onStateChanged(AssemblyKitEvents.AfterTakingPicture(it as ArrayList<ImageMetaData>))
                viewModel.onStateChanged(AssemblyKitEvents.OpenCameraClick(false))
            },
            imageList = state.imageList as ArrayList<ImageMetaData>,
            isForSingleImage = false,
            openFrontFacingCamera = false,
        )
    }
    BackHandler(state.openCamera) {
        if (state.openCamera) viewModel.onStateChanged(AssemblyKitEvents.OpenCameraClick(false))
        else dispatcher.onBackPressed()
    }

}

@Composable
@Preview
fun AssemblyKitSummaryScreenPreview() {
    AssemblyKitSummaryScreen(
        scaffoldState = rememberScaffoldState(),
        navigate = {},
        navigateUp = {}
    )
}