package com.bjs.pdanative.util

import android.annotation.SuppressLint
import java.sql.Timestamp
import java.text.SimpleDateFormat

/**
 * @Author: Vivek
 * @Date: 23/05/22
 */


@SuppressLint("SimpleDateFormat")
fun getDateAndTime(timeStamp: String, format: String) :String = SimpleDateFormat(format).format(
    Timestamp(timeStamp.toLong())
)

