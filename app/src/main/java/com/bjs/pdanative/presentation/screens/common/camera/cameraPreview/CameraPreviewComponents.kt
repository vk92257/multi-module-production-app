package com.bjs.pdanative.presentation.screens.common.camera.cameraPreview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bjs.pdanative.R
import com.bjs.pdanative.domain.models.camera.ImageMetaData
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.components.WindowInfo
import com.bjs.pdanative.presentation.components.rememberWindowInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Author: Vivek
 * @Date: 11/04/22
 */

@Composable
fun Yes(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    iconSize: Dp = 30.dp
) {
    Column(
        modifier = modifier
            .size(50.dp)
            .background(
                brush = CustomBrush.hGradientGreenAndLightGreen,
                shape = CircleShape
            )
            .clip(
                RoundedCornerShape(100.dp)
            )
            .clickable {
                onClick()
            },
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = "Check",
            modifier = Modifier
                .size(
                    size = iconSize
                )
                .align(
                    alignment = Alignment.CenterHorizontally
                )
                .absoluteOffset(
                    x = (-25).dp
                )
        )

    }
}

@Composable
fun No(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    iconSize: Dp = 30.dp
) {
    Column(
        modifier = modifier
            .size(50.dp)
            .background(
                brush = CustomBrush.hGradientSkyBlueDarkBlue,
                shape = CircleShape
            )
            .clip(
                RoundedCornerShape(100.dp)
            )
            .clickable {
                onClick()
            },
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_rotate_right),
            contentDescription = "Check",
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                )
                .absoluteOffset(
                    x = 25.dp
                )
                .size(iconSize)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    list: List<ImageMetaData> = emptyList(),
    onClick: (Int) -> Unit = {},
    onDeleteClick: (Int) -> Unit = {},
    listSize: Int = 0,
    isForSingleImage: Boolean = true,
    bottomBarContent: @Composable () -> Unit,
    onRetryClick: (Int) -> Unit = {},
) {

    val screenInfo = rememberWindowInfo()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(
            topEnd = 25.dp,
            topStart = 25.dp,
        ),
        modifier = modifier.clickable(
            indication = null ,
            interactionSource =  remember { MutableInteractionSource() },

            onClick = {
                scope.launch {

                    if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    } else {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }

                }
            }
        ),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            if (!isForSingleImage) {
                LazyColumn {
                    itemsIndexed(list) { position, item ->
                        if (screenInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
                            VerticalImageItem(
                                onClick = {
                                    scope.launch {
                                        bottomSheetScaffoldState.bottomSheetState.collapse()
                                        delay(100)
                                        onClick(position)
                                    }
                                },
                                onDeleteClick = {
                                    onDeleteClick(position)
                                },
                                position = position,
                                onReloadClick = {
                                    scope.launch {
                                        bottomSheetScaffoldState.bottomSheetState.collapse()
                                        delay(100)
                                        onRetryClick(position)
                                    }
                                },
                                item = item.imageUrl,
                                date = item.date,
                                time = item.time
                            )
                        } else {
                            HorizontalImageItem(
                                onClick = {
                                    scope.launch {
                                        bottomSheetScaffoldState.bottomSheetState.collapse()
                                        delay(100)
                                        onClick(position)
                                    }
                                },
                                onDeleteClick = {
                                    onDeleteClick(position)
                                },
                                position = position,
                                onReloadClick = {
                                    scope.launch {
                                        bottomSheetScaffoldState.bottomSheetState.collapse()
                                        delay(100)
                                        onRetryClick(position)
                                    }
                                },
                                item = item.imageUrl,
                                time = item.time,
                                date = item.date,
                            )
                        }
                    }
                }
            }
        },
        sheetPeekHeight = if (!isForSingleImage) 17.dp else 0.dp,
    ) {
        bottomBarContent()
    }
}

