package com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//
// Created by gaurav on 30/03/22.
// Copyright (c) 2022 gc. All rights reserved.
//

sealed class ItemSize {

    data class SmallItemSize(val smallSize: SmallSize) : ItemSize()
    data class MediumItemSize(val mediumSize: MediumSize) : ItemSize()
    data class LargeItemSize(val largeSize: LargeSize) : ItemSize()


}

data class Size(
    var mainBoxHeight: Dp,
    var mainBoxWidth: Dp,
    var blackCircleSize: Dp,
    var blackCirclePaddingBottom: Dp,
    var yellowCircleSize: Dp,
    var yellowCirclePaddingVertical: Dp,
    var yellowCirclePaddingHorizontal: Dp,
    var userImageSize: Dp,
    var editButtonPadding: Dp,
    var editButtonIconSize: Dp,
    var editIconPadding: Dp


)


data class LargeSize(
    val mainBoxHeight: Dp = 250.dp,
    val mainBoxWidth: Dp = 250.dp,
    val blackCircleSize: Dp = 120.dp,
    val blackCirclePaddingBottom: Dp = 40.dp,
    val yellowCircleSize: Dp = 60.dp,
    val yellowCirclePaddingVertical: Dp = 60.dp,
    val yellowCirclePaddingHorizontal: Dp = 15.dp,
    val userImageSize: Dp = 200.dp,
    val editButtonPadding: Dp = 23.dp,
    val editButtonIconSize: Dp = 55.dp,
    val editIconPadding: Dp = 17.dp

)

data class MediumSize(
    val mainBoxHeight: Dp = 250.dp,
    val mainBoxWidth: Dp = 250.dp,
    val blackCircleSize: Dp = 120.dp,
    val blackCirclePaddingBottom: Dp = 40.dp,
    val yellowCircleSize: Dp = 60.dp,
    val yellowCirclePaddingVertical: Dp = 60.dp,
    val yellowCirclePaddingHorizontal: Dp = 15.dp,
    val userImageSize: Dp = 200.dp,
    val editButtonPadding: Dp = 23.dp,
    val editButtonIconSize: Dp = 55.dp,
    val editIconPadding: Dp = 17.dp

)

data class SmallSize(
    val mainBoxHeight: Dp = 80.dp,
    val mainBoxWidth: Dp = 80.dp,
    val blackCircleSize: Dp = 40.dp,
    val blackCirclePaddingBottom: Dp = 8.dp,
    val yellowCircleSize: Dp = 20.dp,
    val yellowCirclePaddingVertical: Dp = 10.dp,
    val yellowCirclePaddingHorizontal: Dp = 4.dp,
    val userImageSize: Dp = 75.dp,
    val editButtonPadding: Dp = 11.dp,
    val editButtonIconSize: Dp = 27.dp,
    val editIconPadding: Dp = 8.dp

)