package com.bjs.pdanative.presentation.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.*
import com.bjs.pdanative.R
import com.bjs.pdanative.domain.models.camera.ImageMetaData
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import kotlin.math.max


/*@Preview
@Composable
fun ConstraintLayoutTest() {
    Surface(
        color = colorResource(id = R.color.white),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {
ConstraintLayout(
    modifier = Modifier.semantics {
       contentDescription = "Featured"
    }
) {
    val (image, red, yellow, black, steps, icon) = createRefs()
     ScanButton(
         modifier = Modifier.constrainAs(image) {
          *//*  top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)*//*
             centerHorizontallyTo(parent)
             centerVerticallyTo(parent)


        })
    Divider(
        modifier = Modifier.size(
            200.dp
        ).background(
            color = colorResource(id = R.color.red)
        ).constrainAs(red){
            top.linkTo(image.bottom)
        }
    )

    Divider(
        modifier = Modifier.size(
            200.dp
        ).background(
            color = colorResource(id = R.color.bjs)
        ).constrainAs(yellow){
            bottom.
        }
    )


    Divider(
        modifier = Modifier.size(
            200.dp
        ).background(
            color = colorResource(id = R.color.black)
        ).constrainAs(black){

        }
    )



}

    }
}*/


@Preview
@Composable
fun ScanButton(
    modifier: Modifier = Modifier,
    onScanButtonClick: () -> Unit = {},
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.scan))
    val progressLottie by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f,
        isPlaying = true,
    )
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(200.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_scan_button_gradient),
            contentDescription = "Scan Button",
            modifier = Modifier
                .clip(CircleShape)
                .align(
                    alignment = Alignment.Center
                )
                .clickable {
//                        onNavigate(UiEvent.Navigate(Route.RouteAssignConfirmationScreen))
                },
        )

        Box(
            modifier = Modifier
                .size(160.dp)
                .align(
                    alignment = Alignment.Center,
                )
                .clip(CircleShape)
                .background(
                    brush = CustomBrush.bjsYellowGradient,
                    shape = CircleShape
                ),
        ) {

        }

        LottieAnimation(
            modifier = Modifier
                .align(
                    alignment = Alignment.Center
                )
                .clickable {
                    onScanButtonClick()
                },
            composition = composition,
            progress = progressLottie,
            alignment = Alignment.Center,
            clipToCompositionBounds = true,
            enableMergePaths = true,
            renderMode = RenderMode.HARDWARE,
        )


    }
}

