package com.bjs.pdanative.presentation.screens.common.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.bjs.hgv.utils.addWatermark
import com.bjs.hgv.utils.rotateImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class CompressImageWorker(
    private val context: Context, private val workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val imageUriList = arrayListOf<String>()
        val imageFilePath = workerParams.inputData.getStringArray("imageFile")
        return try {
            imageFilePath?.let {
                it.forEach { imageFilePath ->
                    val scale: Float = context.resources.displayMetrics.density
                    val bitmapOption = BitmapFactory.Options()
                    bitmapOption.inSampleSize = 3
                    val bmp = BitmapFactory.decodeFile(imageFilePath, bitmapOption)
                    val resultBmp = bmp.copy(bmp.config, true).rotateImage(imageFilePath)
                    resultBmp.addWatermark(scale, context = context)



                    withContext(Dispatchers.IO) {
                        try {
                            val outputDirectory = context.cacheDir.let { file ->
                                File(file, "Cache").apply { mkdirs() }
                            }
                            val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
                            val PHOTO_EXTENSION = ".jpg"

                            val result = SimpleDateFormat(
                                FILENAME, Locale.US
                            ).format(System.currentTimeMillis()) + PHOTO_EXTENSION
                            val resultFile = File(outputDirectory, result)


                            val outputStream = FileOutputStream(resultFile)
                            val successful =
                                resultBmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)

                            outputStream.flush()
                            outputStream.close()

                            if (!successful) {
                                Result.failure()
                            } else {
                                Log.e("TAG", "doWork: ${resultFile.absolutePath}")
                                imageUriList.add(resultFile.absolutePath)
                            }


                        } catch (e: Exception) {
                            e.printStackTrace()
                            throw e

                        }
                    }


                }
                val dataBuilder = Data.Builder()
                Result.success(
                    dataBuilder.putStringArray("imageUri", imageUriList.toTypedArray()).build()
                )


            } ?: Result.failure()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }


}