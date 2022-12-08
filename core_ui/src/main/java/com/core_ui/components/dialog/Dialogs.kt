package com.core_ui.components.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.core_ui.R
import com.core_ui.components.buttons.CircularButton

/**
 * @Author: Vivek
 * @Date: 08/11/21
 */


/*              CommonAlertDialogWitYesOrNo(
                onDismiss = {openDialog.value = false },
                onNegativeClick = { openDialog.value = false},
                onPositiveClick = {openDialog.value = false},
                title = "The Custom Dialog",
                message = "Are you sure, you want to enter on ignoring the risk",
                buttonPositiveText = "yes I'm Sure",
                negativeButtonText = "No",
                buttonNegativeColor = colorResource(id = R.color.error),
                buttonPositiveColor = colorResource(id = R.color.green),
                IconRId = R.drawable.ic_del
            )*/



@Composable
fun CommonAlertDialogWitYesOrNo(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit,
    title: String = "Title of the Dialog",
    message: String = "Message of the Dialog,That you want to show ",
    buttonPositiveText: String = "Yes",
    negativeButtonText: String = "No",
    buttonPositiveColor: Color = colorResource(id = R.color.bjs),
    buttonNegativeColor: Color = colorResource(id = R.color.black),
    IconRId: Int = R.drawable.bjssmall

) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = painterResource(id = IconRId),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(120.dp)
                )
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = message,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 2.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )


                Spacer(modifier = Modifier.height(10.dp))
                CircularButton(
                    text = buttonPositiveText,
                    buttonColor = buttonPositiveColor,
                    onClick = { onPositiveClick() })
                CircularButton(
                    text = negativeButtonText,
                    onClick = { onNegativeClick() },
                    buttonColor = buttonNegativeColor
                )
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
}


/*CommonAlertDialogWithMessage(
onDismiss = { openDialog.value = false },
onPositiveClick = { openDialog.value = false },
title = "Alert",
message = "Please enter your phone number",
buttonPositiveText = "Okay",
buttonPositiveColor = colorResource(id = R.color.green),
)*/

@Composable
fun CommonAlertDialogWithMessage(
    onDismiss: () -> Unit,
    onPositiveClick: () -> Unit,
    title: String = "Title of the Dialog",
    message: String = "Message of the Dialog,That you want to show ",
    buttonPositiveText: String = "Yes",
    buttonPositiveColor: Color = colorResource(id = R.color.bjs),
    IconRId: Int = R.drawable.bjssmall

) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = painterResource(id = IconRId),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(120.dp)
                )
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = message,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 2.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                CircularButton(
                    text = buttonPositiveText,
                    buttonColor = buttonPositiveColor,
                    onClick = { onPositiveClick() })

                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
}


/*CommonAlertDialogWithNoImage(
               onDismiss = { openDialog.value = false },
               onPositiveClick = { openDialog.value = false },
               title = "Alert",
               message = "Please enter your phone number",
               buttonPositiveText = "Okay",
               buttonPositiveColor = colorResource(id = R.color.green),
           )*/

@Composable
fun CommonAlertDialogWithNoImage(
    onDismiss: () -> Unit,
    onPositiveClick: () -> Unit,
    title: String = "Title of the Dialog",
    message: String = "Message of the Dialog,That you want to show ",
    buttonPositiveText: String = "Yes",
    buttonPositiveColor: Color = colorResource(id = R.color.bjs),

    ) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = message,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 2.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                CircularButton(
                    text = buttonPositiveText,
                    buttonColor = buttonPositiveColor,
                    onClick = { onPositiveClick() })

                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
}


@Composable
fun CustomDialogWithResultExample(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: (Color) -> Unit
) {
    var red by remember { mutableStateOf(0f) }
    var green by remember { mutableStateOf(0f) }
    var blue by remember { mutableStateOf(0f) }

    val color = Color(
        red = red.toInt(),
        green = green.toInt(),
        blue = blue.toInt(),
        alpha = 255
    )

    Dialog(onDismissRequest = onDismiss) {

        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        ) {

            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    text = "Select Color",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Color Selection
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {


                    Column {

                        Text(text = "Red ${red.toInt()}")
                        Slider(
                            value = red,
                            onValueChange = { red = it },
                            valueRange = 0f..255f,
                            onValueChangeFinished = {}
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Green ${green.toInt()}")
                        Slider(
                            value = green,
                            onValueChange = { green = it },
                            valueRange = 0f..255f,
                            onValueChangeFinished = {}
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Blue ${blue.toInt()}")
                        Slider(
                            value = blue,
                            onValueChange = { blue = it },
                            valueRange = 0f..255f,
                            onValueChangeFinished = {}
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            border = BorderStroke(1.dp, Color.DarkGray),
                            color = color,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                        ) {}
                    }
                }

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(onClick = onNegativeClick) {
                        Text(text = "CANCEL")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {
                        onPositiveClick(color)
                    }) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}