@Preview
@Composable
fun SatNavTakePictureComponent(
    modifier: Modifier = Modifier,
    onCameraClick: () -> Unit = {},
    imageCount: Int = 1,
) {
    val spacer = LocalSpacing.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.water_wave))
    val progressLottie by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1.0f,
    )

    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.padding(spacer.spaceEighteen))
        Text(
            maxLines = 1,
            text = stringResource(R.string.take_photos_sat_nav),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
        )
        Spacer(modifier = Modifier.padding(spacer.spaceExtraSmall))
        Row(
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = "tick",
                tint = colorResource(id = R.color.semi_gray)

            )
            Text(
                maxLines = 1,
                text = "(min. $imageCount photo required)",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 4.dp),
                color = colorResource(id = R.color.semi_gray)
            )

        }
        Spacer(modifier = Modifier.padding(spacer.spaceEighteen))


        Box(
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                )
                .clip(CircleShape)
                .size(220.dp)
        ) {
            LottieAnimation(
                modifier = Modifier
                    .align(
                        alignment = Alignment.Center
                    ),
                composition = composition,
                progress = progressLottie,
                alignment = Alignment.Center,
                clipToCompositionBounds = true,
                enableMergePaths = true,
                renderMode = RenderMode.HARDWARE,
            )

            Image(
                painter = painterResource(id = R.drawable.ic_scan_button_gradient),
                contentDescription = "Scan Button",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(200.dp)
                    .align(
                        alignment = Alignment.Center
                    ),
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        brush = CustomBrush.bjsYellowGradient,
                        shape = CircleShape
                    )
                    .size(160.dp)
                    .align(
                        alignment = Alignment.Center,
                    )
                    .clickable {
                        onCameraClick()
                    }

            ) {

            }
            Icon(
                painter = painterResource(id = R.drawable.ic_camera_nav),
                contentDescription = "painter",
                modifier = Modifier
                    .align(
                        alignment = Alignment.Center
                    )
                    .size(70.dp),
                tint = colorResource(id = R.color.dark_brown)
            )


        }

        Spacer(modifier = Modifier.padding(spacer.spaceEighteen))
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun SatNavPictureSummaryComponent(
    modifier: Modifier = Modifier,
    onCameraClick: () -> Unit = {},
    imageCount: Int = 1,
    title: String = stringResource(R.string.take_photos_sat_nav)
) {
    val spacer = LocalSpacing.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animated_camera))
    val progressLottie by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1.0f,
    )

    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.padding(spacer.spaceEighteen))
        Text(
            maxLines = 1,
            text = title,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
        )
        Spacer(modifier = Modifier.padding(spacer.spaceExtraSmall))
        Row(
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = "tick",
                tint = colorResource(
                    id = R.color.lime_green
                )
            )
            Text(
                text = "(min. $imageCount photos added)",
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.body2,
                fontStyle = FontStyle.Italic,
                color = colorResource(id = R.color.green),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 4.dp)
            )

        }
        Spacer(modifier = Modifier.padding(spacer.spaceEighteen))
        Box(
            modifier = Modifier
                .size(90.dp)
                .align(
                    alignment = Alignment.CenterHorizontally
                )
                .background(
                    brush = CustomBrush.bjsYellowGradient,
                    shape = CircleShape
                )
        ) {
            Card(
                elevation = 10.dp,
                shape = CircleShape,
                onClick = {
                    onCameraClick()
                }
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = progressLottie,
                    modifier = Modifier
                        .size(90.dp)
                        .background(
                            brush = CustomBrush.bjsYellowGradient,
                        ),
                    alignment = Alignment.Center,
                    clipToCompositionBounds = true
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_circle_plus),
                contentDescription = "van image ",
                modifier = Modifier
                    .size(25.dp)
                    .offset(y = 7.dp)
                    .align(alignment = Alignment.TopEnd)
            )

        }
        Spacer(modifier = Modifier.padding(spacer.spaceEighteen))
    }

}


@Composable
@Preview
fun ImageItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onDeleteClick: (Int) -> Unit = {},
    position: Int = 1,
    date: String = "Mon 15 Mar 21",
    time: String = "06:44:21",
    onReloadClick: () -> Unit = {},
    item: String = ""
) {
    Box(modifier = Modifier) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = item)
                    .crossfade(true)
                    .error(R.drawable.temp_photo)
                    .fallback(R.drawable.temp_photo)
                    .build()
            ),
            contentDescription = "profilePicture",
            modifier = Modifier
                .padding(10.dp)
                .size(
                    width = 200.dp,
                    height = 100.dp
                )
                .background(
                    color = colorResource(id = R.color.black)
                )
                .clickable { onClick() },
            contentScale = ContentScale.Inside
        )
        Box(
            modifier = Modifier
                .size(35.dp)
                .align(
                    alignment = Alignment.TopEnd
                )
                .offset(
                )
                .background(
                    shape = CircleShape,
                    color = colorResource(id = R.color.white)
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera_focus),
                contentDescription = "nav picture",
                modifier = Modifier
                    .drawBehind {
                        drawCircle(
                            brush = CustomBrush.vGradientLgrayDgray
                        )
                    }
                    .size(
                        25.dp,
                    )
                    .padding(
                        (5.5).dp
                    )
                    .align(alignment = Alignment.Center),
                tint = colorResource(id = R.color.white)
            )
        }


    }
}


@Composable
@Preview
fun ImageListView(
    modifier: Modifier = Modifier,
    imageList: ArrayList<ImageMetaData> = arrayListOf(),
) {
    LazyRow() {
        itemsIndexed(imageList) { position, item ->
            ImageItem(
                item = item.imageUrl,
            )

        }
    }
}


@Composable
public fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val rowWidths = IntArray(rows) { 0 } // Keep track of the width of each row
        val rowHeights = IntArray(rows) { 0 } // Keep track of the height of each row

        // Don't constrain child views further, measure them with given constraints
        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)

            placeable
        }

        // Grid's width is the widest row
        val width = rowWidths.maxOrNull()?.coerceIn(constraints.minWidth, constraints.maxWidth)
            ?: constraints.minWidth
        // Grid's height is the sum of each row
        val height = rowHeights.sum().coerceIn(constraints.minHeight, constraints.maxHeight)

        // y co-ord of each row
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }
        layout(width, height) {
            // x co-ord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.place(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}
