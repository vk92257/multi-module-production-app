package com.bjs.pdanative.presentation.screens.loginWorkflow.driverLoginConfirmation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjs.pdanative.R
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.CircularButton
import com.core_ui.components.buttons.GradientButton
import com.bjs.pdanative.util.UiEvent

/**
 * @Author: Vivek
 * @Date: 31/03/22
 */

@Composable
fun DriverLoginConfirmationScreen(
    navigate: (UiEvent.Navigate) -> Unit,
    navigateUp: () -> Unit,
    scaffoldState: ScaffoldState
) {
    val spacing = LocalSpacing.current
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = scrollState,
                enabled = true,
                reverseScrolling = false
            )
            .background(
                brush = CustomBrush.vGradientGrayWhite,
            )

    ) {
        Icon(
            imageVector = Icons.Default.Close, contentDescription = "Close",
            modifier = Modifier

                .align(alignment = Alignment.End)
                .size(60.dp)
                .padding(10.dp)
                .clickable {
                    navigateUp()
                },
            tint = colorResource(id = R.color.semi_gray),

            )
        Spacer(modifier = Modifier.size(spacing.spaceSmall))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally)
        ) {


            Icon(
                painter = painterResource(id = R.drawable.ic_route_vector),
                contentDescription = "route_Image",
                modifier = Modifier.align(
                    alignment = Center
                )
            )

            Image(
                painter = painterResource(id = R.drawable.ic_circle_exclamation),
                contentDescription = "Close",
                modifier = Modifier
                    .clip(
                        CircleShape
                    )
                    .align(
                        alignment = Center
                    )
                    .background(
                        color = colorResource(id = R.color.newLightGray)
                    )
                    .size(200.dp),

                )
        }



        Spacer(modifier = Modifier.size(spacing.spaceLarge))
        Text(
            text = stringResource(R.string.sign_in_error_info_message),
            lineHeight = 30.sp,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .align(
                    alignment = CenterHorizontally
                )
                .padding(
                    horizontal = 25.dp
                ),
            fontWeight = FontWeight(1000),
            color = colorResource(id = R.color.error),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(spacing.spaceMedium))

        Text(
            text = stringResource(R.string.sing_in_error_req_message),
            style = MaterialTheme.typography.body1,
            lineHeight = 20.sp,
            modifier = Modifier
                .align(
                    alignment = CenterHorizontally
                )
                .padding(
                    horizontal = 65.dp
                ),
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.text_dark),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(spacing.spaceMedium))

        Text(
            text = stringResource(R.string.sign_in_error_buttons_info),
            style = MaterialTheme.typography.body1.copy(
                fontStyle = FontStyle.Italic
            ),
            lineHeight = 20.sp,
            modifier = Modifier
                .align(
                    alignment = CenterHorizontally
                )
                .padding(
                    horizontal = 25.dp
                ),
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.text_dark).copy(
                alpha = 0.7f
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(spacing.spaceMedium))
        CircularButton(
            text = stringResource(id = R.string.add_another_user).uppercase(),
            textSize = 16.sp,
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 30.dp
            ),
            onClick = {
                navigate(UiEvent.Navigate(Route.ProfileScreen))
            }
        )


        GradientButton(
            text = stringResource(id = R.string.continue_text).uppercase(),
            gradient = CustomBrush.hGradientYellowGreen,
            buttonModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp),
            iconVisible = false,
            onclick = {
                navigate(UiEvent.Navigate(Route.SuccessfulSignInScreen))
            }
        )

        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))

    }


}


@Preview
@Composable
fun DriverLoginConfirmationScreenPreview() {

    DriverLoginConfirmationScreen(
        navigate = {},
        navigateUp = {},
        scaffoldState = rememberScaffoldState()
    )
}