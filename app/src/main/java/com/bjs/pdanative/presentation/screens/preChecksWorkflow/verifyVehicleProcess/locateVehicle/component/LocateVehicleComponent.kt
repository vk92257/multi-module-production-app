package com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.locateVehicle.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.locateVehicle.DirectionDetail
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.locateVehicle.LocateVehicleViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@Composable
@Preview(showBackground = true)
fun LocateVehicleToolbar(
    modifier: Modifier = Modifier,
    text: String = "",
    iconClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.blackSecondary)
            )
            .size(60.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.user_group),
            contentDescription = "new data",
            tint = colorResource(id = R.color.dark_grayish_orange),
            modifier = Modifier
                .size(40.dp)
                .align(
                    alignment = Alignment.CenterStart
                )
                .padding(
                    start = 15.dp
                )
                .clickable(
                    onClick = iconClick
                )
        )
        Text(
            text = stringResource(R.string.locate_vehicle),
            modifier = Modifier.align(
                alignment = Alignment.Center
            ),
            fontWeight = FontWeight.Bold,
            style = TypographyEvelethClean.body1,
            color = colorResource(id = R.color.white)
        )

    }
}


@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: LocateVehicleViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    latitude: Double = 1.389,
    longitude: Double = 103.25,
) {

    val singapore = LatLng(latitude, longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            cameraPositionState = cameraPositionState,
            properties = viewModel.state.properties,
            uiSettings = uiSettings,
            onMapLongClick = {
                /*  Sent the lat long t0 the view model for g*/
            },

            ) {
            Marker(
                state = MarkerState(position = singapore),
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }

    }
}


@Composable
@Preview
fun VehicleLocationAndDistance(
    modifier: Modifier = Modifier,
    vanLocation: String = "Main Warehouse",
    distance: String = "< 1km",
) {

    val spacer = LocalSpacing.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.bg_light_white)
            )
    ) {

        Spacer(modifier = Modifier.size(spacer.spaceSmall))
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(spacer.spaceLarge))
            Text(
                text = stringResource(R.string.van_location),
                style = TypographyEvelethClean.body2,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.black),
                modifier = Modifier.padding(
                    horizontal = 10.dp,
                )
            )
            Spacer(modifier = Modifier.size(spacer.spaceExtraSmall))
            Divider(
                color = colorResource(id = R.color.bjs),
                modifier = Modifier
                    .width(50.dp)
                    .height(3.dp)
                    .padding(
                        horizontal = 10.dp
                    )
            )

            Spacer(modifier = Modifier.size(spacer.spaceMedium))
            Text(
                text = vanLocation,
                style = MaterialTheme.typography.body1,
                color = colorResource(id = R.color.blackSecondary),
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(
                    10.dp
                )
            )
            Spacer(modifier = Modifier.size(spacer.spaceMedium))
        }
        Spacer(modifier = Modifier.size(spacer.spaceSmall))
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.size(spacer.spaceLarge))
            Text(
                text = stringResource(R.string.distance),
                style = TypographyEvelethClean.body2,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.black),
                modifier = Modifier.padding(
                    horizontal = 10.dp,
                )
            )
            Spacer(modifier = Modifier.size(spacer.spaceExtraSmall))
            Divider(
                color = colorResource(id = R.color.bjs),
                modifier = Modifier
                    .width(50.dp)
                    .height(3.dp)
                    .padding(
                        horizontal = 10.dp
                    )
            )
            Spacer(modifier = Modifier.size(spacer.spaceMedium))
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.running),
                    contentDescription = "new data",
                    tint = colorResource(id = R.color.black),
                    modifier = Modifier
                        .size(25.dp)
                        .padding(
                            start = 5.dp
                        )
                        .align(
                            alignment = Alignment.CenterVertically
                        )
                )
                Text(
                    text = distance,
                    style = MaterialTheme.typography.body1,
                    color = colorResource(id = R.color.blackSecondary),
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(
                            10.dp
                        )
                        .align(
                            alignment = Alignment.CenterVertically
                        )
                        .offset(
                            x = (-8).dp
                        )
                )
            }
            Spacer(modifier = Modifier.size(spacer.spaceMedium))
        }
    }
}


@Composable
@Preview(device = "spec:shape=Normal,width=1080,height=2340,unit=px,dpi=480")
fun DirectionDetails(
    modifier: Modifier = Modifier,
    listOfDirection: MutableList<DirectionDetail> = mutableStateListOf(),
    directionCount: Int = 0
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.white)
            )
    ) {
        val spacer = LocalSpacing.current
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.direction_to_your_vehicle),
            style = TypographyEvelethClean.body2,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(
                    vertical = 20.dp,
                )
                .fillMaxWidth()
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
        ) {

            items(listOfDirection) { item: DirectionDetail ->
                DirectionDetailItem(
                    directionDetail = item,
                )
            }
        }
        Spacer(modifier = Modifier.size(spacer.spaceMedium))
    }
}


@Composable
@Preview
fun DirectionDetailItem(
    modifier: Modifier = Modifier,
    directionDetail: DirectionDetail = DirectionDetail(),
) {

    val rotationDegree: Float = when (directionDetail.direction) {
        "left" -> {
            270f
        }
        "right" -> {
            90f
        }
        "top" -> {
            0f
        }
        "down" -> {
            180f
        }
        else -> {
            0f
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.Center,

        ) {

        Icon(
            painter = painterResource(id = R.drawable.arrow_up),
            contentDescription = "direction",
            tint = colorResource(id = R.color.bjs),
            modifier = Modifier
                .size(40.dp)
                .padding(
                    horizontal = 5.dp
                )
                .align(
                    alignment = Alignment.CenterVertically
                )
                .rotate(rotationDegree)
        )
        Text(
            text = directionDetail.message,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.blackSecondary),
            modifier = Modifier
                .padding(
                    horizontal = 10.dp,
                )
                .align(
                    alignment = Alignment.CenterVertically
                )
                .offset(
                    x = (-8).dp
                )
        )
    }
}




