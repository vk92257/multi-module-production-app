package com.bjs.pdanative.presentation.screens.loginWorkflow.verifyPasscode.component

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.bjs.pdanative.presentation.screens.loginWorkflow.verifyPasscode.VerifyPasscodeScreenViewModel
import com.bjs.pdanative.presentation.ui.otpview.OnOtpCompletionListener
import com.bjs.pdanative.presentation.ui.otpview.OtpView

/**
 * @Author: Vivek
 * @Date: 21/10/21
 */

@SuppressLint("ResourceAsColor")
@ExperimentalUnitApi
@Composable

fun Info(
    modifier: Modifier = Modifier,
    viewModel: VerifyPasscodeScreenViewModel
) {
    val context = LocalContext.current
    var passcodeState: Int by remember {
        mutableStateOf(0)
    }

    var OTPViewColor: Long by remember {
        mutableStateOf(0xFF857B6E)
    }

    var currentColor: Color by remember {
        mutableStateOf(Color.Black)
    }

    var currentText: String by remember {
        mutableStateOf("PASSCODE")
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.you_are_signing_into),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp
                ),
            color = colorResource(id = R.color.text_dark).copy(
                alpha = 0.7f
            ),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )
        Text(
            text = "13/01 B-002",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            style = MaterialTheme.typography.h6,
            color = colorResource(id = R.color.text_dark),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )



        if (!viewModel.isPassCodeSent.value)

            Spacer(modifier = modifier.height(LocalSpacing.current.spaceSmall))

        Text(
            text = stringResource(id = R.string.passcode_sent_message),
            lineHeight = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            color = colorResource(id = R.color.text_dark),
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
        )
        if (viewModel.isPassCodeSent.value)
            Text(
                text = currentText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 30.dp),
                color = currentColor,
                fontWeight = FontWeight.Bold,
                style = TypographyEvelethClean.h6 ,
                textAlign = TextAlign.Center,
            )

        val otpView = OtpView(context)
        with(otpView) {
            itemCount = 6
            itemSpacing = 30
            itemRadius = 5
            textSize = 10f
            inputType = 0x00000012
            setLineColor(ColorStateList.valueOf(OTPViewColor.toInt()))

        }

        otpView.setOtpCompletionListener(object : OnOtpCompletionListener {
            override fun onOtpCompleted(otp: String?) {
                viewModel.text.value = otp.toString()
                viewModel.isPassCodeSent.value = true
                if (otp == "123456") {
                    passcodeState = 2
                    currentText = "PASSCODE CORRECT"
                    currentColor = Color.Green
                    OTPViewColor = 0xFF00FF00
                    otpView.setLineColor(ColorStateList.valueOf(OTPViewColor.toInt()))
                } else {
                    currentText = "PASSCODE INCORRECT"
                    passcodeState = 1
                    currentColor = Color.Red
                    OTPViewColor = 0xfffF5454
                    otpView.setLineColor(ColorStateList.valueOf(OTPViewColor.toInt()))

                }
            }
        })
        AndroidView(
            { otpView },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        )
        Text(
            text = "Resend Passcode",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, bottom = 35.dp),
            color = colorResource(id = R.color.textColor),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
        )

    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
@Preview
fun InfoPreview() {
    Info(
        viewModel = VerifyPasscodeScreenViewModel(),
        modifier = Modifier.background(
            color = Color.White
        )
    )
}