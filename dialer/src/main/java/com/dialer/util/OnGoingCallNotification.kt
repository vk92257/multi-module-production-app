package com.dialer.util
/*


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telecom.Call
import android.view.View
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.dialer.R
import com.dialer.CallActivity
import com.dialer.receiver.CallActionReceiver
import kotlin.random.Random

object OnGoingCallNotification {
    private var collapsedView: RemoteViews? = null
    private var notification: Notification? = null
    private const val CALL_NOTIFICATION_ID: Int = 69
    var notificationManager: NotificationManager? = null
    private fun isOreoPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    @RequiresApi(Build.VERSION_CODES.M)
    fun setupNotification(name: String, state: CallType, time: String, ctx: Context, call: Call) {
        notificationManager =
            ctx.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "pda_native Call"
        if (isOreoPlus()) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val name = "call_notification_channel"

            NotificationChannel(channelId, name, importance).apply {
                setSound(null, null)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                notificationManager?.createNotificationChannel(this)
            }
        }

        val openAppIntent = Intent(ctx, CallActivity::class.java)
        openAppIntent.data = call.details.handle
        openAppIntent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
        val openAppPendingIntent = PendingIntent.getActivity(
            ctx, 0, openAppIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )

        val acceptCallIntent = Intent(ctx, CallActionReceiver::class.java)
        acceptCallIntent.action = ACCEPT_CALL
        val acceptPendingIntent = PendingIntent.getBroadcast(
            ctx,
            0,
            acceptCallIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )

        val declineCallIntent = Intent(ctx, CallActionReceiver::class.java)
        declineCallIntent.action = REJECT_CALL


        val declinePendingIntent = PendingIntent.getBroadcast(
            ctx,
            CALL_NOTIFICATION_ID,
            declineCallIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )

      */
