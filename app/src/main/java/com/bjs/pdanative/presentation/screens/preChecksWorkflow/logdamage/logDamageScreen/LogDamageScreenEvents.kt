package com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.logDamageScreen

import com.bjs.pdanative.domain.models.camera.ImageMetaData

sealed class LogDamageScreenEvents {


    /* Log Damage List Events*/
    object OnAddMoreButtonClick : LogDamageScreenEvents()
    object OnSaveClick : LogDamageScreenEvents()

    data class OnDeleteItemButtonClick(val position: Int) : LogDamageScreenEvents()
    data class DeleteConfirmationClick(val position: Int) : LogDamageScreenEvents()
    data class CancelDeleteConfirmationClick(val position: Int) : LogDamageScreenEvents()
    data class OnEditItemButtonClick(val position: Int) : LogDamageScreenEvents()


    /* Log Damage Form events */
    data class OnDamagedComponentSelected(val damagedComponent: String) : LogDamageScreenEvents()
    data class OnPrioritySelected(val priority: String) : LogDamageScreenEvents()
    data class OnAdditionalInfoChange(val additionalInfo: String) : LogDamageScreenEvents()
    data class OnCancelButtonClick(val position: Int) : LogDamageScreenEvents()
    object OnNextButtonClick : LogDamageScreenEvents()
    data class OnImageCaptureButtonClick(var isCameraOpen: Boolean) : LogDamageScreenEvents()
    object ExitConfirmationScreenButtonClick : LogDamageScreenEvents()

    /* Camera Screen events */
    data class OnImageCapturingComplete(var imageList: List<ImageMetaData>) :
        LogDamageScreenEvents()

}