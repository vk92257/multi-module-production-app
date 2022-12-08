package com.bjs.pdanative.presentation.ui.runsheet

import android.os.Bundle
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunSheetActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_runsheet)
    }

}