/*  val contentTextId = when (state) {
            CallType.INCOMING ->
            Call.STATE_DIALING -> OngoingCallObject.DIALING
            Call.STATE_DISCONNECTED -> OngoingCallObject.CLL_ENDED
            Call.STATE_DISCONNECTING -> OngoingCallObject.CALL_ENDING
            else -> OngoingCallObject.ONGOING
        }*//*

        collapsedView = RemoteViews(ctx.packageName, R.layout.call_notification).apply {
            var nameToShow = ""
            val numberWithName: List<String> = name.split("/")
            if (numberWithName.size > 1) {
                nameToShow = "${numberWithName[0]}  ( Drop ${numberWithName[4]})"
                if (numberWithName[2].isNotBlank()) {
                    setViewVisibility(R.id.notification_Bnumber, View.VISIBLE)
                    setTextViewText(R.id.notification_Bnumber, numberWithName[1])
                    setViewVisibility(R.id.notification_recipient, View.VISIBLE)
                    setTextViewText(R.id.notification_recipient, "${numberWithName[2]}")
                    if (numberWithName[3].isNotBlank()) {
                        when (numberWithName[3].trim()) {
                            "Home" -> {
                                setViewVisibility(R.id.notification_home, View.VISIBLE)
                                setViewVisibility(R.id.notification_work, View.GONE)
                                setViewVisibility(R.id.notification_phone, View.GONE)
                            }
                            "Mobile" -> {
                                setViewVisibility(R.id.notification_home, View.GONE)
                                setViewVisibility(R.id.notification_work, View.GONE)
                                setViewVisibility(R.id.notification_phone, View.VISIBLE)
                            }
                            "Work" -> {
                                setViewVisibility(R.id.notification_home, View.GONE)
                                setViewVisibility(R.id.notification_work, View.VISIBLE)
                                setViewVisibility(R.id.notification_phone, View.GONE)
                            }
                            else -> {
                                setViewVisibility(R.id.notification_home, View.GONE)
                                setViewVisibility(R.id.notification_work, View.GONE)
                                setViewVisibility(R.id.notification_phone, View.GONE)
                                setViewVisibility(R.id.notification_Bnumber, View.GONE)
                                setViewVisibility(R.id.notification_recipient, View.GONE)
                            }
                        }
                    }
                }
            } else {
                setViewVisibility(R.id.notification_home, View.GONE)
                setViewVisibility(R.id.notification_work, View.GONE)
                setViewVisibility(R.id.notification_phone, View.GONE)
                setViewVisibility(R.id.notification_Bnumber, View.GONE)
                setViewVisibility(R.id.notification_recipient, View.GONE)
                nameToShow = name
            }
            setTextViewText(R.id.notification_caller_name, nameToShow)
            setTextViewText(R.id.notification_call_status, contentTextId)
            if (state == Call.STATE_RINGING) setViewVisibility(
                R.id.notification_accept_call, View.VISIBLE
            )
            setTextViewText(R.id.notification_time, time)
            setOnClickPendingIntent(R.id.notification_decline_call, declinePendingIntent)
            setOnClickPendingIntent(R.id.notification_accept_call, acceptPendingIntent)
        }

        val builder = NotificationCompat.Builder(ctx, channelId).setSmallIcon(R.drawable.bjssmall)
            .setContentIntent(openAppPendingIntent).setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(Notification.CATEGORY_CALL).setCustomContentView(collapsedView)
            .setOngoing(true).setSound(null).setAutoCancel(false).setChannelId(channelId)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())

        notification = builder.build()
        notificationManager?.notify(CALL_NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun missCallNotification(name: String, ctx: Context, call: Call) {
        notificationManager =
            ctx.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "pda_native Call "
        if (isOreoPlus()) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val name = "call_notification_channel"

            NotificationChannel(channelId, name, importance).apply {
                setSound(null, null)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                notificationManager?.createNotificationChannel(this)
            }
        }

        val openAppIntent = Intent(ctx, PhoneActivity::class.java)
        openAppIntent.data = call.details.handle
        openAppIntent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
        val openAppPendingIntent = PendingIntent.getActivity(
            ctx, 0, openAppIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )

        val acceptCallIntent = Intent(ctx, CallActionReceiver::class.java)
        acceptCallIntent.action = OngoingCallObject.STATE_ACTIVE
        val acceptPendingIntent = PendingIntent.getBroadcast(
            ctx,
            0,
            acceptCallIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )

        val declineCallIntent = Intent(ctx, CallActionReceiver::class.java)
        declineCallIntent.action = OngoingCallObject.STATE_DISCONNECTED
        val declinePendingIntent = PendingIntent.getBroadcast(
            ctx,
            CALL_NOTIFICATION_ID,
            declineCallIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )

        val contentTextId = OngoingCallObject.CALL_MISSED
        collapsedView = RemoteViews(ctx.packageName, R.layout.call_notification).apply {
            var nameToShow = ""
            val numberWithName: List<String> = name.split("/")
            if (numberWithName.size > 1) {
                nameToShow = "${numberWithName[0]} ( Drop ${numberWithName[4]})"
                if (numberWithName[2].isNotBlank()) {
                    setViewVisibility(R.id.notification_Bnumber, View.VISIBLE)
                    setTextViewText(R.id.notification_Bnumber, "${numberWithName[1]}")
                    setViewVisibility(R.id.notification_recipient, View.VISIBLE)
                    setTextViewText(R.id.notification_recipient, numberWithName[2])
                    if (numberWithName[3].isNotBlank()) {
                        when (numberWithName[3].trim()) {
                            "Home" -> {
                                setViewVisibility(R.id.notification_home, View.VISIBLE)
                                setViewVisibility(R.id.notification_work, View.GONE)
                                setViewVisibility(R.id.notification_phone, View.GONE)
                            }
                            "Mobile" -> {
                                setViewVisibility(R.id.notification_home, View.GONE)
                                setViewVisibility(R.id.notification_work, View.GONE)
                                setViewVisibility(R.id.notification_phone, View.VISIBLE)
                            }
                            "Work" -> {
                                setViewVisibility(R.id.notification_home, View.GONE)
                                setViewVisibility(R.id.notification_work, View.VISIBLE)
                                setViewVisibility(R.id.notification_phone, View.GONE)
                            }
                            else -> {
                                setViewVisibility(R.id.notification_home, View.GONE)
                                setViewVisibility(R.id.notification_work, View.GONE)
                                setViewVisibility(R.id.notification_phone, View.GONE)
                                setViewVisibility(R.id.notification_Bnumber, View.GONE)
                                setViewVisibility(R.id.notification_recipient, View.GONE)
                            }
                        }
                    }
                }
            } else {
                setViewVisibility(R.id.notification_home, View.GONE)
                setViewVisibility(R.id.notification_work, View.GONE)
                setViewVisibility(R.id.notification_phone, View.GONE)
                setViewVisibility(R.id.notification_Bnumber, View.GONE)
                setViewVisibility(R.id.notification_recipient, View.GONE)
                nameToShow = name
            }
            setViewVisibility(R.id.notification_decline_call, View.GONE)
            setTextViewText(R.id.notification_caller_name, nameToShow)
            setTextViewText(R.id.notification_call_status, contentTextId)
            setOnClickPendingIntent(R.id.notification_decline_call, declinePendingIntent)
            setOnClickPendingIntent(R.id.notification_accept_call, acceptPendingIntent)
        }

        val builder = NotificationCompat.Builder(ctx, channelId).setSmallIcon(R.drawable.bjssmall)
            .setContentIntent(openAppPendingIntent).setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(Notification.CATEGORY_CALL).setCustomContentView(collapsedView)
            .setOngoing(true).setSound(null).setAutoCancel(true).setChannelId(channelId)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())

        notification = builder.build()
        notificationManager?.notify(
            call.details.handle.schemeSpecificPart.toString().takeLast(5).toInt(), notification
        )
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun messageNotification(name: String, message: String?, ctx: Context) {
        notificationManager =
            ctx.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "100"
        if (isOreoPlus()) {
            val importance = NotificationManager.IMPORTANCE_HIGH

            NotificationChannel(channelId, name, importance).apply {
                setSound(null, null)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC

                notificationManager?.createNotificationChannel(this)

            }
        }

        val openAppIntent = Intent(ctx, SmsMainActivity::class.java)
        openAppIntent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
        val openAppPendingIntent = PendingIntent.getActivity(
            ctx, 0, openAppIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        */
