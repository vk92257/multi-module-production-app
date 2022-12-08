package com.bjs.pdanative.presentation.ui.lgvcheck

import android.app.Application
import androidx.lifecycle.*

class LGVCheckViewModel(application: Application) : AndroidViewModel(application) {

    val lgvCheckList: MutableLiveData<List<LgvCheck>> = MutableLiveData()

    fun setLgvCheckList(lgvChecks: ArrayList<LgvCheck>) {
        lgvCheckList.postValue(lgvChecks)
    }

    fun setPosition(pos: Int) {

    }


}