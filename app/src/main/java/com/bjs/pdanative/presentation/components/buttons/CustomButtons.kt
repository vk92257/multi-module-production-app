package com.bjs.pdanative.presentation.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean

/**
 * @Author: Vivek
 * @Date: 21/10/21
 */


@Composable
@Preview
fun CircularButtonWithIcon(
    modifier: Modifier = Modifier,
    textSize: TextUnit = 17.sp,
    buttonColor: Color = colorResource(id = R.color.blackSecondary),
    text: String = "VERIFY VEHICLE",
    onclick: (() -> Unit) = {},
    painterResources: Painter = painterResource(id = R.drawable.ic_shield_check)
) {

    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onclick,
            )
            .height(60.dp)
            .clip(
                shape = RoundedCornerShape(50.dp)
            )
            .background(color = buttonColor),
        horizontalArrangement = Arrangement.Center
    ) {

        Icon(
            painter = painterResources,
            contentDescription = "check",
            modifier = Modifier
                .padding(start = 20.dp, end = 8.dp)
                .align(
                    alignment = Alignment.CenterVertically
                )
                .size(25.dp),
            tint = colorResource(id = R.color.green)
        )

        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 20.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = TypographyEvelethClean.h6,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
@Preview
fun CircularButtonWithImage(
    modifier: Modifier = Modifier,
    textSize: TextUnit = 17.sp,
    buttonColor: Color = colorResource(id = R.color.blackSecondary),
    text: String = "VERIFY VEHICLE",
    onClick: (() -> Unit) = {},
    painterResources: Painter = painterResource(id = R.drawable.ic_bjs_van)
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,

                )
            .height(60.dp)
            .clip(
                shape = RoundedCornerShape(50.dp)
            )
            .background(color = buttonColor),
        horizontalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResources,
            contentDescription = "check",
            modifier = Modifier
                .padding(start = 20.dp, end = 8.dp)
                .align(
                    alignment = Alignment.CenterVertically
                )
                .size(45.dp),
        )

        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 20.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = TypographyEvelethClean.body2,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
@Preview
fun CircularButton(
    modifier: Modifier = Modifier,
    textSize: TextUnit = 17.sp,
    buttonColor: Color = colorResource(id = R.color.blackSecondary),
    text: String = "",
    textColor: Color = colorResource(id = R.color.white),
    onClick: (() -> Unit) = {}
) {
    Button(
        onClick = { onClick() },
        modifier = modifier.height(60.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(50.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(),
            color = textColor,
            fontWeight = FontWeight.Bold,
            style = TypographyEvelethClean.body1,
            fontSize = textSize,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun OutlinedCircularButton(
    modifier: Modifier = Modifier,
    textSize: TextUnit = 16.sp,
    buttonColor: Color = colorResource(id = R.color.black),
    text: String = "",
    borderColor: Color = Color.Gray,
    textColor: Color = Color.Black,
    borderWidth: Dp = 1.dp,
    onclick: (() -> Unit) = {}
) {
    Button(
        onClick = { onclick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
            contentColor = Color.White
        ),
        border = BorderStroke(
            color = borderColor,
            width = borderWidth
        ),
        shape = RoundedCornerShape(50.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(),
            color = textColor,
            fontWeight = FontWeight.Bold,
            style = TypographyEvelethClean.body1,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
@Preview
fun GradientButton(
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = colorResource(id = R.color.text_dark),
    onclick: () -> Unit = {},
    gradient: Brush = CustomBrush.hGradientSkyBlueDarkBlue,
    iconVisible: Boolean = true,
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        modifier = buttonModifier
            .fillMaxWidth()
            .height(60.dp),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(50.dp),
        onClick = { onclick() }) {
        Box(
            modifier = modifier
                .background(gradient)
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (iconVisible) Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                )
                Text(
                    text = text, color = textColor,
                    fontWeight = FontWeight.Bold,
                    style = TypographyEvelethClean.body1
                )

            }
        }
    }
}

@Composable
@Preview
fun GradientHalfButton(
    text: String = "",
    textColor: Color = colorResource(id = R.color.white),
    onclick: () -> Unit = {},
    gradient: Brush = CustomBrush.hGradientSkyBlueDarkBlue,
    iconVisible: Boolean = true,
    modifier: Modifier = Modifier,
    topRight: Dp = 50.dp,
    bottomRight: Dp = 50.dp,
    topLeft: Dp = 50.dp,
    bottomLeft: Dp = 50.dp
) {


    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        modifier = modifier
            //  .padding(horizontal = 30.dp, vertical = 10.dp)
            .height(60.dp),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(topRight, topLeft, bottomLeft, bottomRight),
        onClick = { onclick() }) {
        Box(
            modifier = modifier
                .background(gradient)
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (iconVisible) Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                )
                Text(
                    text = text, color = textColor,
                    fontWeight = FontWeight(1000),
                    fontSize = 20.sp,
                )

            }
        }
    }
}


@Composable
@Preview
fun CircularBlackButton(
    text: String = "", textColor: Color = Color.White,
    onclick: () -> Unit = {},
    gradient: Brush = CustomBrush.hGradientSkyBlueDarkBlue,
    iconVisible: Boolean = true,
    modifier: Modifier = Modifier
) {


    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 10.dp)
            .height(60.dp),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(50.dp),
        onClick = { onclick() }) {
        Box(
            modifier = modifier
                .background(color = colorResource(id = R.color.blackSecondary))
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (iconVisible) Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                )
                Text(
                    text = text, color = colorResource(id = R.color.white),
                    fontWeight = FontWeight(1000),
                    fontSize = 16.sp,
                )

            }
        }
    }
}


@Composable
@Preview
fun GradientSquareButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onclick: () -> Unit = {},
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        contentPadding = PaddingValues(),
        onClick = {
            onclick()
        }) {
        Box(
            modifier = Modifier
                .background(CustomBrush.hGradientYellowGreen)
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {

            Text(
                text = text.uppercase(),
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight(1000),
                style = MaterialTheme.typography.h5,
            )

        }
    }
}


@Composable
@Preview
fun LeftHalfOutlinedRoundButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String = "",
    borderStroke: BorderStroke = BorderStroke(
        2.dp,
        color = colorResource(id = R.color.text_dark).copy(alpha = 0.5f)
    ),
    textColor: Color = colorResource(id = R.color.text_dark).copy(alpha = 0.65f)
) {
    Button(
        border = borderStroke,
        shape = RoundedCornerShape(
            topEnd = 60.dp,
            bottomEnd = 60.dp
        ),
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white)),
        onClick = {
            onClick()
        },
        modifier = modifier
    ) {
        Text(
            color = textColor,
            text = text.uppercase(),
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
            style = TypographyEvelethClean.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
        )
    }


}


@Composable
@Preview
fun RightHalfRoundButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String = "",
    borderStroke: BorderStroke = BorderStroke(
        2.dp,
        color = colorResource(id = R.color.text_dark)
    )

) {
    Button(
        border = borderStroke,
        shape = RoundedCornerShape(
            topStart = 60.dp,
            bottomStart = 60.dp
        ),
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.text_dark)),
        onClick = {
            onClick()
        },
        modifier = modifier
    ) {
        Text(
            text = text.uppercase(),
            fontWeight = FontWeight.Bold,
            style = TypographyEvelethClean.body1,
            textAlign = TextAlign.Start,
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp)
        )

    }

}







