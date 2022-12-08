package com.bjs.hgv.utils

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.media.ExifInterface
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.bjs.pdanative.R
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by chauhan :) on 21/8/20.
 */

internal fun Any.runOnMainThread(process: () -> Any?, delayInMs: Long = 0L) {
    Handler(Looper.getMainLooper()).postDelayed({
        if (this is AppCompatActivity) {
            runIfNotDestroyed { process() }
        } else if (this is Fragment) {
            runIfNotDestroyed { process() }
        } else if (this is Activity) {
            runIfNotDestroyed { process() }
        } else {
            process()
        }

    }, delayInMs)

}


internal fun Activity.runIfNotDestroyed(process: () -> Any?) {
    process()
}

internal fun AppCompatActivity.runIfNotDestroyed(process: () -> Any?) {
    if (this.lifecycle.currentState != Lifecycle.State.DESTROYED) {
        process()
    }
}

internal fun Fragment.runIfNotDestroyed(process: () -> Any?) {
    if (this.lifecycle.currentState != Lifecycle.State.DESTROYED) {
        process()
    }
}


internal fun Bitmap.rotateImage(filePath: String): Bitmap {
    val exif = ExifInterface(filePath)
    val orientation =
        exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
    val matrix = Matrix()
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        ExifInterface.ORIENTATION_TRANSVERSE -> matrix.postRotate(-90f)
        ExifInterface.ORIENTATION_TRANSPOSE -> matrix.postRotate(90f)
        ExifInterface.ORIENTATION_NORMAL -> {}

    }
    val rotatedBitmap = Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)


    val outputStream = FileOutputStream(filePath)
    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
    outputStream.flush()
    outputStream.close()
    return rotatedBitmap

}


internal fun Bitmap.addWatermark(
    scale: Float,
    watermark: Int = R.drawable.bjs_watermark_yellow,
    text: String = "${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())}",
    context: Context
) {
    /*Creating the Paint*/
    val paint = Paint()
    paint.color = Color.rgb(255, 191, 0)
    paint.textSize = 15 * scale
    paint.isAntiAlias = true

    /*Creating the canvas for drawing the watermark on the image */
    val canvas = Canvas(this)
    canvas.drawBitmap(this, 0f, 0f, paint)

    val option = BitmapFactory.Options()
    option.inSampleSize = 2

    val waterMark = BitmapFactory.decodeResource(context.resources, watermark, option)

    canvas.drawBitmap(
        waterMark, this.width.toFloat() - (0f + waterMark.width.toFloat()), 0f, paint
    )

    val bounds = Rect()
    paint.getTextBounds(text, 0, text.length, bounds)
    canvas.drawText(
        text, 10f, 25f, paint
    )
}





