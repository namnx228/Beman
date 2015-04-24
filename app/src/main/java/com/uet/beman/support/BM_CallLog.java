package com.uet.beman.support;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.CallLog;
import android.provider.Telephony;
import android.text.format.Time;

import java.util.Date;

public class BM_CallLog extends Service {

    private static int ONE_DAY = 86400;
    private int geometricCallTime = 0;


    /*protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
    }*/
    public BM_CallLog(){}
    public BM_CallLog(String girlFriend)
    {
        geometricCallTime = calGeometricCallTime(girlFriend);
    }

    public int getGeometricCallTime()
    {
        return  geometricCallTime;
    }

    private boolean checkCallDate(Date date, int timePeriod)
    {
        Time now = new Time();
        now.setToNow();
        return now.second -  date.getTime() < timePeriod ;
    }

    public boolean checkCall(Context context, String girlfriend, int timePeriod) {

        //StringBuffer sb = new StringBuffer();
        boolean stopSend = false;
        Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        //     int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        // int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int nameIndex = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        //  int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        //  sb.append("Call Details :");

        while (managedCursor.moveToNext()) {
            // String phNumber = managedCursor.getString(number);
            //   String callType = managedCursor.getString(type);
            String name = managedCursor.getString(nameIndex);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            if (girlfriend.compareToIgnoreCase(name) == 0)
                stopSend = checkCallDate(callDayTime,timePeriod);
            if (stopSend) break;
        }
        managedCursor.close();
        return stopSend;
    }

    private int calGeometricCallTime(String girlfriend)
    {

        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                null, null, null);

        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int nameIndex = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        Date tmpDate = null;
        int sumTime = 0, numOfCall = 0;
        while (managedCursor.moveToNext()) {

            String name = managedCursor.getString(nameIndex);


            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            if (girlfriend.compareToIgnoreCase(name) == 0) {
                if (tmpDate != null) sumTime+= callDayTime.getTime() - tmpDate.getTime();
                tmpDate = callDayTime;
                numOfCall++;
            }



        }
        managedCursor.close();

        return sumTime/numOfCall;
    }




    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
