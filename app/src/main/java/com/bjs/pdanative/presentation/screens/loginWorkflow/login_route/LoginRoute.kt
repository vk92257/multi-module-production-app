package com.bjs.pdanative.presentation.screens.loginWorkflow.login_route

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjs.pdanative.R
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.presentation.components.Constants.LOG_IN_TO_ROUTE
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.CircularButton
import com.core_ui.components.dialog.CommonAlertDialogWithMessage
import com.core_ui.components.header.Header
import com.bjs.pdanative.util.UiEvent

/**
 * @Author: Vivek
 * @Date: 02/11/21
 */
@Composable
fun LoginRoute(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onNavigateUp: () -> Unit,
) {
    val openDialog = remember { mutableStateOf(false) }
    val spacer = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = CustomBrush.vGradientGrayWhite
            )
    ) {

        var phoneNumber: String by rememberSaveable {
            mutableStateOf("")
        }
        Header(title = LOG_IN_TO_ROUTE, onBackPress = {
            onNavigateUp()
        })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState()
                )
                .weight(4f, true)
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.login_using_number),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp, vertical = 40.dp),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.blackSecondary)
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                    },
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    ),
                    placeholder = {
                        Text(
                            textAlign = TextAlign.Center,
                            text = stringResource(id = R.string.enter_phone_number),
                            fontSize = 18.sp,
                            style = TextStyle(
                                color = colorResource(id = R.color.blackSecondary),
                            ), modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth()
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(
                            horizontal = 50.dp
                        ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    maxLines = 1,
                    shape = RoundedCornerShape(4.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.textColor),
                        unfocusedBorderColor = colorResource(id = R.color.textColor)
                    )
                )
            }

            Spacer(modifier = Modifier.size(spacer.spaceLarge))
            CircularButton(
                text = stringResource(id = R.string.log_in).uppercase(),
                onClick = {
                    if (phoneNumber.isEmpty())
                        openDialog.value = true
                    else onNavigate(UiEvent.Navigate(Route.VerifyPasscode))
                }, modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 12.dp)
            )

        }
        if (openDialog.value) {
            CommonAlertDialogWithMessage(
                onDismiss = { openDialog.value = false },
                onPositiveClick = { openDialog.value = false },
                title = "Alert",
                message = "Please enter your phone number",
                buttonPositiveText = "Okay",
                buttonPositiveColor = colorResource(id = R.color.green),
            )
        }

        Spacer(modifier = Modifier.size(spacer.spaceMedium))

    }
}

@Composable
@Preview
fun LoginRoutePreview() {
    LoginRoute(
        onNavigate = {},
        onNavigateUp = {},

        )
}

