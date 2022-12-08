package com.bjs.pdanative.presentation.screens.preChecksWorkflow.assemblyKitProcess.assemblyKitVerificationCompleteScreen

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
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.bjs.pdanative.util.UiEvent
import kotlinx.coroutines.delay

@Composable
fun AssemblyKitSuccessfullyVerified(
    navigate: (UiEvent.Navigate) -> Unit
) {
    val spacing = LocalSpacing.current
    val scrollState = rememberScrollState()
    var progress by remember { mutableStateOf(0.1f) }

    LaunchedEffect(key1 = true, block = {
        delay(2000)
        navigate(UiEvent.Navigate(Route.AssemblyKitSummaryScreen))
    })

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
                painter = painterResource(id = R.drawable.ic_bjs_bag),
                contentDescription = "van image ",
                modifier = Modifier
                    .size(290.dp)
                    .offset(
                        y = 83.dp,
                        x = (10).dp
                    )
                    .align(alignment = Alignment.BottomCenter)
            )
        }

        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))
        Text(
            text = stringResource(id = R.string.assembly_kit_verification_success),
            style = TypographyEvelethClean.h6,
            modifier = Modifier
                .padding(
                    horizontal = 30.dp
                )
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))



        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))

    }
}

@Preview(showBackground = true)
@Composable
fun AssemblyKitSuccessfullyVerifiedPreview() {
    AssemblyKitSuccessfullyVerified(
        navigate = {}
    )
}