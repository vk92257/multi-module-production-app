package com.bjs.pdanative.presentation.ui.camera

import android.app.Application
import androidx.lifecycle.*

class CameraViewModel(application: Application) : AndroidViewModel(application) {

    val userProfile: MutableLiveData<List<ImagesModel>> = MutableLiveData()
    val requirePhotos: MutableLiveData<Int> = MutableLiveData()

    fun setUserProfile(imageList: ArrayList<ImagesModel>) {
        userProfile.postValue(imageList)
    }

    val capturedImageList: MutableLiveData<ArrayList<ImagesModel>> = MutableLiveData()
    fun setCapturedImageList(imageList: ArrayList<ImagesModel>) {
        capturedImageList.postValue(imageList)
    }

    fun setRequiredPhotos(count:Int){
        requirePhotos.value=count
    }
}