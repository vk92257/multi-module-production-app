package com.core.util

import android.os.Handler
import android.os.Looper

/**
 * @Author: Vivek
 * @Date: 31/10/22
 */


class CustomTimer {
    private var isRunning = true
    private var time: String = ""
    private var number: String = ""
    private lateinit var timerFunction: (String) -> Unit
    fun getCurrentTime(timer: (String) -> Unit) {
        timerFunction = timer
    }

    val handler = Handler(Looper.getMainLooper())
    private val runnable: Runnable = object : Runnable {
        private val startTime = System.currentTimeMillis()

        override fun run() {
            try {
                while (isRunning) {
                    Thread.sleep(1000)
                    handler.post {
                        val millis = (System.currentTimeMillis() - startTime).toInt() / 1000
                        val min = millis / 60
                        val sec = millis % 60
                        time =
                            (if (min < 10) "0$min" else min).toString() + ":" + if (sec < 10) "0$sec" else sec
                        timerFunction(time)
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }


    fun startTimer(phoneNumber: String) {
        number = phoneNumber
        Thread(runnable).start()
    }

    fun getPhoneNumber() = number

    fun stopTimer() {
        number = ""
        isRunning = false
    }


}