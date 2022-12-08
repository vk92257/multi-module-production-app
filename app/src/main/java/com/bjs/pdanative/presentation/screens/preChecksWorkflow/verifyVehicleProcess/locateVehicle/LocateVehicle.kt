package com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.locateVehicle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bjs.pdanative.R
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.components.WindowInfo
import com.core_ui.components.buttons.CircularButtonWithImage
import com.bjs.pdanative.presentation.components.rememberWindowInfo
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.locateVehicle.component.DirectionDetails
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.locateVehicle.component.LocateVehicleToolbar
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.locateVehicle.component.MapScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.locateVehicle.component.VehicleLocationAndDistance
import com.bjs.pdanative.util.UiEvent
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun LocateVehicle(
    viewModel: LocateVehicleViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    navigate: (UiEvent.Navigate) -> Unit,
) {
    val state = viewModel.state
    val screenInfo = rememberWindowInfo()
    val spacer = LocalSpacing.current
    Column {
        LocateVehicleToolbar()
        if (screenInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
            MapScreen(
                modifier = Modifier.height(300.dp),
                viewModel = viewModel,
                longitude = state.destinationLongitude,
                latitude = state.destinationLatitude,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        state = rememberScrollState(),
                    )

            ) {
                VehicleLocationAndDistance(
                    vanLocation = state.vanLocation,
                    distance = state.distance
                )
                DirectionDetails(
                    listOfDirection = state.directionDetailList,
                )
                CircularButtonWithImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp
                        ),

                    onClick = {
                        navigate(UiEvent.Navigate(Route.ArrivedAtYourVehicle))
                        viewModel.onLocateVanClick()
                    },
                    text = stringResource(R.string.found_my_van),
                    painterResources = painterResource(id = R.drawable.ic_bjs_van)
                )
                Spacer(modifier = Modifier.size(spacer.spaceMedium))
            }
        } else {
            Row {
                MapScreen(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    longitude = state.destinationLongitude,
                    latitude = state.destinationLatitude,
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(
                            state = rememberScrollState(),
                            enabled = !rememberCameraPositionState().isMoving
                        )
                ) {
                    VehicleLocationAndDistance(
                        vanLocation = state.vanLocation,
                        distance = state.distance
                    )
                    DirectionDetails(
                        listOfDirection = state.directionDetailList,
                    )
                    CircularButtonWithImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp
                            ),
                        onClick = {
                            navigate(UiEvent.Navigate(Route.ArrivedAtYourVehicle))
                            viewModel.onLocateVanClick()
                        },
                        text = stringResource(R.string.found_my_van),
                        painterResources = painterResource(id = R.drawable.ic_bjs_van)
                    )
                    Spacer(modifier = Modifier.size(spacer.spaceMedium))
                }
            }


        }


    }

}

@Composable
fun LocateVehiclePreview(){
     LocateVehicle(
        scaffoldState = rememberScaffoldState(),
        navigate= {},
    )
}

