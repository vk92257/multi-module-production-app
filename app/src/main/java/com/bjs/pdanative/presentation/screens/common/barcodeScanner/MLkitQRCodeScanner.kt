package com.bjs.pdanative.presentation.screens.common.barcodeScanner

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class MLkitQRCodeScanner(
    private val onQrCodeScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {
    private val supportedImageFormats = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
            Barcode.FORMAT_AZTEC,
            Barcode.FORMAT_CODE_128,
            Barcode.FORMAT_CODE_93,
            Barcode.FORMAT_CODE_39,
        ).build()


    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val mediaImage = image.image
        val scanner = BarcodeScanning.getClient(supportedImageFormats)
        if (mediaImage != null) {
            try {
                val currentImage =
                    InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)
                scanner.process(currentImage).addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty())
                        onQrCodeScanned(barcodes[0].displayValue.toString())
                }.addOnFailureListener {
                    onQrCodeScanned(it.toString())
                }.addOnCompleteListener {
                    mediaImage.close()
                    image.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mediaImage.close()
                image.close()
            }
        }
    }
}