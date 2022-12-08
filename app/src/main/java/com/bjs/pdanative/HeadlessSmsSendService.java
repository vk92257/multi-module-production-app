package com.bjs.pdanative;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class HeadlessSmsSendService extends Service {
    IBinder ib;

    @Override
    public IBinder onBind(Intent intent) {
        // Implement your logic here.
        return ib;
    }
}
