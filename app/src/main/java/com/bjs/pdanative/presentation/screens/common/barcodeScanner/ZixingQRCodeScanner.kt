package com.bjs.pdanative.presentation.screens.common.barcodeScanner

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
/*import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer*/
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


/*This calls is used for implementing the Zxing library barcode scanner \*/

class ZixingQRCodeScanner(
    private val onQrCodeScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {

    companion object {
        private val supportedImageFormats = listOf(
            ImageFormat.YUV_420_888,
            ImageFormat.YUV_422_888,
            ImageFormat.YUV_444_888,
        )
    }

    override fun analyze(image: ImageProxy) {
       /* if (image.format in supportedImageFormats) {
            val bytes = image.planes.first().buffer.toByteArray(image.imageInfo.rotationDegrees)
            val source = PlanarYUVLuminanceSource(
                bytes,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false
            )
            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
            try {
                val result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to listOf(
                                BarcodeFormat.QR_CODE,
                            )
                        )
                    )
                }.decode(binaryBitmap)
                onQrCodeScanned(result.text)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {

            }
        }*/
    }

    private fun ByteBuffer.toByteArray(rotationDegrees: Int): ByteArray {
        rewind()
        var byteArray = ByteArray(remaining()).also {
            get(it)
        }


        if (rotationDegrees != 180 && rotationDegrees != 0) {

            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size,null)

            val matrix = Matrix().apply { postRotate(rotationDegrees.toFloat()) }
            if (bitmap != null) {
                val rotatedBitmap =
                    Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                val bytArrayStream = ByteArrayOutputStream()
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytArrayStream)
                byteArray = bytArrayStream.toByteArray()
                rotatedBitmap.recycle()
            }
        }


        return byteArray
    }
}