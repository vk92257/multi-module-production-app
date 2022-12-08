package com.bjs.pdanative.presentation.ui.barcodescanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * Created by deepak on {11/06/21}
 */
class BarcodeScanningViewModel(application: Application) : AndroidViewModel(application) {

    val barcodeScanType: MutableLiveData<BarCodeScanType> = MutableLiveData()
    val vehicleBarCodeScanValue: MutableLiveData<String> = MutableLiveData()
    val productBarCodeScanValue: MutableLiveData<String> = MutableLiveData()

    fun setBarcodeScan(barCodeScanType: BarCodeScanType) {
        barcodeScanType.value = barCodeScanType
    }

    fun setVehicleBarCodeScanValue(vehicleBarCodeScanValue: String?) {
        this.vehicleBarCodeScanValue.value = vehicleBarCodeScanValue
    }

    fun setProductBarCodeScanValue(productBarCodeScanValue: String?) {
        this.productBarCodeScanValue.value = productBarCodeScanValue
    }

    enum class BarCodeScanType {
        PRODUCT,
        VEHICLE
    }
}