package com.bjs.pdanative.presentation.screens.preChecksWorkflow.satNavProcess.satNavSummary.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.bjs.pdanative.presentation.screens.common.ImageItem


@Preview
@Composable
fun SatNavSummaryComponents(
    modifier: Modifier = Modifier,
    navAssetId: String = "21391487329",
    onEditButtonClicked: () -> Unit = {},
    onScanClicked: () -> Unit = {},
    image: Painter = painterResource(id = R.drawable.ic_satnav_new),


    ) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.scan))
    val progressLottie by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f,
        isPlaying = true,
    )
    val spacer = LocalSpacing.current
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(5.dp),
                )
                .wrapContentHeight()
                .background(
                    shape = RoundedCornerShape(30.dp),
                    brush = CustomBrush.hGradientYellowAndGreen
                ),
        ) {
            Spacer(modifier = Modifier.size(spacer.spaceExtraLarge))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(spacer.spaceExtraLarge)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_barcode_icon),
                    contentDescription = "painter",
                    modifier = Modifier
                        .align(
                            alignment = Alignment.CenterVertically
                        )
                        .clickable {


                        }
                        .size(30.dp)
                )

                Spacer(modifier = Modifier.size(spacer.spaceMedium))
                Text(
                    text = navAssetId,
                    modifier = Modifier
                        .align(
                            alignment = Alignment.CenterVertically
                        ),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.size(spacer.spaceLarge))
                Icon(
                    painter = painterResource(id = R.drawable.ic_pencil),
                    contentDescription = "painter",
                    modifier = Modifier
                        .border(
                            shape = CircleShape,
                            border = BorderStroke(
                                width = 2.dp,
                                color = colorResource(id = R.color.black)
                            ),
                        )
                        .padding(12.dp)
                        .align(
                            alignment = Alignment.CenterVertically
                        )
                        .clickable {
                        }
                        .size(25.dp)
                )


            }


            Spacer(modifier = Modifier.size(spacer.spaceLarge))

            Divider(
                thickness = 3.dp,
                color = colorResource(id = R.color.trams_black)
            )
            Row(
                modifier = Modifier.align(
                    alignment = Alignment.CenterHorizontally
                )
            ) {

                LottieAnimation(
                    modifier = Modifier
                        .align(
                            alignment = Alignment.CenterVertically
                        )
                        .clickable {
//                        openScanner = true
                        }
                        .size(
                            150.dp
                        ),
                    composition = composition,
                    progress = progressLottie,
                    alignment = Alignment.Center,
                    clipToCompositionBounds = true,
                    enableMergePaths = true,
                    renderMode = RenderMode.HARDWARE,
                )


                Text(
                    text = stringResource(R.string.tap_to_rescan).uppercase(),
                    modifier = Modifier
                        .align(
                            alignment = Alignment.CenterVertically
                        )
                        .offset(
                            x = (-38).dp
                        ), style = TypographyEvelethClean.body1,
                    fontWeight = FontWeight.Bold
                )

            }


            Spacer(modifier = Modifier.size(spacer.spaceSmall))
        }
        Image(
            painter = image,
            contentDescription = "Nav image ",
            modifier = Modifier
                .size(180.dp)
                .offset(
                    y = (-112).dp
                )
                .align(
                    alignment = Alignment.TopCenter
                )
        )
    }
}










