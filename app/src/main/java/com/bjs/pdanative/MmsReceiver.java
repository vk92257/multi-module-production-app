package com.bjs.pdanative;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MmsReceiver extends BroadcastReceiver {
    String TAG = "SMS APP";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.e(TAG, "In SMS APP class");

    }
}
