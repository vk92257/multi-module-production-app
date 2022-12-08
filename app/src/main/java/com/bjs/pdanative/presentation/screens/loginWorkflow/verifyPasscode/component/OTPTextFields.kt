package com.util.compose.presentation.verifyPasscode.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp

/**
 * @Author: Vivek
 * @Date: 25/10/21
 */

@Composable
fun OTPTextFields(
    modifier: Modifier = Modifier,
    length: Int,
    onFilled: (code: String) -> Unit
) {

    var code: List<Char> by remember { mutableStateOf(listOf()) }
    val focusRequesters: List<FocusRequester> = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(length) {
            temp.add(FocusRequester())
        }
        temp
    }

    Row(
        modifier = Modifier.height(50.dp)
    ) {

        (0 until length).forEach { index ->
            OutlinedTextField(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .focusOrder(focusRequester = focusRequesters[index]) {
                        focusRequesters[index + 1].requestFocus()
                    },
                textStyle = MaterialTheme.typography.body2.copy(
                    textAlign = TextAlign.Center, color = Color.Black
                ),
                singleLine = true,
                value = code.getOrNull(index = index)?.takeIf {
                    it.isDigit()
                }?.toString() ?: "",
                onValueChange = { value: String ->

                    if (focusRequesters[index].freeFocus()) {
                        val temp = code.toMutableList()
                        if (value == "") {
                            if (temp.size > index) {
                                temp.removeAt(index = index)
                                code = temp
                                focusRequesters.getOrNull(index - 1)?.requestFocus()
                            }
                        } else {
                            if (code.size > index) {
                                temp[index] = value.getOrNull(0) ?: ' '
                            } else {
                                temp.add(value.getOrNull(0) ?: ' ')
                                code = temp
                                focusRequesters.getOrNull(index + 1)?.requestFocus()
                                    ?: onFilled(
                                        code.joinToString(separator = "")
                                    )
                            }
                        }
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = PasswordVisualTransformation()
            )
//            }

            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}


@ExperimentalUnitApi
@Composable
fun OTPTextField(
    length: Int = 0,
    color: Color = Color.LightGray,
    onFilled: (code: String) -> Unit = {}
) {
//   val  onFilled: (code: String) -> Unit

    /*For storing the current state */
    var currentValue: List<Char> by remember {
        mutableStateOf(listOf())
    }

    var isEditAble: Boolean by remember {
        mutableStateOf(false)
    }

    /* For storing the current focus*/
    val focusRequesters: List<FocusRequester> = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(length) {
            temp.add(FocusRequester())
        }
        temp
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        (0 until length).forEach { index ->
            BasicTextField(
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true,
                    keyboardType = KeyboardType.NumberPassword,
                ),
                modifier = Modifier
                    .size(45.dp, 45.dp)
                    .border(width = 1.5.dp, color = color)
                    .focusOrder(focusRequester = focusRequesters[index]) {
                        focusRequesters[index + 1].requestFocus()
                    }
                    .align(alignment = Alignment.CenterVertically),
                singleLine = true,
                value = currentValue.getOrNull(index = index)?.takeIf {
                    it.isDigit()
                }?.toString() ?: "",
                onValueChange = { value: String ->
                    val temp = currentValue.toMutableList()
                    if (value == "") {
                        if (temp.size > index) {
                            temp.removeAt(temp.size - 1)
                            currentValue = temp
                            focusRequesters.getOrNull(index - 1)?.requestFocus()
                        }
                    } else {
                        if (currentValue.size > index) {
                            temp[index] = value.getOrNull(0) ?: ' '
                        } else {
                            temp.add(value.getOrNull(0) ?: ' ')
                            currentValue = temp
                            focusRequesters.getOrNull(index + 1)?.requestFocus()
                                ?: onFilled(
                                    currentValue.joinToString(separator = "")
                                )

                        }
                    }
                },
                visualTransformation = PasswordVisualTransformation(),
            )


        }
    }

}


@Composable
@Preview
fun CustomOtpScree() {

}


/*

@Composable
fun OTPTextField(
    length: Int = 0,
    color: Color = Color.LightGray,
    onFilled: (code: String) -> Unit = {}
) {
//   val  onFilled: (code: String) -> Unit

    */
/*For storing the current state *//*

    var currentValue: List<Char> by remember {
        mutableStateOf(listOf())
    }

    var isEditAble: Boolean by remember {
        mutableStateOf(false)
    }

    */
/* For storing the current focus*//*

    val focusRequesters: List<FocusRequester> = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(length) {
            temp.add(FocusRequester())
        }
        temp
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {


        (0 until length).forEach { index ->
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true,
                    keyboardType = KeyboardType.NumberPassword,
                ),
                modifier = Modifier
                    .background(Color.Transparent)
                    .size(42.dp, 42.dp)
                    .border(width = 2.dp, color = color)
                    .focusOrder(focusRequester = focusRequesters[index]) {
                        focusRequesters[index + 1].requestFocus()
                    },
                textStyle = MaterialTheme.typography.body2.copy(
                    textAlign = TextAlign.Center, color = Color.Black
                ),
                singleLine = true,
                value = currentValue.getOrNull(index = index)?.takeIf {
                    it.isDigit()
                }?.toString() ?: "",
                onValueChange = { value: String ->
                    val temp = currentValue.toMutableList()
                    if (value == "") {
                        if (temp.size > index) {
//                            temp.removeAt(index = index)

                            temp.removeAt(temp.size - 1)
                            currentValue = temp

                            focusRequesters.getOrNull(index - 1)?.requestFocus()
                        }
                    } else {
                        if (currentValue.size > index) {
                            temp[index] = value.getOrNull(0) ?: ' '
                        } else {
                            temp.add(value.getOrNull(0) ?: ' ')
                            currentValue = temp
                            focusRequesters.getOrNull(index + 1)?.requestFocus()
                                ?: onFilled(
                                    currentValue.joinToString(separator = "")
                                )

                        }
                    }
                },
                visualTransformation = PasswordVisualTransformation()
            )


        }
    }

}


*/