@Composable
fun VerticalImageItem(
    onClick: () -> Unit = {},
    onDeleteClick: (Int) -> Unit = {},
    position: Int = 1,
    date: String = "Mon 15 Mar 21",
    time: String = "06:44:21",
    onReloadClick: () -> Unit = {},
    item: String = ""
) {
    val spacing = LocalSpacing.current
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 10.dp
                )
                .size(180.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "delete",
                modifier = Modifier
                    .align(
                        alignment = Alignment.CenterVertically,
                    )
                    .size(40.dp)
                    .padding(5.dp)
                    .weight(1.5f)
                    .clickable {
                        onReloadClick()
                    },
                tint = colorResource(id = R.color.sky_blue)
            )
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = item)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            error(R.drawable.temp_photo)
                            fallback(R.drawable.temp_photo)
                        }).build()
                ),
                contentDescription = "profilePicture",
                modifier = Modifier
                    .size(
                        height = 130.dp,
                        width = 80.dp
                    )
                    .align(
                        alignment = Alignment.CenterVertically,
                    )
                    .weight(3f)
                    .clickable { onClick() },
                contentScale = ContentScale.Inside
            )


            Spacer(modifier = Modifier.size(30.dp))
            Column(
                modifier = Modifier
                    .align(
                        alignment = Alignment.CenterVertically
                    )
                    .weight(4f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.size(spacing.spaceMedium))
                Text(
                    textAlign = TextAlign.Center,
                    text = "PICTURE ${position + 1}",
                    modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.blackSecondary)
                        )
                        .padding(
                            all = 12.dp
                        ),
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.size(spacing.spaceMedium))
                Text(
                    textAlign = TextAlign.Center,
                    text = date,
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.size(spacing.spaceMedium))
                Text(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    text = time,
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.size(spacing.spaceMedium))
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_del),
                contentDescription = "delete",
                modifier = Modifier
                    .align(
                        alignment = Alignment.CenterVertically,
                    )
                    .weight(1.5f)
                    .size(40.dp)
                    .padding(5.dp)
                    .clickable {
                        onDeleteClick(position.toInt())
                    },
                tint = colorResource(id = R.color.pink)
            )

        }
        Spacer(modifier = Modifier.size(spacing.spaceSmall))
        Divider(
            color = colorResource(id = R.color.semi_gray),
            thickness = 0.5.dp
        )

    }
}


@Composable
@Preview
fun HorizontalImageItem(
    onClick: () -> Unit = {},
    onDeleteClick: (Int) -> Unit = {},
    position: Int = 1,
    date: String = "Mon 15 Mar 21",
    time: String = "06:44:21",
    onReloadClick: () -> Unit = {},
    item: String = ""
) {
    val spacing = LocalSpacing.current
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(180.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "delete",
                modifier = Modifier
                    .align(
                        alignment = Alignment.CenterVertically,
                    )
                    .size(40.dp)
                    .padding(5.dp)
                    .weight(1f)
                    .clickable {
                        onReloadClick()
                    },
                tint = colorResource(id = R.color.sky_blue)
            )
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = item)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            error(R.drawable.temp_photo)
                            fallback(R.drawable.temp_photo)
                        }).build()
                ),
                contentDescription = "profilePicture",
                modifier = Modifier
                    .size(
                        height = 130.dp,
                        width = 80.dp
                    )
                    .align(
                        alignment = Alignment.CenterVertically,
                    )
                    .weight(2f)
                    .clickable { onClick() },
                contentScale = ContentScale.Inside
            )

            Spacer(modifier = Modifier.size(30.dp))
            Column(
                modifier = Modifier
                    .align(
                        alignment = Alignment.CenterVertically
                    )
                    .weight(3f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                Spacer(modifier = Modifier.size(spacing.spaceMedium))
                Text(
                    textAlign = TextAlign.Center,
                    text = date,
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.size(spacing.spaceMedium))
                Text(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    text = time,
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.size(spacing.spaceMedium))

            }


            Text(
                textAlign = TextAlign.Center,
                text = "PICTURE ${position+1}",
                modifier = Modifier
                    .background(
                        color = colorResource(id = R.color.blackSecondary)
                    )
                    .weight(1.5f)
                    .align(Alignment.CenterVertically)
                    .padding(
                        all = 12.dp
                    ),
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.body2
            )


            Icon(
                painter = painterResource(id = R.drawable.ic_del),
                contentDescription = "delete",
                modifier = Modifier
                    .align(
                        alignment = Alignment.CenterVertically,
                    )
                    .weight(1.5f)
                    .size(40.dp)
                    .padding(5.dp)
                    .clickable {
                        onDeleteClick(position.toInt())
                    },
                tint = colorResource(id = R.color.pink)
            )

        }

        Spacer(modifier = Modifier.size(spacing.spaceSmall))
        Divider(
            color = colorResource(id = R.color.semi_gray),
            thickness = 0.5.dp
        )
    }
}









