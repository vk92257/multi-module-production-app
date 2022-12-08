package com.bjs.pdanative.presentation.screens.common.camera.cameraPreview

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bjs.pdanative.R
import com.bjs.pdanative.common.horizontalSwipeListener
import com.bjs.pdanative.domain.models.camera.ImageMetaData
import com.bjs.pdanative.presentation.components.CustomBrush
import com.core_ui.components.buttons.CircularButton
import com.core_ui.components.buttons.GradientButton
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.bjs.pdanative.presentation.screens.common.camera.cameraScreen.DeleteConfirmation

/**
 * @Author: Vivek
 * @Date: 07/04/22
 */

@Composable
fun CameraPreviewScreen(
    imageList: List<ImageMetaData> = emptyList(),
    minImages: Int = Int.MAX_VALUE,
    imageToDelete: Int = -1,
    isShowDeleteImage: Boolean = false,
    currentPreviewImageIndex: Int = 0,
    listSize: Int = 0,
    isForLogDamage: Boolean = false,
    onFinishedClick: () -> Unit = {},
    isForSingleImage: Boolean = true,
    onLogDamageClick: () -> Unit = {},
    onDeleteConfirmClick: () -> Unit = {},
    onDeleteCancelClick: () -> Unit = {},
    onRetryClick: (Int) -> Unit = {},
    onConfirmClick: () -> Unit = {},
    onDeleteClick: (Int) -> Unit = {},
    onListImageClick: (Int) -> Unit = {},
) {
    if (!isShowDeleteImage) {
        BottomBar(list = imageList, modifier = Modifier.fillMaxSize(), onClick = { position ->
            onListImageClick(position)
        }, onDeleteClick = { position ->
            onDeleteClick(position)
        }, listSize = listSize, onRetryClick = { onRetryClick(it) },
            isForSingleImage = isForSingleImage, bottomBarContent = {
                if (imageList.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.Black
                            )
                            .horizontalSwipeListener(
                                sensitivity = 10,
                                onSwipeLeft = {
                                    Log.e("TAG", "CameraPreviewScreen: left")
                                    onConfirmClick()
                                },
                                onSwipeRight = {
                                    Log.e("TAG", "CameraPreviewScreen: right")
//                                    Log.e("TAG", "CameraPreviewScreen: right  $currentPreviewImageIndex")
                                    onRetryClick(currentPreviewImageIndex)

                                }
                            )

                    ) {

                        var currentImage = " "
                        if (currentPreviewImageIndex < imageList.size)
                          currentImage  = imageList[currentPreviewImageIndex].imageUrl
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = currentImage)
                                    .crossfade(true)
                                    .error(R.drawable.temp_photo)
                                    .fallback(R.drawable.temp_photo)
                                    .build()
                            ),
                            contentDescription = "profilePicture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        ImagePreviewCount(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(top = 10.dp),
                            count = currentPreviewImageIndex + 1,
                            minImages = minImages,
                            isShowPreviewText = true
                        )
                        Yes(modifier = Modifier
                            .size(
                                130.dp
                            )
                            .align(
                                alignment = Alignment.CenterEnd
                            )
                            .absoluteOffset(
                                x = 50.dp
                            )
                            .padding(
                                start = 10.dp
                            ), iconSize = 35.dp, onClick = { onConfirmClick() }

                        )
                        No(modifier = Modifier
                            .size(
                                130.dp
                            )
                            .align(
                                alignment = Alignment.CenterStart
                            )
                            .absoluteOffset(
                                x = (-50).dp
                            )
                            .padding(
                                end = 10.dp
                            ), iconSize = 35.dp, onClick = {
                            onRetryClick(currentPreviewImageIndex)
                        })

                        if (!isForSingleImage && !isForLogDamage) {
                            CircularButton(
                                modifier = Modifier
                                    .align(
                                        alignment = Alignment.BottomEnd
                                    )
                                    .padding(
                                        bottom = 25.dp, end = 20.dp
                                    )
                                    .width(
                                        180.dp
                                    ),
                                onClick = onFinishedClick,
                                text = stringResource(R.string.remove),
                                textSize = 16.sp,
                            )

                        }
                        if (isForLogDamage) {
                            GradientButton(
                                text = stringResource(R.string.log_damage),
                                textColor = Color.White,
                                buttonModifier = Modifier
                                    .width(200.dp)
                                    .align(
                                        alignment = Alignment.BottomStart
                                    )
                                    .padding(
                                        bottom = 25.dp, start = 20.dp
                                    ),
                                gradient = CustomBrush.vPinkRedGradient,
                                iconVisible = false,
                                onclick = onLogDamageClick
                            )
                        }
                    }
                }
            })
    } else {
        DeleteConfirmation(
            onDeleteConfirmClick = onDeleteConfirmClick,
            onDeleteCancelClick = onDeleteCancelClick,
            currentImageIndex = imageToDelete,
            minImageIndex = minImages,
            imageUrl = imageList[imageToDelete].imageUrl,
        )
    }

}


@Composable
@Preview(showBackground = true)
fun ImagePreviewCount(
    modifier: Modifier = Modifier,
    count: Int = 0,
    minImages: Int = Int.MAX_VALUE,
    isShowPreviewText: Boolean = false
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isShowPreviewText) Text(
            text = stringResource(R.string.image_preview),
            modifier = Modifier
                .background(color = Color.Black)
                .fillMaxHeight()
                .wrapContentHeight()
                .align(
                    alignment = Alignment.CenterVertically
                )
                .padding(horizontal = 15.dp),
            color = colorResource(id = R.color.white),
            maxLines = 1,
            style = TypographyEvelethClean.body2,
            textAlign = TextAlign.Center
        )
        Text(
            text = "${stringResource(id = R.string.picture).uppercase()} $count${if (minImages != Int.MAX_VALUE) "/$minImages" else ""}",
            modifier = Modifier
                .background(colorResource(id = R.color.black).copy(alpha = 0.65F))
                .fillMaxHeight()
                .wrapContentHeight()
                .align(
                    alignment = Alignment.CenterVertically
                )
                .padding(horizontal = 15.dp),
            color = colorResource(id = R.color.light_white),
            textAlign = TextAlign.Center,
            maxLines = 1,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Normal
        )

    }
}

@Composable
@Preview(showBackground = true)
fun CamPreview() {
    CameraPreviewScreen()
}





