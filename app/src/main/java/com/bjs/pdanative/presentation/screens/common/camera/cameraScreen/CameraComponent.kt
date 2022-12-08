package com.bjs.pdanative.presentation.screens.common.camera.cameraScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.CustomBrush
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.core_ui.components.buttons.CircularButton
import com.bjs.pdanative.presentation.components.fonts.TypographyEvelethClean
import com.bjs.pdanative.presentation.screens.common.camera.CameraUIAction
import com.bjs.pdanative.presentation.screens.common.camera.cameraPreview.ImagePreviewCount

/**
 * @Author: Vivek
 * @Date: 21/04/22
 */

@Composable
fun CameraControl(
    imageVector: ImageVector,
    contentDescId: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector,
            contentDescription = contentDescId,
            modifier = modifier,
            tint = Color.White
        )
    }
}

@Composable
fun CameraControls(cameraUIAction: (CameraUIAction) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        CameraControl(
            Icons.Default.Face,
            "Flip Camera",
            modifier = Modifier.size(64.dp),
            onClick = { cameraUIAction(CameraUIAction.OnSwitchCameraClick) }
        )

        CameraControl(
            Icons.Default.AccountCircle,
            "take Photo",
            modifier = Modifier
                .size(64.dp)
                .padding(1.dp)
                .border(1.dp, Color.White, CircleShape),
            onClick = { cameraUIAction(CameraUIAction.OnCameraClick) }
        )

        CameraControl(
            Icons.Default.PlayArrow,
            "Gallery",
            modifier = Modifier.size(64.dp),
            onClick = { /*cameraUIAction(CameraUIAction.)*/ }
        )

    }
}


@Composable
@Preview
fun DeleteConfirmation(
    onDeleteConfirmClick: () -> Unit = {},
    onDeleteCancelClick: () -> Unit = {},
    currentImageIndex: Int = -1,
    minImageIndex: Int = -1,
    imageUrl: String = "",
) {
    val spacer = LocalSpacing.current
    Column(
        Modifier
            .fillMaxSize()
            .background(
                brush = CustomBrush.vPinkRedGradient
            )
            .verticalScroll(
                state = rememberScrollState()
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.size(spacer.spaceMedium))
        Icon(
            modifier = Modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                )
                .size(100.dp),
            painter = painterResource(id = R.drawable.ic_circle_exclamation),
            contentDescription = "Stock item scan error",
            tint = colorResource(id = R.color.white)
        )
        Spacer(modifier = Modifier.size(spacer.spaceLarge))
        Text(
            text = stringResource(id = R.string.delete_image),
            style = TypographyEvelethClean.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp
                ),
            textAlign = TextAlign.Center,
            letterSpacing = (0.5).sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white)
        )
        Spacer(modifier = Modifier.size(spacer.spaceLarge))
        ImagePreview(
            modifier = Modifier
                .height(200.dp)
                .padding(horizontal = 30.dp)
                .align(Alignment.CenterHorizontally),
            imageUrl = imageUrl,
            currentImageCount = currentImageIndex+1,
            minImageCount = minImageIndex,
        )
        Spacer(modifier = Modifier.size(spacer.spaceLarge))
        Text(
            text = "Deleting this image will permanently remove it.",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 30.dp
                ),
            textAlign = TextAlign.Center,
            letterSpacing = (0.5).sp,
            color = colorResource(id = R.color.light_white)
        )
        Spacer(modifier = Modifier.size(spacer.spaceMedium))
        Text(
            text = "Are you sure you want to do this?",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 30.dp
                ),
            textAlign = TextAlign.Center,
            letterSpacing = (0.5).sp,
            color = colorResource(id = R.color.light_white)
        )
        Spacer(modifier = Modifier.size(spacer.spaceForty))
        CircularButton(
            modifier = Modifier.padding(
                horizontal = 30.dp
            ),
            text = stringResource(id = R.string.delete_confirmation),
            buttonColor = colorResource(id = R.color.white),
            textColor = colorResource(id = R.color.red),
            onClick = {
                onDeleteConfirmClick()
            }
        )
        Spacer(modifier = Modifier.size(spacer.spaceMedium))

        Button(
            border = BorderStroke(
                2.dp,
                color = colorResource(id = R.color.white)
            ),
            shape = RoundedCornerShape(
                60.dp
            ),
            elevation = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick = {
                onDeleteCancelClick()
            },
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(
                    horizontal = 30.dp,
                )
        ) {
            Text(
                text = stringResource(id = R.string.cancel),
                fontWeight = FontWeight.Bold,
                style = TypographyEvelethClean.body1,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.size(spacer.spaceLarge))
    }

}


@Composable
@Preview(showBackground = true)
fun ImagePreview(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    currentImageCount: Int = -1,
    minImageCount: Int = -1,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = imageUrl)
                    .crossfade(true)
                    .error(R.drawable.temp_photo)
                    .fallback(R.drawable.temp_photo).placeholder(R.drawable.temp_photo).build()
            ),
            contentDescription = "profilePicture",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        ImagePreviewCount(
            modifier = Modifier
                .wrapContentWidth()
                .height(50.dp)
                .padding(all = 10.dp)
                .align(Alignment.TopEnd),
            count = currentImageCount,
            minImages = minImageCount,
        )

    }

}











