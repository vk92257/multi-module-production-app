package com.bjs.pdanative.presentation.screens.common.qrScanner

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.components.WindowInfo
import com.core_ui.components.buttons.CircularButton
import com.bjs.pdanative.presentation.components.rememberWindowInfo
import com.bjs.pdanative.presentation.screens.common.barcodeScanner.MLkitQRCodeScanner
import com.bjs.pdanative.presentation.screens.common.camera.cameraScreen.getCameraProvider


@Composable
fun QRScanner(
    onAddManualClick: () -> Unit = {},
    onScan: (String) -> Unit
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val content = LocalContext.current
    val spacer = LocalSpacing.current
    val screenInfo = rememberWindowInfo()
    val scrollState = rememberScrollState()
    var code by remember {
        mutableStateOf("")
    }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                content, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                hasCameraPermission = granted
            })


    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    val context = LocalContext.current
    val preview = androidx.camera.core.Preview.Builder().build()
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    val previewView = remember { PreviewView(context) }

    val isBackPressed = remember {
        true
    }
    val imageAnalysis =
        ImageAnalysis.Builder().setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
            .build()

    LaunchedEffect(key1 = isBackPressed) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            imageAnalysis,
            preview
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (hasCameraPermission) {
            AndroidView(
                factory = { context ->

                    imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context),
                        MLkitQRCodeScanner { result ->
                            result.let {
                                onScan(it)
                                code = it
                            }
                        })
                    return@AndroidView previewView
                }, modifier = Modifier.fillMaxSize()
            )
        }



        if (screenInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {

            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_square),
                    contentDescription = "square icon",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            all = 30.dp
                        ),
                    tint = colorResource(id = R.color.white)
                )
                Spacer(modifier = Modifier.size(spacer.spaceLarge))
                Text(
                    text = stringResource(R.string.scanning_vehicle_message),
                    modifier = Modifier.align(
                        alignment = Alignment.CenterHorizontally
                    ),
                    style = MaterialTheme.typography.body2,
                    letterSpacing = 1.sp,
                    color = colorResource(id = R.color.white)
                )
                CircularButton(
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp
                        )
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 40.dp),
                    text = stringResource(R.string.add_manually).uppercase(),
                    onClick = onAddManualClick
                )
            }

        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_square),
                    contentDescription = "square icon",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 15.dp
                        )
                        .weight(1.5f),
                    tint = colorResource(id = R.color.white)
                )
                Column(
                    modifier = Modifier

                        .align(
                            alignment = Alignment.Bottom
                        )
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.scanning_vehicle_message),
                        modifier = Modifier
                            .align(
                                alignment = Alignment.CenterHorizontally
                            )
                            .padding(
                                20.dp
                            ),
                        style = MaterialTheme.typography.body2,
                        letterSpacing = 1.sp,
                        color = colorResource(id = R.color.white)
                    )
                    CircularButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(130.dp)
                            .padding(horizontal = 20.dp, vertical = 40.dp),
                        text = stringResource(R.string.add_manually).uppercase(),
                        onClick = onAddManualClick
                    )

                }


            }
        }
    }
}


@Composable
@Preview
fun ScanWindowPreview() {
    QRScanner(
        onAddManualClick = {},
        onScan = {},
    )
}

