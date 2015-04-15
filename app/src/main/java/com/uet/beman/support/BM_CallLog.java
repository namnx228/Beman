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

    /*protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
    }*/

    private boolean checkCallDate(Date date)
    {
        Time now = new Time();
        now.setToNow();
        return now.second -  date.getTime() < ONE_DAY ;
    }

    public boolean checkCall(Context context, String girlfriend)
        {

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
                    stopSend = checkCallDate(callDayTime);
                if (stopSend) break;
               // String callDuration = managedCursor.getString(duration);
                //String dir = null;
              //  int dircode = Integer.parseInt(callType);
            /*    switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }*/
           /*     sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                        + dir + " \nCall Date:--- " + callDayTime
                        + " \nCall duration in sec :--- " + callDuration);
                sb.append("\n----------------------------------");*/
            }
            managedCursor.close();
            return stopSend;

        }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
