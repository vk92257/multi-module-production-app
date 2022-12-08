package com.bjs.pdanative;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;


public class IncomingSms extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, final Intent intent) {

    }

    private ContentValues addMessageToSent(String telNumber, String senderName, String messageBody, Context ctx) {
        return null;
    }

    public String getContactDisplayNameByNumber(String number) {


        return "name";
    }
}