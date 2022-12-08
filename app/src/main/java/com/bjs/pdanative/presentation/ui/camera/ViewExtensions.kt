package com.bjs.pdanative.presentation.ui.camera

import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import android.view.DisplayCutout
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

/** Combination of all flags required to put activity into immersive mode */
const val FLAGS_FULLSCREEN =
    View.SYSTEM_UI_FLAG_LOW_PROFILE or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

/** Milliseconds used for UI animations */
const val ANIMATION_FAST_MILLIS = 50L
const val ANIMATION_SLOW_MILLIS = 100L

/**
 * Simulate a button click, including a small delay while it is being pressed to trigger the
 * appropriate animations.
 */
fun ImageButton.simulateClick(delay: Long = ANIMATION_FAST_MILLIS) {
    performClick()
    isPressed = true
    invalidate()
    postDelayed({
        invalidate()
        isPressed = false
    }, delay)
}

/** Pad this view with the insets provided by the device cutout (i.e. notch) */
@RequiresApi(Build.VERSION_CODES.P)
fun View.padWithDisplayCutout() {

    /** Helper method that applies padding from cutout's safe insets */
    fun doPadding(cutout: DisplayCutout) = setPadding(
        cutout.safeInsetLeft,
        cutout.safeInsetTop,
        cutout.safeInsetRight,
        cutout.safeInsetBottom
    )

    // Apply padding using the display cutout designated "safe area"
    rootWindowInsets?.displayCutout?.let { doPadding(it) }

    // Set a listener for window insets since view.rootWindowInsets may not be ready yet
    setOnApplyWindowInsetsListener { _, insets ->
        insets.displayCutout?.let { doPadding(it) }
        insets
    }
}

/** Same as [AlertDialog.show] but setting immersive mode in the dialog's window */
fun AlertDialog.showImmersive() {
    // Set the dialog to not focusable
    window?.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    )

    // Make sure that the dialog's window is in full screen
    window?.decorView?.systemUiVisibility = FLAGS_FULLSCREEN

    // Show the dialog while still in immersive mode
    show()

    // Set the dialog to focusable again
    window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
}


fun showTimePickerDialog(mActivity: AppCompatActivity,OnTimeSet:(time : String?)->Unit): String? {
    var time: String? = null
    val cal = Calendar.getInstance()
    val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
        cal.set(Calendar.HOUR_OF_DAY, i)
        cal.set(Calendar.MINUTE, i2)

        time = SimpleDateFormat("hh:mm a").format(cal.time)

        OnTimeSet.invoke(time)
    }

    TimePickerDialog(
        mActivity,
        timeSetListener,
        cal.get(Calendar.HOUR_OF_DAY),
        cal.get(Calendar.MINUTE),
        false
    ).show()


    return time
}

fun ablumUpdate(context: Context?, dstPath: String?) {
    if (TextUtils.isEmpty(dstPath) || context == null) return
    val values = ContentValues(2)
    val extensionName: String? =
        getExtensionName(
            dstPath )

    values.put(
        MediaStore.Images.Media.MIME_TYPE,
        "image/" + if (TextUtils.isEmpty(extensionName)) "jpeg" else extensionName
    )
    values.put(MediaStore.Images.Media.DATA, dstPath)
    context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
}

fun getExtensionName(filename: String?): String? {
    if (filename != null && filename.length > 0) {
        val dot = filename.lastIndexOf('.')
        if (dot > -1 && dot < filename.length - 1) {
            return filename.substring(dot + 1)
        }
    }
    return ""
}