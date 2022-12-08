package com.dialer.util

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.core.util.ContactsDBConstants.DATE_PATTERN
import com.dialer.CallActivity
import com.dialer.domain.model.CallLogsPojo
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * @Author: Vivek
 * @Date: 30/11/22
 */


@RequiresApi(Build.VERSION_CODES.O)
fun sortingCallLogs(callLogs: HashMap<String, CallLogsPojo>): TreeMap<String, CallLogsPojo> {
    val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH)
    val comparator = Comparator<String> { s1, s2 ->
        LocalDate.parse(s1, formatter).compareTo(LocalDate.parse(s2, formatter))
    }
    val sorted = TreeMap<String, CallLogsPojo>(comparator)
    sorted.putAll(callLogs)
    return sorted
}


fun isAirplaneModeOn(context: Context): Boolean {
    return Settings.System.getInt(
        context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0
    ) != 0
}


@RequiresApi(Build.VERSION_CODES.M)
fun makeCall(phoneNumber: String, context: Context, onError: (String) -> Unit) {
    if (!isAirplaneModeOn(context)) {
        val manager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        val phoneAccountHandle = PhoneAccountHandle(
            ComponentName(
                context.packageName, CallActivity::class.java.name
            ), "myConnectionServiceId"
        )
        val test = Bundle()
        test.putParcelable(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phoneAccountHandle)
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        var number = phoneNumber.takeLast(10)
        if (number.length >= 10) {
            number = "0${number}"
        }
        manager.placeCall(Uri.parse("tel:$number"), test)
    } else onError("Can not make a call check your network or turn off your airplane mode to place a call.")


}

