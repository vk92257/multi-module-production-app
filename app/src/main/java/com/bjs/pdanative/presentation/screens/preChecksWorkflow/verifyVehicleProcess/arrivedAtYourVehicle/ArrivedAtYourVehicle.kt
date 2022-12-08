package com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.arrivedAtYourVehicle

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.*
import com.bjs.pdanative.R
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.CircularButtonWithIcon
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.bjs.pdanative.presentation.screens.common.errorscreen.ScanErrorScreen
import com.bjs.pdanative.presentation.screens.common.qrScanner.QRScanner
import com.bjs.pdanative.util.UiEvent


@Composable
fun ArrivedAtYourVehicle(
    navigateUp: () -> Boolean,
    scaffoldState: ScaffoldState,
    navigate: (UiEvent.Navigate) -> Unit,
) {
    val modifier: Modifier = Modifier
    val spacer = LocalSpacing.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.water_wave))
    val progressLottie by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f,
        isPlaying = true,
    )
    val scrollState = rememberScrollState()
    var openScanner by rememberSaveable {
        mutableStateOf(false)
    }
    var openErrorScreen by rememberSaveable {
        mutableStateOf(false)
    }

    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
    if (!openScanner && !openErrorScreen) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(
                    state = scrollState,
                )
                .background(
                    brush = CustomBrush.vGradientGrayWhite
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "back-button",
                tint = colorResource(id = R.color.blackSecondary).copy(alpha = 0.7F),
                modifier = Modifier
                    .size(35.dp)
                    .padding(
                        start = 15.dp,
                        top = 15.dp
                    )
                    .clickable {
                        navigateUp()
                    }
            )

            Spacer(modifier = Modifier.size(spacer.spaceLarge))
            Text(
                text = stringResource(id = R.string.arrive_at_your_vehicle),
                modifier = Modifier.align(
                    alignment = Alignment.CenterHorizontally
                ),
                color = colorResource(id = R.color.blackSecondary).copy(
                    alpha = 0.7f
                ),
                style = TypographyEvelethClean.h6,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(id = R.string.vehicle),
                modifier = Modifier.align(
                    alignment = Alignment.CenterHorizontally
                ),
                color = colorResource(id = R.color.blackSecondary),
                style = TypographyEvelethClean.h3,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(spacer.spaceLarge))
            Box() {
                LottieAnimation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    composition = composition,
                    progress = progressLottie,
                    alignment = Alignment.Center,
                    clipToCompositionBounds = true,
                    enableMergePaths = true,
                    renderMode = RenderMode.HARDWARE
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_map_pin_icon),
                    contentDescription = "van image ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(180.dp)
                        .padding(horizontal = 20.dp)
                        .offset(
                            x = 5.dp,
                            y = (37).dp
                        )
                )
            }
            Image(
                painter = painterResource(id = R.drawable.bjstwoman_van),
                contentDescription = "van image ",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)

            )
            Spacer(modifier = Modifier.size(spacer.spaceExtraLarge))
            Text(
                text = stringResource(id = R.string.scan_and_verify_vehicle_reg),
                modifier = Modifier
                    .align(
                        alignment = Alignment.CenterHorizontally
                    )
                    .padding(horizontal = 20.dp),
                lineHeight = 20.sp,
                style = MaterialTheme.typography.body1,
                letterSpacing = (0.5).sp,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.blackSecondary)
            )

            Spacer(modifier = Modifier.size(spacer.spaceLarge))
            CircularButtonWithIcon(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = stringResource(id = R.string.verify_vehicle).uppercase(),
                onclick = {
                    openScanner = true
                }

            )
            Spacer(modifier = Modifier.size(spacer.spaceLarge))

        }
    } else if (openScanner) {
        QRScanner(
            onAddManualClick = {
                navigate(UiEvent.Navigate(Route.VerifyVehicle))
            }
        ) {
            if (it == "1234567") {

            } else {
                openScanner = false
                openErrorScreen = true
            }
        }
    } else if (openErrorScreen) {
        ScanErrorScreen(
            errorTitle = stringResource(R.string.vehicleRegistrationErrorMessage),
            errorInfo = stringResource(R.string.vehicleRegistrationErrorInfo),
            errorProblemMessage = stringResource(R.string.havingProblem),
            onTryAgainClick = {
                openScanner = true
                openErrorScreen = false
            },
            onAddManuallyClick = {
                navigate(UiEvent.Navigate(Route.VerifyVehicle))
            },
            onCallDHClick = {},
            whiteButtonText = stringResource(R.string.try_again),
            outLinedButtonText = stringResource(R.string.add_manually)
        )

    }

    BackHandler(openScanner || openErrorScreen) {
        if (openScanner) openScanner = false
        else if (openErrorScreen) openErrorScreen = false
        else dispatcher.onBackPressed()
    }

}


@Composable
@Preview
fun ArrivedAtYourVehiclePreview() {
    ArrivedAtYourVehicle(
        navigateUp = {
            false
        },
        scaffoldState = rememberScaffoldState(),
        navigate = {},
    )
}
