package com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen.ItemSize
import com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen.LargeSize
import com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen.Size
import com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen.toSize

/**
 * @Author: Vivek
 * @Date: 14/03/22
 */
@Composable
@Preview
fun UserProfileScreenComponent(
    image: String = "",
    modifier: Modifier = Modifier,
    itemSize: ItemSize = ItemSize.LargeItemSize(LargeSize()),
    editButtonClick: () -> Unit = {},
    showEditButton: Boolean = true,

    ) {
    var size: Size? = null

    when (itemSize) {
        is ItemSize.LargeItemSize -> {
            size = itemSize.largeSize.toSize()
        }

        is ItemSize.MediumItemSize -> {
            size = itemSize.mediumSize.toSize()
        }

        is ItemSize.SmallItemSize -> {
            size = itemSize.smallSize.toSize()
        }

    }



    Box(

        modifier = modifier.size(size.mainBoxHeight, size.mainBoxWidth),
        contentAlignment = Alignment.Center
    ) {


        Row(
            modifier = Modifier
                .align(
                    alignment = BottomStart
                )
                .padding(bottom = size.blackCirclePaddingBottom)
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier.size(size.blackCircleSize),
                backgroundColor = colorResource(id = R.color.blackSecondary)
            ) {}
        }


        Row(
            modifier = Modifier
                .align(
                    alignment = TopEnd
                )
                .padding(

                    vertical = size.yellowCirclePaddingVertical,
                    horizontal = size.yellowCirclePaddingHorizontal
                )
        ) {

            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(size.yellowCircleSize)
                    .align(alignment = CenterVertically),
                backgroundColor = colorResource(id = R.color.bjs),
            ) {}
        }



        Row {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(size.userImageSize)
                    .padding(1.dp),
                backgroundColor = colorResource(id = R.color.white),
                border = BorderStroke(
                    width = 4.dp, color = Color.White
                ),
                elevation = 5.dp
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = image)
                            .crossfade(true).error(R.drawable.temp_photo)
                            .fallback(R.drawable.temp_photo).build()
                    ), contentDescription = "bell", modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            shape = CircleShape
                        ), contentScale = ContentScale.Crop
                )
            }
        }


        if (showEditButton) {
            Box(
                modifier = Modifier
                    .align(
                        alignment = BottomEnd
                    )
                    .padding(
                        size.editButtonPadding
                    )
                    .clickable {
                        editButtonClick()
                    },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pencil),
                    contentDescription = "pencil",
                    modifier = Modifier

                        .size(size.editButtonIconSize)
                        .clip(CircleShape)
                        .background(
                            brush = CustomBrush.hGradientSkyBlueDarkBlue
                        )
                        .padding(size.editIconPadding),
                )
            }
        }


    }
}

@Composable
fun TermsAndConditionView(
    modifier: Modifier = Modifier,
    onCheckedChange: (state: Boolean) -> Unit,
) {

    var checked by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier, verticalAlignment = CenterVertically
    ) {
        Checkbox(
            checked = checked, onCheckedChange = {
                onCheckedChange(it)
                checked = it
            }, modifier = Modifier.border(
                border = BorderStroke(
                    width = 0.5.dp, color = colorResource(id = R.color.borderGrayLight)
                ),
            )
        )
        Text(
            text = "I agree to the Terms & Conditions",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 20.dp),
            color = colorResource(id = R.color.textSecondary)
        )
    }
}



