package com.bjs.pdanative.presentation.screens.loginWorkflow.userProfileScreen

import com.bjs.pdanative.domain.models.camera.ImageMetaData

/**
 * @Author: Vivek
 * @Date: 30/03/22
 */
sealed class ProfileScreenEvents {
    /* Profile Screen Events */
    data class TermsAndConditionCheckBoxClick(val value: Boolean) : ProfileScreenEvents()
    data class TermsAndConditionButtonClick(val value : Boolean) : ProfileScreenEvents()
    object OnContinueClick : ProfileScreenEvents()
    object OnEditClick : ProfileScreenEvents()
    data class OnDropDownSelect(val value: String) : ProfileScreenEvents()
    data class OnNotificationClick(val id: String) : ProfileScreenEvents()
    data class AfterImageCaptured(val listOfImages: ArrayList<ImageMetaData>) :
        ProfileScreenEvents()

    /*LoggedInUserList Screen Events*/
    data class OpenCameraClick(val openCameraClick: Boolean) : ProfileScreenEvents()
    data class OpenUserList(val openUserList: Boolean) : ProfileScreenEvents()
    object OnAddAnotherUserClick : ProfileScreenEvents()
    object OnLoggedInUserContinueClick : ProfileScreenEvents()

}

