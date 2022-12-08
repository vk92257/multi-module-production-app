package com.bjs.pdanative.presentation.screens.loginWorkflow.routeStatus.component

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bjs.pdanative.R
import com.bjs.pdanative.navigation.Route
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.components.header.Header
import com.bjs.pdanative.presentation.screens.common.ScanButton
import com.bjs.pdanative.presentation.screens.common.qrScanner.QRScanner
import com.bjs.pdanative.util.UiEvent

/**
 * @Author: Vivek
 * @Date: 09/11/21
 */

@Composable
fun RouteStatus(onNavigate: (UiEvent.Navigate) -> Unit) {
    val spacer = LocalSpacing.current
    var openScanner by remember {
        mutableStateOf(false)
    }
    val currentRoute by remember {
        mutableStateOf("B004")
    }
    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
    val annotatedString = buildAnnotatedString {
        append("Device :")
        withStyle(style = SpanStyle(Color.Black, fontWeight = FontWeight.Bold)) {
            append(" B008")
        }
    }
    if (!openScanner) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = colorResource(id = R.color.white)
                ),
        ) {
            Spacer(modifier = Modifier.size(spacer.spaceSmall))
            Header(
                title = stringResource(R.string.your_activity),
                backPressVisibility = false
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(CustomBrush.vGradientGrayWhite)
                    .fillMaxHeight()
                    .verticalScroll(
                        state = rememberScrollState()
                    )
            ) {
                Text(
                    text = "13/01 B-002",
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.blackSecondary),
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                )

                Spacer(modifier = Modifier.size(spacer.spaceExtraLarge))
                ScanButton(
                    modifier = Modifier.align(
                        alignment = Alignment.CenterHorizontally
                    ),
                    onScanButtonClick = {
                        openScanner = true
//                        onNavigate(UiEvent.Navigate(Route.RouteAssignConfirmationScreen))
                    }
                )
                Spacer(modifier = Modifier.size(spacer.spaceExtraLarge))
                Text(
                    text = annotatedString,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp),
                    style = MaterialTheme.typography.body1,
                    color = colorResource(id = R.color.textColor)
                )
            }
        }
    } else {
        QRScanner(onScan = {
            if (it == currentRoute) {
                onNavigate(UiEvent.Navigate(Route.RouteAssignConfirmationScreen))
            } else {
                onNavigate(UiEvent.Navigate(Route.RouteAssignConfirmationScreen))

                openScanner = false
            }
        })
    }

    BackHandler(openScanner) {
        if (openScanner) openScanner = false
        else dispatcher.onBackPressed()
    }
}


@Composable
@Preview
fun RouteStatusPreview() {
    RouteStatus(onNavigate = {})
}
