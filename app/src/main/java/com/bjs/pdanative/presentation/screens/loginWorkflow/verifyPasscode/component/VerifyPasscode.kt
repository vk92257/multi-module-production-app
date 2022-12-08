package com.bjs.pdanative.presentation.screens.loginWorkflow.verifyPasscode.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bjs.pdanative.R
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.CircularButton
import com.core_ui.components.buttons.GradientButton
import com.bjs.pdanative.presentation.components.header.Header
import com.bjs.pdanative.presentation.screens.loginWorkflow.verifyPasscode.VerifyPasscodeScreenViewModel
import com.bjs.pdanative.util.UiEvent


/**
 * @Author: Vivek
 * @Date: 22/10/21
 */
@ExperimentalUnitApi
@Composable
fun VerifyScreen(
    onNavigateUp: () -> Unit,
    navigate: (UiEvent.Navigate) -> Unit,
    viewModel: VerifyPasscodeScreenViewModel = hiltViewModel()
) {
    val spacer = LocalSpacing.current
    Column(
        modifier = Modifier
            .background(brush = CustomBrush.vGradientGrayWhite)
            .fillMaxSize(),
    ) {

        Header(
            title = stringResource(id = R.string.verify_passcode).uppercase(),
            onBackPress = {
                onNavigateUp()
            }
        )
        Column(
            modifier = Modifier.verticalScroll(
                state = rememberScrollState(),

            ).background(
                brush = CustomBrush.vGradientGrayWhite
            ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Info(
                viewModel = viewModel
            )
            Spacer(modifier = Modifier.size(spacer.spaceExtraLarge))
            Column(
                modifier = Modifier
                    .padding(bottom = 22.dp)

            ) {
                CircularButton(
                    text = stringResource(id = R.string.verify).uppercase(),
                    modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
                    onClick = {
                        navigate(UiEvent.Navigate(Route.PasscodeVerification))
                    }
                )
                GradientButton(
                    text = stringResource(id = R.string.contct_dh),
                    buttonModifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 10.dp),

                    onclick = { },
                    gradient = CustomBrush.hGradientBlueSkyDarkBlue
                )

            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
@Preview
fun VerifyScreen() {
    VerifyScreen(
        onNavigateUp = {},
        navigate = {},
        viewModel = VerifyPasscodeScreenViewModel()
    )
}




