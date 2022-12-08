package com.bjs.pdanative.presentation.screens.preChecksWorkflow.verifyVehicleProcess.succesfullyVerifiedVehicle

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.*
import com.bjs.pdanative.R
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.CircularButtonWithIcon
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.bjs.pdanative.util.UiEvent

@Composable
fun SuccessfullyVerifiedVehicle(
    navigate: (UiEvent.Navigate) -> Unit
) {
    val spacing = LocalSpacing.current
    val scrollState = rememberScrollState()
    var progress by remember { mutableStateOf(0.1f) }
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 3000,
            easing = FastOutSlowInEasing
        )
    )
    while (progress <= 1f) {
        progress += 0.1f
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = scrollState,
                enabled = true,
                reverseScrolling = false
            )
            .background(
                brush = CustomBrush.vGradientYellowGreen
            )

    ) {

        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))
        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.ic_verified_screen),
                contentDescription = "circle",
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .size(300.dp)
            )

            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animated_circle))
            val progress by animateLottieCompositionAsState(
                composition,
                isPlaying = true,
                speed = 1.0f,
                iterations = LottieConstants.IterateForever
            )
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .size(360.dp),
                alignment = Alignment.Center,
                clipToCompositionBounds = true,
                renderMode = RenderMode.HARDWARE
            )
            Image(
                painter = painterResource(id = R.drawable.bjstwoman_van),
                contentDescription = "van image ",
                modifier = Modifier
                    .size(330.dp)
                    .offset(
                        y = 95.dp
                    )
                    .align(alignment = Alignment.BottomCenter)
            )
        }

        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))
        Text(
            text = stringResource(R.string.verified_your_vehicle).uppercase(),
            style = TypographyEvelethClean.h6,
            modifier = Modifier
                .padding(
                    horizontal = 18.dp
                )
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))

        /* OutlinedCircularButton(
             text = stringResource(id = R.string.skip_this_step).uppercase(),
             buttonColor = Color(0xFF57DB7A),
             borderColor = Color.Black,
             onclick = {
                 navigate(UiEvent.Navigate(Route.ProfileScreen))
             },
             modifier = Modifier
                 .padding(
                     vertical = 10.dp,
                     horizontal = 30.dp
                 )
                 .height(
                     60.dp
                 )
         )*/
        Spacer(modifier = Modifier.size(spacing.spaceLarge))
        CircularButtonWithIcon(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = stringResource(id = R.string.verify_stock).uppercase(),
            onclick = {
                navigate(UiEvent.Navigate(Route.VerifyStock))
            }

        )

        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))

    }
}

@Preview(showBackground = true)
@Composable
fun SuccessfullyVerifiedVehiclePreview() {
    SuccessfullyVerifiedVehicle(
        navigate = {}
    )
}