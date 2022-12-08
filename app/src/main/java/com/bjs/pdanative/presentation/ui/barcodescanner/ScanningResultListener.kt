package com.bjs.pdanative.presentation.ui.barcodescanner

interface ScanningResultListener {
    fun onScannedSuccess(result: String)
    fun onScannedFailed()
}