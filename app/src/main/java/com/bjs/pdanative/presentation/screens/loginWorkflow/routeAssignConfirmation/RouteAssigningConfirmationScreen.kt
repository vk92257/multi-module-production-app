package com.bjs.pdanative.presentation.screens.loginWorkflow.routeAssignConfirmation

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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjs.pdanative.R
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.CircularButton
import com.bjs.pdanative.util.UiEvent

/**
 * @Author: Vivek
 * @Date: 31/03/22
 */

@Composable
fun RouteAssigningConfirmationScreen(
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
                    .size(180.dp),

                )
        }



        Spacer(modifier = Modifier.size(spacing.spaceLarge))
        Text(
            text = "D143 R.Singh is not allocated \n to route 13/01 b-002",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .align(
                    alignment = CenterHorizontally
                )
                .padding(
                    horizontal = 20.dp
                ),
            fontWeight = FontWeight(1000),
            color = colorResource(id = R.color.error),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(spacing.spaceLarge))

        Text(
            text = "Would you like to continue signing \n in to route 13/01 B-002?",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(
                    alignment = CenterHorizontally
                )
                .padding(
                    horizontal = 30.dp
                ),
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.text_dark),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(spacing.spaceMedium))

        Text(
            text = "You will be issued with a login \n passcode if you continue.",
            style = MaterialTheme.typography.body1.copy(
                fontStyle = FontStyle.Italic
            ),
            lineHeight = 20.sp,
            modifier = Modifier
                .align(
                    alignment = CenterHorizontally
                )
                .padding(
                    horizontal = 35.dp
                ),
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.text_dark).copy(
                alpha = 0.7f,
            ),
            textAlign = TextAlign.Center
        )


        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))
        CircularButton(
            text = stringResource(id = R.string.yes).uppercase(),
            textSize = 16.sp,
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 30.dp
            ),
            onClick = {
                navigate(UiEvent.Navigate(Route.LoginRoute))
            }
        )

        Spacer(modifier = Modifier.size(spacing.spaceMedium))
        Text(
            text = "Cancel & return to route scan",
            style = MaterialTheme.typography.body1.copy(
                textDecoration = TextDecoration.Underline,
            ),
            modifier = Modifier
                .align(
                    alignment = CenterHorizontally
                )
                .clickable {
                    navigateUp()
                }
                .padding(
                    horizontal = 35.dp
                ),
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.text_dark).copy(
                alpha = 0.7f
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(spacing.spaceExtraLarge))

    }


}


@Composable
@Preview
fun RouteAssigningConfirmationScreenPreview() {
    RouteAssigningConfirmationScreen(
        navigate = {},
        navigateUp = {},
        scaffoldState = rememberScaffoldState()
    )
}