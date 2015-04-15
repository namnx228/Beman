package com.uet.beman.support;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nam on 15/04/2015.
 */

public class BM_SMS {

    private  final long ONE_DAY = 864000;
    private boolean checkSmsDate(Date date)
    {
        Time now = new Time();
        now.setToNow();
        return now.second - date.getTime() < ONE_DAY ;
    }
    public boolean checkSmsHistory(Context context, String girlPhone) {


        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/sent"), null, null, null, null);
        boolean stopSend = false;
        int number = cursor.getColumnIndex("address");
        int date = cursor.getColumnIndexOrThrow("date");
        while (cursor.moveToNext()) {
                Date smsDayTime = new Date(Long.valueOf(date));
                String phoneNumber = cursor.getString(number);
                if (girlPhone.compareToIgnoreCase(phoneNumber) == 0) stopSend = checkSmsDate(smsDayTime);
        }
        cursor.close();
        return stopSend;
    }

}


  /*String[] list = cursor.getColumnNames();
        int a = 0;
        String tenkhacnull = "tôi là nam";
        boolean ok = true;
        String date="day la date", date2 = "day la date2";
        String sentDate = "day la sentdate", SentDate = "day la Sentdate";

        String add = "đây là address";*/
       /*while (cursor.moveToNext()) {


           add = cursor.getString(cursor.getColumnIndex("address"));
           date = cursor.getString(cursor.getColumnIndex("date_sent"));
           date2 = cursor.getString(cursor.getColumnIndexOrThrow("date"));
           if (date.compareTo("0") != 0) sentDate =date;
           if (date2.compareTo("0") != 0) SentDate = date2;
            a++;
       }*/
//        String mes = ;
//        Toast.makeText(getBaseContext(), mes, Toast.LENGTH_LONG).show();



/* String typeOfSMS = null;
                switch (Integer.parseInt(type)) {
                    case 1:
                        typeOfSMS = "INBOX";
                        break;

                    case 2:
                        typeOfSMS = "SENT";
                        break;

                    case 3:
                        typeOfSMS = "DRAFT";
                        break;
                }

            textView.setText(stringBuffer);*/