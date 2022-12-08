package com.bjs.pdanative.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import com.bjs.pdanative.R
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URISyntaxException
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object AllUtils {
    // private static boolean result = false;
    private var dialog: Dialog? = null

    /**
     * Is network available boolean.
     *
     * @param ctx the ctx
     * @return the boolean
     */
    //Check Network is available or not
    fun isNetworkAvailable(ctx: Context): Boolean {
        val connectivityManager =
            ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    /**
     * Gets all image and video file path.
     *
     * @param context the context
     * @param uri     the uri
     * @return the all image and video file path
     * @throws URISyntaxException the uri syntax exception
     */
    @SuppressLint("NewApi")
    @Throws(URISyntaxException::class)
    fun getAllImageAndVideoFilePath(context: Context, uri: Uri): String? {
        var uri = uri
        var selection: String? = null
        var selectionArgs: Array<String>? = null
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        when {
            isExternalStorageDocument(uri) -> {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }
            isDownloadsDocument(uri) -> {
                val id = DocumentsContract.getDocumentId(uri)
                uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )
            }
            isMediaDocument(uri) -> {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("image" == type) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                selection = "_id=?"
                selectionArgs = arrayOf(split[1])
            }
        }
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            var cursor: Cursor? = null
            try {
                cursor =
                    context.contentResolver.query(uri, projection, selection, selectionArgs, null)
                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index)
                }
            } catch (e: Exception) {
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    /**
     * Is external storage document boolean.
     *
     * @param uri the uri
     * @return the boolean
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * Is downloads document boolean.
     *
     * @param uri the uri
     * @return the boolean
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * Is media document boolean.
     *
     * @param uri the uri
     * @return the boolean
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * Sets asterisk.
     *
     * @param message the message
     * @return the asterisk
     */
    fun setAsterisk(message: String?): SpannableStringBuilder {
        val colored = " *"
        val builder = SpannableStringBuilder()
        builder.append(message)
        val start = builder.length
        builder.append(colored)
        val end = builder.length
        builder.setSpan(
            ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return builder
    }

    /**
     * Sets status bar color.
     *
     * @param activity the activity
     */
    fun setStatusBarColor(activity: Activity) {
        val window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#CA9426")
        }
    }


    /**
     * Convert date string.
     *
     * @param timeMilliSecond the timeMilliSecond
     * @param format          the format
     * @return the string
     */
    fun convertDate(timeMilliSecond: Long, format: String?): String {
        var mDate = ""

        val cal = Calendar.getInstance()
        //TimeZone tz = cal.getTimeZone();
        val date = Date(timeMilliSecond)
        val df1: DateFormat = SimpleDateFormat(format)
        df1.timeZone = TimeZone.getDefault()
        mDate = df1.format(date)
        return mDate
    }

    /**
     * Camel case string.
     *
     * @param str the str
     * @return the string
     */
    fun camelCase(str: String?): String {
        return try {
            val builder = StringBuilder(str!!)
            // Flag to keep track if last visited character is a
            // white space or not
            var isLastSpace = true
            // Iterate String from beginning to end.
            for (i in builder.indices) {
                val ch = builder[i]
                isLastSpace = if (isLastSpace && ch >= 'a' && ch <= 'z') {
                    // Character need to be converted to uppercase
                    builder.setCharAt(i, (ch.toInt() + ('A' - 'a')).toChar())
                    false
                } else ch == ' '
            }
            builder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            "NONE"
        }
    }

    /**
     * Read from url string.
     *
     * @param u the u
     * @return the string
     */
    fun readFromURL(u: String?): String {
        return try {
            val url = URL(u)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connectTimeout = 60000
            urlConnection.readTimeout = 60000
            val stream: InputStream = BufferedInputStream(urlConnection.inputStream)
            val bufferedReader = BufferedReader(InputStreamReader(stream))
            val builder = StringBuilder()
            var inputString: String?
            while (bufferedReader.readLine().also { inputString = it } != null) {
                builder.append(inputString)
            }
            urlConnection.disconnect()
            builder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }


    /**
     * Dialog.
     *
     * @param activity the activity
     */
    fun dialog(activity: Context?) {
        dialog = Dialog(activity!!)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.custom_progressdialog)
        val progressbar = dialog!!.findViewById<View>(R.id.progressBar) as ProgressBar
        progressbar.indeterminateDrawable.setColorFilter(
            Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP
        )
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val wmlp = dialog!!.window!!.attributes
        wmlp.gravity = Gravity.CENTER
        wmlp.width = WindowManager.LayoutParams.MATCH_PARENT
        wmlp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.show()
    }

    /**
     * Dismiss progress dialog.
     */
    fun dismissProgressDialog() {
        if (dialog != null) {
            if (dialog!!.isShowing) dialog!!.dismiss()
            dialog = null
        }
    }


}