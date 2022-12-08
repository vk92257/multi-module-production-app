package com.bjs.pdanative.presentation.screens.preChecksWorkflow.satNavProcess.setNavScanScreen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
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
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bjs.pdanative.R
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.core_ui.components.header.TitleHeader
import com.bjs.pdanative.presentation.screens.common.ScanButton
import com.bjs.pdanative.presentation.screens.common.errorscreen.ScanErrorScreen
import com.bjs.pdanative.presentation.screens.common.qrScanner.QRScanner
import com.bjs.pdanative.util.UiEvent


@Composable
fun SatNavScanScreen(
    scaffoldState: ScaffoldState,
    navigateUp: () -> Unit,
    navigate: (UiEvent.Navigate) -> Unit,
) {
    val spacer = LocalSpacing.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.scan))
    val progressLottie by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f,
        isPlaying = true,
    )
    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
    val scrollState = rememberScrollState()
    var openScanner by rememberSaveable {
        mutableStateOf(false)
    }
    val navAssetId: String by rememberSaveable() {
        mutableStateOf("21391487329")
    }
    var openErrorScreen by rememberSaveable {
        mutableStateOf(false)
    }

    if (!openScanner && !openErrorScreen) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomBrush.vGradientGrayWhite),
        ) {
            TitleHeader(
                title = stringResource(R.string.sat_nav).uppercase(),
                isShowTrailerIcon = false,
                textStyle = TypographyEvelethClean.h6,
                onHeadIconClick = {
                    navigateUp()
                }
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier

                    .fillMaxHeight()
                    .verticalScroll(
                        state = scrollState
                    )
            ) {
                Spacer(modifier = Modifier.size(spacer.spaceExtraLarge))
                Image(
                    painter = painterResource(id = R.drawable.ic_satnav_new),
                    contentDescription = "Scan Button",
                    modifier = Modifier
                        .size(200.dp)
                        .align(
                            alignment = Alignment.CenterHorizontally
                        )
                        .clickable {
                            navigate(UiEvent.Navigate(Route.SatNavAddManuallyScreen))
                        },
                )
                Spacer(modifier = Modifier.size(spacer.spaceMedium))
                Text(
                    text = stringResource(R.string.scan_sat_nav),
                    style = TypographyEvelethClean.body1,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.size(spacer.spaceMedium))
                Text(
                    text = stringResource(id = R.string.route_require_sat_nav),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.blackSecondary),
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.size(spacer.spaceExtraLarge))
                ScanButton(
                    modifier = Modifier.align(
                        alignment = Alignment.CenterHorizontally
                    )
                ) {
                    openScanner = true
                }
                Spacer(modifier = Modifier.size(spacer.spaceExtraLarge))
            }
        }
    } else if (openScanner) {
        QRScanner(
            onAddManualClick = {
                navigate(UiEvent.Navigate(Route.SatNavAddManuallyScreen))
            }
        ) {
            if (it == navAssetId) {

            } else {
                openScanner = false
                openErrorScreen = true
            }
        }
    } else if (openErrorScreen) {
        ScanErrorScreen(
            errorTitle = stringResource(R.string.sat_nav_scan_error_title),
            errorInfo = stringResource(R.string.sat_nav_scan_error_message),
            errorProblemMessage = stringResource(R.string.havingProblem),
            onTryAgainClick = {
                openScanner = true
                openErrorScreen = false
            },
            onAddManuallyClick = {
                navigate(UiEvent.Navigate(Route.SatNavAddManuallyScreen))
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
fun SatNavScanScreenPreview() {
    SatNavScanScreen(
        scaffoldState = rememberScaffoldState(),
        navigateUp = { },
        navigate = {}
    )

}