/* val acceptCallIntent = Intent(ctx, CallActionReceiver::class.java)
         acceptCallIntent.action = OngoingCallObject.STATE_ACTIVE
         val acceptPendingIntent =
             PendingIntent.getBroadcast(ctx, 0, acceptCallIntent, PendingIntent.FLAG_CANCEL_CURRENT)

         val declineCallIntent = Intent(ctx, CallActionReceiver::class.java)
         declineCallIntent.action = OngoingCallObject.STATE_DISCONNECTED
         val declinePendingIntent = PendingIntent.getBroadcast(
             ctx,
             CALL_NOTIFICATION_ID,
             declineCallIntent,
             PendingIntent.FLAG_CANCEL_CURRENT
         )*//*

        collapsedView = RemoteViews(ctx.packageName, R.layout.message_notification).apply {
            var nameToShow = ""
            val numberWithName: List<String> = name.split("/")
            if (numberWithName.size > 1) {
                nameToShow = "${numberWithName[0]} ( Drop ${numberWithName[1]})"
                if (numberWithName[2].isNotBlank()) {
                    setViewVisibility(R.id.notification_Bnumber, View.VISIBLE)
                    setTextViewText(R.id.notification_Bnumber, numberWithName[1])
                    setViewVisibility(R.id.notification_recipient, View.VISIBLE)
                    setTextViewText(R.id.notification_recipient, numberWithName[2])
                    if (numberWithName[4].isNotBlank()) {
                        when (numberWithName[4].trim()) {
                            "Home" -> {
                                setViewVisibility(R.id.notification_home, View.VISIBLE)
                                setViewVisibility(R.id.notification_work, View.GONE)
                                setViewVisibility(R.id.notification_phone, View.GONE)
                            }
                            "Mobile" -> {
                                setViewVisibility(R.id.notification_home, View.GONE)
                                setViewVisibility(R.id.notification_work, View.GONE)
                                setViewVisibility(R.id.notification_phone, View.VISIBLE)
                            }
                            "Work" -> {
                                setViewVisibility(R.id.notification_home, View.GONE)
                                setViewVisibility(R.id.notification_work, View.VISIBLE)
                                setViewVisibility(R.id.notification_phone, View.GONE)
                            }
                            else -> {
                                setViewVisibility(R.id.notification_home, View.GONE)
                                setViewVisibility(R.id.notification_work, View.GONE)
                                setViewVisibility(R.id.notification_phone, View.GONE)
                                setViewVisibility(R.id.notification_Bnumber, View.GONE)
                                setViewVisibility(R.id.notification_recipient, View.GONE)
                            }
                        }
                    }
                }
            } else {
                setViewVisibility(R.id.notification_home, View.GONE)
                setViewVisibility(R.id.notification_work, View.GONE)
                setViewVisibility(R.id.notification_phone, View.GONE)
                setViewVisibility(R.id.notification_call_status, View.GONE)
                setViewVisibility(R.id.notification_Bnumber, View.VISIBLE)
                setViewVisibility(R.id.notification_recipient, View.GONE)
                nameToShow = name
            }
            setTextViewText(R.id.notification_caller_name, nameToShow)
            setTextViewText(R.id.notification_Bnumber, message)

        }

        val pendingIntent = PendingIntent.getActivity(
            ctx,
            0,
            Intent(ctx, SmsMainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(ctx, channelId).setSmallIcon(R.drawable.bjssmall)
            .setContentIntent(openAppPendingIntent).setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(Notification.CATEGORY_CALL).setCustomContentView(collapsedView)
            .setOngoing(true).setSound(null).setAutoCancel(true).setChannelId(channelId)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setFullScreenIntent(pendingIntent, true).setAutoCancel(true)
            .setContentIntent(pendingIntent)

        notification = builder.build()
        notificationManager?.notify(
            Random(25).nextInt(), notification
        )
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun updateNotification(time: String, name: String) {
        val callState = OngoingCallObject.currentCall?.state
        val contentTextId = when (callState) {
            Call.STATE_RINGING -> OngoingCallObject.CALLING
            Call.STATE_DIALING -> OngoingCallObject.DIALING
            Call.STATE_DISCONNECTED -> OngoingCallObject.CLL_ENDED
            Call.STATE_DISCONNECTING -> OngoingCallObject.CALL_ENDING
            else -> OngoingCallObject.ONGOING
        }
        collapsedView?.apply {
            setTextViewText(R.id.notification_time, time)

            val nameToShow: String
            val numberWithName: List<String> = name.split("/")
            if (numberWithName.size > 1) {
                nameToShow = "${numberWithName[0].trim()} (Drop ${numberWithName[4]})"
                if (numberWithName[2].isNotBlank()) {
                    setViewVisibility(R.id.notification_Bnumber, View.VISIBLE)
                    setTextViewText(R.id.notification_Bnumber, " ${numberWithName[1].trim()}")
                    setViewVisibility(R.id.notification_recipient, View.VISIBLE)
                    setTextViewText(R.id.notification_recipient, numberWithName[2].trim())
                    if (numberWithName[3].isNotBlank()) {
                        when (numberWithName[3].trim()) {
                            "Home" -> {
                                setViewVisibility(R.id.notification_home, View.VISIBLE)
                                setViewVisibility(R.id.notification_work, View.GONE)
                                setViewVisibility(R.id.notification_phone, View.GONE)
                            }
                            "Mobile" -> {
                                setViewVisibility(R.id.notification_home, View.GONE)
                                setViewVisibility(R.id.notification_work, View.GONE)
                                setViewVisibility(R.id.notification_phone, View.VISIBLE)
                            }
                            "Work" -> {
                                setViewVisibility(R.id.notification_home, View.GONE)
                                setViewVisibility(R.id.notification_work, View.VISIBLE)
                                setViewVisibility(R.id.notification_phone, View.GONE)
                            }
                            else -> {
                                setViewVisibility(R.id.notification_home, View.GONE)
                                setViewVisibility(R.id.notification_work, View.GONE)
                                setViewVisibility(R.id.notification_phone, View.GONE)
                                setViewVisibility(R.id.notification_Bnumber, View.GONE)
                                setViewVisibility(R.id.notification_recipient, View.GONE)
                            }
                        }
                    }
                }
            } else {
                setViewVisibility(R.id.notification_home, View.GONE)
                setViewVisibility(R.id.notification_work, View.GONE)
                setViewVisibility(R.id.notification_phone, View.GONE)
                setViewVisibility(R.id.notification_Bnumber, View.GONE)
                setViewVisibility(R.id.notification_recipient, View.GONE)
                nameToShow = name
            }
            setTextViewText(R.id.notification_caller_name, nameToShow)
            setTextViewText(R.id.notification_call_status, contentTextId)
            setViewVisibility(R.id.notification_accept_call, View.GONE)

        }
        if (callState == Call.STATE_DISCONNECTED) notification?.flags =
            Notification.FLAG_AUTO_CANCEL
        notificationManager?.notify(CALL_NOTIFICATION_ID, notification)
    }


    fun cancelNotification() {
        notificationManager?.cancel(CALL_NOTIFICATION_ID)
        notification = null
        notificationManager = null
    }

}*/
