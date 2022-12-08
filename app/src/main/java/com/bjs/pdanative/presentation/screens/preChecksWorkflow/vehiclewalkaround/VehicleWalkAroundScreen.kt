package com.bjs.pdanative.presentation.screens.preChecksWorkflow.vehiclewalkaround

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bjs.pdanative.R
import com.bjs.pdanative.domain.models.camera.ImageMetaData
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.LeftHalfOutlinedRoundButton
import com.core_ui.components.buttons.RightHalfRoundButton
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.bjs.pdanative.presentation.screens.common.camera.cameraScreen.CameraScreen
import com.bjs.pdanative.util.UiEvent

@Composable
fun VehicleWalkAroundScreen(
    viewModel: VehicleWalkAroundViewModel = hiltViewModel(),
    navigateUp: () -> Boolean,
    scaffoldState: ScaffoldState,
    navigate: (UiEvent.Navigate) -> Unit,
) {
    val spacer = LocalSpacing.current
    val scrollState = rememberScrollState()
    val state = viewModel.state
    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> {
                    navigate(it)
                }
                is UiEvent.NavigateUp -> {

                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message
                    )
                }
            }
        }
    }



    if (!state.isCameraOpen) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(
                    brush = CustomBrush.vGradientGrayWhite
                )

        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "back-button",
                tint = colorResource(id = R.color.blackSecondary).copy(alpha = 0.7F),
                modifier = Modifier
                    .size(40.dp)
                    .padding(
                        start = 15.dp,
                        top = 15.dp
                    )
                    .clickable {
                    }
            )

            Spacer(modifier = Modifier.size(spacer.spaceLarge))
            Text(
                text = "Vehicle Walk",
                modifier = Modifier.align(
                    alignment = Alignment.CenterHorizontally
                ),
                color = colorResource(id = R.color.blackSecondary).copy(alpha = 0.7F),
                style = TypographyEvelethClean.h6,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "Around",
                modifier = Modifier.align(
                    alignment = Alignment.CenterHorizontally
                ),
                color = colorResource(id = R.color.blackSecondary),
                style = TypographyEvelethClean.h3,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(spacer.spaceLarge))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bjstwoman_van),
                    contentDescription = "van image ",
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.TopCenter)
                        .height(250.dp)
                        .padding(horizontal = 25.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_hand_camera),
                    contentDescription = "van image ",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 135.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.take_vehicle_photo),
                modifier = Modifier
                    .align(
                        alignment = Alignment.CenterHorizontally
                    )
                    .padding(horizontal = 30.dp),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                color = colorResource(id = R.color.blackSecondary)
            )

            Spacer(modifier = Modifier.size(spacer.spaceEighteen))
            Text(
                text = stringResource(id = R.string.walk_around_photo),
                modifier = Modifier
                    .align(
                        alignment = Alignment.CenterHorizontally
                    )
                    .padding(horizontal = 30.dp),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                color = colorResource(id = R.color.blackSecondary)
            )

            Row(
                Modifier
                    .padding(top = 50.dp, bottom = 20.dp)
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                LeftHalfOutlinedRoundButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .offset(x = (-10).dp),
                    text = "Back",
                    onClick = {
                        navigateUp()
                    }
                )
                RightHalfRoundButton(
                    text = "Next",
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .offset(x = (10).dp),
                    onClick = {
                        viewModel.onStateChange(VehicleWalkAroundEvent.OpenCamera(true))
                    }
                )

            }
        }
    } else {
        CameraScreen(
            isForLogDamage = true,

            onLogDamageClick = {
                viewModel.onStateChange(VehicleWalkAroundEvent.OpenLogDamage)
            },
            minImages = 4,
            navigateUp = {
                Log.e("TAG", "AddVehicleWalkAroundPhotos: $it")
                viewModel.onStateChange(
                    event = VehicleWalkAroundEvent.OnImageCapturingCompleted(it as ArrayList<ImageMetaData>)
                )

                /*navigate(UiEvent.Navigate(Route.VehicleWalkAroundSuccessful))*/
            },
            scaffoldState = rememberScaffoldState(),
            isForSingleImage = false,
//        imageList = state.logDamageFaultModel.faultImages,
            openFrontFacingCamera = false,
        )
    }


    BackHandler(state.isCameraOpen) {
        if (state.isCameraOpen) viewModel.onStateChange(VehicleWalkAroundEvent.OpenCamera(false))
        else dispatcher.onBackPressed()
    }

}



