package com.bjs.pdanative.presentation.screens.preChecksWorkflow.satNavProcess.satNavVerificationComplete

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.*
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean

/**
 * @Author: Vivek
 * @Date: 01/04/22
 */

@Composable
fun SatNavVerificationCompleteScreen(
    onProgressComplete: () -> Unit
) {
    val spacing = LocalSpacing.current
    val scrollState = rememberScrollState()
    var progress by remember { mutableStateOf(0.1f) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.successful_login_route_animation))
    val progressLottie by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f,
        isPlaying = true,
    )
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 3000,
            easing = FastOutSlowInEasing
        ),
        finishedListener = {
            onProgressComplete()
        }
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
                brush = CustomBrush.vGradientLgrayDgray,
            )

    ) {

        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))
        Text(
            text = "tech & tools",
            style = TypographyEvelethClean.h6,
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.white).copy(
                alpha = 0.7f
            ),
        )
        Spacer(modifier = Modifier.size(spacing.spaceExtraSmall))
        Text(
            text = "complete!",
            style = TypographyEvelethClean.h3,
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
            fontWeight = FontWeight.ExtraBold,
            color = colorResource(id = R.color.white),
        )
        Spacer(modifier = Modifier.size(spacing.spaceLarge))

        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.ic_complete_screen),
                contentDescription = "circle",
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .size(300.dp)
            )
            LottieAnimation(
                composition = composition,
                progress = progressLottie,
                modifier = Modifier
                    .size(200.dp)
                    .offset(
                        x = 50.dp,
                        y = 50.dp
                    ),
                alignment = Alignment.Center,
                clipToCompositionBounds = true,
                enableMergePaths = true,
                renderMode = RenderMode.HARDWARE
            )
            Image(
                painter = painterResource(id = R.drawable.ic_bjs_bag),
                contentDescription = "van image ",
                modifier = Modifier
                    .size(330.dp)
                    .offset(
                        x = (20).dp,
                        y = 95.dp
                    )
                    .align(alignment = Alignment.BottomCenter)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_satnav_new),
                contentDescription = "stock image",
                modifier = Modifier
                    .size(170.dp)
                    .offset(
                        y = (-15).dp,
                        x = (-94).dp
                    )
                    .align(alignment = Alignment.BottomCenter)
            )
        }
        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))

        Text(
            text = "Hang tight for the next step...",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                )
                .padding(
                    horizontal = 65.dp
                ),
            fontWeight = FontWeight.W300,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(spacing.spaceMedium))

        Text(
            text = "lgv check".uppercase(),
            style = TypographyEvelethClean.h5,
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                )
                .padding(
                    horizontal = 35.dp
                ),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))

        LinearProgressIndicator(
            backgroundColor = Color(0xFF30373A),
            progress = progressAnimation,
            color = colorResource(id = R.color.bjs),
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .padding(start = 60.dp, end = 60.dp)
                .align(
                    alignment = Alignment.CenterHorizontally
                )
                .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))
    }

}


@Composable
@Preview
fun SatNavVerificationCompleteScreenPreview() {
    SatNavVerificationCompleteScreen(
        onProgressComplete = {}
    )
}
