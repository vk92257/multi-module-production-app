package com.bjs.pdanative.presentation.screens.loginWorkflow.passcodVerification

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.core_ui.components.buttons.OutlinedCircularButton
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.bjs.pdanative.util.UiEvent

/**
 * @Author: Vivek
 * @Date: 05/04/22
 */
@Composable
fun PasscodeVerification(
    navigateUp: () -> Unit,
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
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = "Close",
            modifier = Modifier
                .align(alignment = Alignment.Start)
                .size(24.dp)
                .clickable {

                }
                .offset(
                    y = 16.dp,
                    x = 16.dp,
                ),
            tint = colorResource(id = R.color.black),

            )
        Spacer(modifier = Modifier.size(spacing.spaceExtraSmall))
        Text(
            text = stringResource(R.string.route_passcode).uppercase(),
            style = TypographyEvelethClean.h6,
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
            fontWeight = FontWeight.Medium,

            color = colorResource(id = R.color.black).copy(
                alpha = 0.7f
            )
        )
        Spacer(modifier = Modifier.size(spacing.spaceExtraSmall))
        Text(
            text = stringResource(R.string.verified).uppercase(),
            style = TypographyEvelethClean.h2,
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
            fontWeight = FontWeight.ExtraBold,
            color = colorResource(id = R.color.black),
        )
        Spacer(modifier = Modifier.size(spacing.spaceSmall))

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
        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
        {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animated_camera))
            val progress by animateLottieCompositionAsState(
                composition,
                iterations = LottieConstants.IterateForever,
                isPlaying = true,
                speed = 1.0f,
            )
            Card(
                modifier = Modifier
                    .offset(
                        y = 19.dp,
                    )
                    .size(70.dp)
                    .clip(CircleShape),
                shape = CircleShape,
                border = BorderStroke(
                    width = 3.dp,
                    color = Color.Black
                ),
                backgroundColor = Color(0xFF57DB7A)
            ) {}
            Image(
                painter = painterResource(id = R.drawable.ic_circle_plus),
                contentDescription = "van image ",
                modifier = Modifier
                    .size(23.dp)
                    .offset(
                        y = 20.dp,
                        x = 7.dp
                    )
                    .align(alignment = Alignment.TopEnd)
            )

            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .size(70.dp)
                    .offset(
                        y = 17.dp,
                    ),
                alignment = Alignment.Center,
                clipToCompositionBounds = true
            )
        }

        CircularButton(
            text = stringResource(id = R.string.add_your_photo).uppercase(),
            textSize = 16.sp,
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 30.dp
            ),
            onClick = {
                navigate(UiEvent.Navigate(Route.ProfileScreen))
            }
        )



        OutlinedCircularButton(
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
        )

        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))

    }
}


@Preview
@Composable
fun Preview() {
    PasscodeVerification(
        navigateUp = {},
        navigate = {}
    )
}