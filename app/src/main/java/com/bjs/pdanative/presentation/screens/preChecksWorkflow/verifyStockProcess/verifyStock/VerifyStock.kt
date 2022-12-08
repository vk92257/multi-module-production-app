package com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyStockProcess.verifyStock

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import com.core_ui.components.buttons.CircularButton
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.bjs.pdanative.presentation.components.header.Header
import com.bjs.pdanative.presentation.screens.common.errorscreen.ScanErrorScreen
import com.bjs.pdanative.presentation.screens.common.qrScanner.QRScanner
import com.bjs.pdanative.util.UiEvent


@Composable
fun VerifyStock(
    navigateUp: () -> Unit,
    scaffoldState: ScaffoldState,
    navigate: (UiEvent.Navigate) -> Unit
) {
    var phoneNumber: String by rememberSaveable() {
        mutableStateOf("21391487329")
    }
    val modifier: Modifier = Modifier
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
    var openErrorScreen by rememberSaveable {
        mutableStateOf(false)
    }


    if (!openScanner && !openErrorScreen) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(
                    state = scrollState,
                )
                .background(
                    brush = CustomBrush.vGradientGrayWhite
                ),
        ) {
            Header(
                backPressVisibility = false,
                iconVisibility = false,
                title = stringResource(R.string.verify_stock),
                modifier = Modifier.offset(
                    y = (-8).dp
                )
            )
            Column(
                modifier = Modifier

                    .padding(
                        horizontal = 10.dp,
                        vertical = 215.dp
                    )
                    .fillMaxWidth()
                    .align(
                        alignment = Alignment.TopCenter
                    )
                    .clip(
                        shape = RoundedCornerShape(5.dp),
                    )
                    .wrapContentHeight()
                    .background(
                        color = colorResource(id = R.color.trams_black)
                    ),
            ) {
                Spacer(modifier = Modifier.size(spacer.spaceExtraLarge))
                Text(
                    text = stringResource(R.string.enter_stock_barcode_manually),
                    modifier = Modifier.align(
                        alignment = Alignment.CenterHorizontally
                    ), style = TypographyEvelethClean.body1,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(spacer.spaceMedium))

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                    },
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    ),

                    modifier = Modifier
                        .padding(
                            horizontal = 50.dp
                        )
                        .align(Alignment.CenterHorizontally)
                        .background(
                            color = colorResource(id = R.color.white)
                        )
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Ascii
                    ),
                    maxLines = 1,
                    shape = RoundedCornerShape(4.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.white),
                        unfocusedBorderColor = colorResource(id = R.color.white)
                    )
                )


                Spacer(modifier = Modifier.size(spacer.spaceSmall))
                Row(
                    modifier = Modifier.align(
                        alignment = Alignment.CenterHorizontally
                    )
                ) {

                    LottieAnimation(
                        modifier = Modifier
                            .align(
                                alignment = Alignment.CenterVertically
                            )
                            .size(
                                width = 200.dp,
                                height = 200.dp
                            )
                            .clickable {
                                openScanner = true
                            },
                        composition = composition,
                        progress = progressLottie,
                        alignment = Alignment.Center,
                        clipToCompositionBounds = true,
                        enableMergePaths = true,
                        renderMode = RenderMode.HARDWARE
                    )


                    Text(
                        text = stringResource(R.string.tap_to_scan).uppercase(),
                        modifier = Modifier
                            .align(
                                alignment = Alignment.CenterVertically
                            )
                            .offset(
                                x = (-38).dp
                            ), style = TypographyEvelethClean.body1,
                        fontWeight = FontWeight.Bold
                    )

                }
                Spacer(modifier = Modifier.size(spacer.spaceSmall))

            }

            Image(
                painter = painterResource(id = R.drawable.ic_verify_stock_image),
                contentDescription = "van image ",
                modifier = Modifier
                    .size(150.dp)
                    .offset(
                        y = 115.dp
                    )
                    .align(
                        alignment = Alignment.TopCenter
                    )
            )

            CircularButton(
                modifier = Modifier
                    .align(
                        alignment = Alignment.BottomCenter
                    )
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 40.dp),
                onClick = {
                    navigate(UiEvent.Navigate(Route.SuccessfullyVerifiedStockScreen))
                },
                text = stringResource(R.string.add_stock_barcode)
            )

        }
    } else if (openScanner) {
        QRScanner(
            onAddManualClick = {
                openScanner = false
            }
        ) {
            if (it == "https://www.qr-code-generator.com/") {
                navigate(UiEvent.Navigate(Route.SuccessfullyVerifiedStockScreen))
            } else {
                openScanner = false
                openErrorScreen = true
            }
        }
    } else if (openErrorScreen) {
        ScanErrorScreen(
            errorTitle = stringResource(R.string.stockScanErrorTitle),
            errorInfo = stringResource(R.string.stockScanErrorInfo),
            errorProblemMessage = stringResource(R.string.havingProblem),
            onTryAgainClick = {
                openScanner = true
                openErrorScreen = false
            },
            onAddManuallyClick = {
                openErrorScreen = false
            },
            onCallDHClick = {},
            whiteButtonText = stringResource(id = R.string.try_again),
            outLinedButtonText = stringResource(id = R.string.add_manually)
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
fun VerifyStockPreview() {
    VerifyStock(
        scaffoldState = rememberScaffoldState(),
        navigate = {},
        navigateUp = {}
    )
}