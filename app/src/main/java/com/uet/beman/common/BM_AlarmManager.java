package com.uet.beman.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.uet.beman.support.BM_BroadcastReceiver;

/**
 * Created by PhanDuy on 4/24/2015.
 */
public class BM_AlarmManager extends Service{
    private static AlarmManager alarmManager = null;
    private static BM_AlarmManager instance = null;


    public BM_AlarmManager() {
        if(instance == null) instance = new BM_AlarmManager();

        if(alarmManager == null) alarmManager = (AlarmManager) BM_Application.getInstance().getSystemService(Context.ALARM_SERVICE);
    }

    public static BM_AlarmManager getInstance() {
        return instance;
    }

    /* Create alarm to auto send message on a specific time ahead */
    public void createAlarm( int id, String message, long remainTime) {
        Intent intent = new Intent(getBaseContext(), BM_BroadcastReceiver.class);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + remainTime, pendingIntent);
    }

    /* Cancel an alarm that was set before */
    public void cancelAlarm(int id, String message) {
        Intent intent = new Intent(getBaseContext(), BM_BroadcastReceiver.class);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
