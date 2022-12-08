package com.bjs.pdanative.presentation.components

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * @Author: Vivek
 * @Date: 11/03/22
 */
object CustomBrush {


    val hGradientSkyBlueAndBlue = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF83EFF4),
            Color(0xFF6396E5)
        )
    )

    val hGradientGreenAndLightGreen = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFB6DC69),
            Color(0xFF57DB7A)
        )
    )

    val lGradientGreenAndLightGreen = Brush.linearGradient(
        colors = listOf(
            Color(0xFFB6DC69),
            Color(0xFF57DB7A)
        )
    )


    val vGradientLgrayDgray = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF758588),
            Color(0xFF30373A),
        )
    )

    val vGradientGrayWhite = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFD5D5CC),
            Color(0xFFFFFFFF),
            Color(0xFFF5F0EA)
        )
    )

    val vGradientGrayUnSelected = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFD5D5CC),
            Color(0xFFD5D5CC),
            Color(0xFFD5D5CC),
        )
    )

    val hGradientYellowGreen = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFB6DC69),
            Color(0xFF57DB7A),
            Color(0xFF57DB7A)
        )
    )

    val hGradientYellowAndGreen = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFB6DC69),
            Color(0xFF57DB7A)
        )
    )

    val vGradientYellowGreen = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFB6DC69),
            Color(0xFF57DB7A),
            Color(0xFF57DB7A)
        )
    )

    val hGradientSkyBlueDarkBlue = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF83EFF4),
            Color(0xFF6396E5),

            )
    )

    val hGradientBlueSkyDarkBlue = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF55F5DF),
            Color(0xFF55F5DF),
            Color(0xFF55CFF5),

            )
    )


    val hOrangeRedGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFE95252),
            Color(0xFFDC69AE),
        )
    )

    val bjsYellowGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFDBB30),
            Color(0xFFFEA76D),
        )
    )


    val vPinkRedGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFDC69AE),
            Color(0xFFE95252),
        )
    )

}