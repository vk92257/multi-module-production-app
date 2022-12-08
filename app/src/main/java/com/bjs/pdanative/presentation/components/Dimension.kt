package com.bjs.pdanative.presentation.components

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @Author: Vivek
 * @Date: 10/03/22
 */
data class Dimension(
    val default: Dp = 0.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceTen: Dp = 10.dp,
    val spaceTwelve: Dp = 12.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceEighteen: Dp = 18.dp,
    val spaceTwenty: Dp = 20.dp,
    val spaceTwentyTwo: Dp = 22.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceForty: Dp = 40.dp,
    val spaceExtraLarge: Dp = 64.dp,
    val spaceLargeExtreme: Dp = 84.dp
)

val LocalSpacing = compositionLocalOf { Dimension() }