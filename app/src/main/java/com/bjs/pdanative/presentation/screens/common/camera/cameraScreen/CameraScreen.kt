package com.bjs.pdanative.presentation.screens.common.camera.cameraScreen

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.bjs.pdanative.R
import com.bjs.pdanative.domain.models.camera.ImageMetaData
import com.bjs.pdanative.presentation.screens.common.camera.CameraUIAction
import com.bjs.pdanative.presentation.screens.common.camera.CameraViewModel
import com.bjs.pdanative.presentation.screens.common.camera.cameraPreview.CameraPreviewScreen
import com.bjs.pdanative.presentation.screens.common.camera.cameraPreview.ImagePreviewCount
import com.bjs.pdanative.presentation.screens.common.camera.getOutputDirectory
import com.bjs.pdanative.presentation.screens.common.camera.takePicture
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * @Author: Vivek
 * @Date: 08/04/22
 */

@Composable
fun CameraScreen(
    navigateUp: (List<ImageMetaData>) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: CameraViewModel = hiltViewModel(),
    isForSingleImage: Boolean = true,
    minImages: Int = Int.MAX_VALUE,
    isForLogDamage: Boolean = false,
    imageList: ArrayList<ImageMetaData> = arrayListOf<ImageMetaData>(),
    openFrontFacingCamera: Boolean = true,
    onLogDamageClick: () -> Unit = {},
) {
    LaunchedEffect(key1 = false) {
        if (imageList.isNotEmpty()) {
            viewModel.updateImageList(imageList)
        }
        if (minImages < Int.MAX_VALUE) {
            viewModel.updateImageMinCount(minImages)
        }


        viewModel.cameraUiEvent.collect { action ->
            when (action) {
                is CameraUIAction.OnCompletingImageCapturing -> {
                    navigateUp(action.listOfImages)
                    /* navigate(UiEvent.Navigate(action.source))*/
//                    onImageCapturedCompleted(action.listOfImages)
                }
                else -> {}
            }
        }
    }


    /* initializing the required components for the camera */
    var lensFacing by rememberSaveable {
        mutableStateOf(CameraSelector.LENS_FACING_FRONT)
    }

    if (!openFrontFacingCamera) {
        lensFacing = CameraSelector.LENS_FACING_BACK
    }

    val context = LocalContext.current
    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().build()
    }

    /*val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) onImageCaptured(uri, true)
    }*/

    if (viewModel.state.imageCaptured && viewModel.state.imageList.isNotEmpty()) {
        /* After taking the image showing the image preview screen */
        CameraPreviewScreen(
            currentPreviewImageIndex = viewModel.state.currentImageIndex,
            isForLogDamage = isForLogDamage,
            isForSingleImage = isForSingleImage,
            minImages = minImages,
            isShowDeleteImage = viewModel.state.isShowDeleteImage,
            onLogDamageClick = onLogDamageClick,
            imageList = viewModel.state.imageList,
            imageToDelete = viewModel.state.imageToDelete,
            onRetryClick = { position ->
                viewModel.onStateChange(CameraUIAction.OnRetryClick(position))
            },
            onConfirmClick = {
                if (isForSingleImage)
                    viewModel.onStateChange(
                        CameraUIAction.ImageCapturingComplete
                    )
                else viewModel.onStateChange(CameraUIAction.OnConfirmClick)
            },
            onDeleteClick = { position ->
                viewModel.onStateChange(
                    CameraUIAction.OnImageItemDeleteClick(
                        position = position
                    )
                )
            },
            onDeleteConfirmClick = {
                viewModel.onStateChange(CameraUIAction.OnDeleteConfirmClick(viewModel.state.imageToDelete))
            },
            onDeleteCancelClick = {
                viewModel.onStateChange(CameraUIAction.OnDeleteCancelClick)
            },
            onListImageClick = { position ->
                viewModel.onStateChange(CameraUIAction.OnListImageClick(position))
            },

            onFinishedClick = {
                viewModel.onStateChange(CameraUIAction.ImageCapturingComplete)
            },
        )

    } else {

        /* Live Camera preview for taking the images */
        CameraImageView(
            imageList = viewModel.state.imageList.toMutableList() as ArrayList<ImageMetaData>,
            minImages = minImages,
            imageCapture = imageCapture,
            lensFacing = lensFacing
        ) { cameraUiAction ->
            when (cameraUiAction) {
                is CameraUIAction.OnCameraClick -> {
                    imageCapture.takePicture(
                        context = context,
                        lensFacing = lensFacing,
                        onImageCaptured = { uri, _ ->
                            if (uri.isNotEmpty()) {
                                viewModel.onStateChange(
                                    CameraUIAction.AfterPhotoTaken(
                                        uri,
                                        System.currentTimeMillis().toString()
                                    )
                                )
                            }

                        },
                        onError = { imageCaptureException ->
                            Log.e("TAG", "CameraScreen: $imageCaptureException")
                        }
                    )
                }
                is CameraUIAction.OnSwitchCameraClick -> {
                    lensFacing =
                        if (lensFacing == CameraSelector.LENS_FACING_BACK) CameraSelector.LENS_FACING_FRONT else CameraSelector.LENS_FACING_BACK
                }
                is CameraUIAction.OnListImageClick -> {
                    if (true == context.getOutputDirectory().listFiles()?.isNotEmpty()) {
//                        galleryLauncher.launch("image/*")
                    }
                }
                else -> {}
            }
        }
    }

}


@Composable
private fun CameraImageView(
    imageCapture: ImageCapture,
    imageList: ArrayList<ImageMetaData> = arrayListOf(),
    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    minImages: Int = Int.MAX_VALUE,
    cameraUIAction: (CameraUIAction) -> Unit,
) {
    /* initializing the required components for the camera */
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()
    val previewView = remember { PreviewView(context) }


    LaunchedEffect(key1 = lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .clickable {
            cameraUIAction(CameraUIAction.OnCameraClick)
        }) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize()) {
        }
        ImagePreviewCount(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .offset(y = 10.dp),
            count = imageList.size + 1,
            minImages = minImages,
        )
        Image(
            painter = painterResource(id = R.drawable.ic_square_two),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    20.dp
                ),
            contentScale = ContentScale.FillBounds,
            contentDescription = "square"
        )
    }
}


suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener(
                {
                    continuation.resume(cameraProvider.get())
                },
                ContextCompat.getMainExecutor(this)
            )
        }
    }






