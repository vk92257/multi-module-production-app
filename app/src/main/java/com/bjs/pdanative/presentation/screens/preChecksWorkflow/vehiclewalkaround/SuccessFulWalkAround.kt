package com.bjs.pdanative.presentation.screens.preChecksWorkflow.vehiclewalkaround

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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

@Composable
@Preview
fun SuccessFulWalkAround(
    onProgressComplete: () -> Unit = {},
) {
    val spacer = LocalSpacing.current
    val scrollState = rememberScrollState()

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.successful_login_route_animation))
    val progressLottie by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f,
        isPlaying = true,
    )

    var progress by rememberSaveable { mutableStateOf(0.1f) }
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
        Spacer(modifier = Modifier.size(spacer.spaceExtraLarge))
        Text(
            text = stringResource(R.string.vehicle_walk_around),
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
            style = TypographyEvelethClean.h6,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.color_successful_heading).copy(alpha = 0.7f),
        )
        Text(
            text = stringResource(R.string.walk_around_complete).uppercase(),
            style = TypographyEvelethClean.h3,
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.color_successful_heading),
        )
        Spacer(modifier = Modifier.size(spacer.spaceLarge))
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

        Spacer(modifier = Modifier.size(spacer.spaceExtraLarge))

        Text(
            text = stringResource(R.string.hang_tight),
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(spacer.spaceEighteen))

        Text(
            text = stringResource(R.string.tech_tools),
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                )
                .padding(
                    horizontal = 35.dp
                ),
            style = TypographyEvelethClean.h5,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(spacer.spaceExtraLarge))

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

        Spacer(modifier = Modifier.size(spacer.spaceExtraLarge))
    }
}