package com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageFormScreen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.bjs.pdanative.R
import com.bjs.pdanative.domain.models.camera.ImageMetaData
import com.bjs.pdanative.presentation.components.LocalSpacing
import com.bjs.pdanative.presentation.screens.common.ImageListView
import com.bjs.pdanative.presentation.screens.common.SatNavPictureSummaryComponent
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageFormScreen.component.BottomButtons
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageFormScreen.component.LogDamageForm

@Composable
@ExperimentalComposeUiApi
fun LogFaultFormScreen(
    onCameraClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onAdditionalInfoChange: (String) -> Unit = {},
    onDamagedComponentSelected: (String) -> Unit = {},
    onPrioritySelected: (String) -> Unit = {},
    faultImages: ArrayList<ImageMetaData> = arrayListOf(),
    damagedComponentList: List<String> = listOf("mirror", "Medium", "Low"),
    priorityList: List<String> = listOf("High", "Medium", "Low"),
    additionalNotes: String = "",
    priority: String = "Priority",
    damagedComponent: String = "Damaged Component",
    softwareKeyboardController: SoftwareKeyboardController
) {
    val localSpacing = LocalSpacing.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.size(localSpacing.spaceLarge))
        LogDamageForm(
            priority = priority,
            damagedComponent = damagedComponent,
            damagedComponentList = damagedComponentList,
            priorityList = priorityList,
            onAdditionalInfoChange = { onAdditionalInfoChange(it) },
            additionalNotes = additionalNotes,
            onDamagedComponentSelected = {
                onDamagedComponentSelected(it)
            },
            onPrioritySelected = {
                onPrioritySelected(it)
            },
            softwareKeyboardController = softwareKeyboardController
        )

        SatNavPictureSummaryComponent(
            title = stringResource(id = R.string.take_photos_damage),
            modifier = Modifier.align(
                alignment = Alignment.CenterHorizontally
            ),
            imageCount = 1,
            onCameraClick = onCameraClick,
        )

        if (faultImages.isNotEmpty()) {
            ImageListView(
                imageList = faultImages
            )
            Spacer(modifier = Modifier.size(localSpacing.spaceMedium))
        }
        BottomButtons(
            onCancelClick = onCancelClick,
            onNextClick = onNextClick,
        )
        Spacer(modifier = Modifier.padding(localSpacing.spaceTwenty))


    }

}


