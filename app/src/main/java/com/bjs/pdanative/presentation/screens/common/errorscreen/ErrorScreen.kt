package com.bjs.pdanative.presentation.screens.common.errorscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.CircularButton
import com.core_ui.components.buttons.GradientButton
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean

@Composable
fun ScanErrorScreen(
    errorTitle: String,
    errorInfo: String,
    errorProblemMessage: String,
    onTryAgainClick: () -> Unit,
    onAddManuallyClick: () -> Unit,
    onCallDHClick: () -> Unit,
    whiteButtonText: String,
    outLinedButtonText: String,
    showFaultEntry: Boolean = false,
    index: Int = 0,
    damagedFault: String = "Wing Mirror"
) {
    val spacer = LocalSpacing.current



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = CustomBrush.vPinkRedGradient
            )
            .verticalScroll(
                state = rememberScrollState()
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.size(spacer.spaceLarge))

        Icon(
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                )
                .size(140.dp),
            painter = painterResource(id = R.drawable.ic_circle_exclamation),
            contentDescription = "Stock item scan error",
            tint = colorResource(id = R.color.white)
        )

        Spacer(modifier = Modifier.size(spacer.spaceLarge))

        Text(
            text = errorTitle,
            style = TypographyEvelethClean.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp
                ),
            textAlign = TextAlign.Center,
            letterSpacing = (0.5).sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white)
        )

        if (showFaultEntry) {
            Spacer(modifier = Modifier.size(spacer.spaceLarge))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {

                Box(
                    Modifier
                        .size(35.dp)
                        .background(colorResource(id = R.color.black), shape = CircleShape),
                ) {
                    Text(
                        text = index.toString().take(2),
                        maxLines = 1,
                        modifier = Modifier
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.white),
                    )
                }
                Text(
                    text = damagedFault,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(
                            horizontal = 10.dp
                        )
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.white)
                )

            }
        }




        Spacer(modifier = Modifier.size(spacer.spaceLarge))
        Text(
            text = errorInfo,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 30.dp
                ),
            textAlign = TextAlign.Center,
            letterSpacing = (0.5).sp,
            color = colorResource(id = R.color.light_white)
        )

        Spacer(modifier = Modifier.size(spacer.spaceLarge))
        CircularButton(
            modifier = Modifier.padding(
                horizontal = 30.dp
            ),
            text = whiteButtonText.uppercase(),
            buttonColor = colorResource(id = R.color.white),
            textColor = colorResource(id = R.color.red),
            onClick = {
                onTryAgainClick()
            }
        )
        Spacer(modifier = Modifier.size(spacer.spaceMedium))

        Button(
            border = BorderStroke(
                2.dp,
                color = colorResource(id = R.color.white)
            ),
            shape = RoundedCornerShape(
                60.dp
            ),
            elevation = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick = {
                onAddManuallyClick()
            },
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(
                    horizontal = 30.dp,
                )
        ) {
            Text(
                text = outLinedButtonText,
                fontWeight = FontWeight.Bold,
                style = TypographyEvelethClean.body1,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.size(spacer.spaceLarge))
        Text(
            text = errorProblemMessage,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 30.dp
                ),
            textAlign = TextAlign.Center,
            letterSpacing = (0.5).sp,
            color = colorResource(id = R.color.white)
        )

        Spacer(modifier = Modifier.size(spacer.spaceLarge))

        GradientButton(
            text = stringResource(id = R.string.contct_dh),
            gradient = CustomBrush.hGradientBlueSkyDarkBlue,
            buttonModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp),
            onclick = {
                onCallDHClick()
            }
        )
        Spacer(modifier = Modifier.size(spacer.spaceLarge))

    }


}

@Composable
@Preview
fun VehicleRegistrationScanErrorPreview() {
    ScanErrorScreen(
        errorTitle = stringResource(R.string.vehicleRegistrationErrorMessage),
        errorInfo = stringResource(R.string.vehicleRegistrationErrorInfo),
        errorProblemMessage = stringResource(R.string.havingProblem),
        onTryAgainClick = {},
        onAddManuallyClick = {},
        onCallDHClick = {},
        whiteButtonText = stringResource(id = R.string.try_again),
        outLinedButtonText = stringResource(id = R.string.add_manually)
    )
}