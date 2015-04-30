package com.uet.beman.support;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Telephony;
import android.telecom.TelecomManager;
import android.text.format.Time;

import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.database.BM_ModelScheduler;
import com.uet.beman.object.SentenceNode;

import java.util.Date;
import java.util.List;

/**
 * Created by nam on 15/04/2015.
 */

public class BM_SMS extends Service{


    private String standardizePhoneNumber(String number)
    {
        number = number.replaceAll("\\s","");
        if(number.charAt(0) == '+') number = "0" + number.substring(3);
        return number;
    }

    private boolean checkSmsDate(Date date, long timePeriod)
    {
        Time now = new Time();
        now.setToNow();
        return now.second - date.getTime() < timePeriod ;
    }
    public boolean checkSmsHistory(long timePeriod) {
        Cursor cursor = BM_Context.getInstance().getContext().getContentResolver().query(Uri.parse("content://sms/sent"), null, null, null, null);
        String girlPhone = standardizePhoneNumber(SharedPreferencesHelper.getInstance().getDestNumber());
        boolean stopSend = false;
        int number = cursor.getColumnIndex("address");
        int date = cursor.getColumnIndexOrThrow("date");
        while (cursor.moveToNext()) {
                Date smsDayTime = new Date(Long.valueOf(date));
                String phoneNumber = standardizePhoneNumber(cursor.getString(number));
                if (girlPhone.compareToIgnoreCase(phoneNumber) == 0) stopSend = checkSmsDate(smsDayTime, timePeriod);
        }
        cursor.close();
        return stopSend;
    }

    public boolean checkLastSentMsgFromBeman()
    {
        Cursor cursor = BM_Context.getInstance().getContext().getContentResolver().query(Uri.parse("content://sms/sent"), null, null, null, null);
        String girlPhone = standardizePhoneNumber(SharedPreferencesHelper.getInstance().getDestNumber());
        int number = cursor.getColumnIndexOrThrow("address");
        int body = cursor.getColumnIndexOrThrow(Telephony.Sms.BODY);
        while (cursor.moveToNext()) {

            String phoneNumber = standardizePhoneNumber(cursor.getString(number));
            if (girlPhone.compareToIgnoreCase(phoneNumber) == 0)
            {
                String bodyMessage = cursor.getString(body);
                List<SentenceNode> list = BM_ModelScheduler.getInstance().getSentenceNodeByMessage(bodyMessage);
                return list !=null && list.size() > 0 ;
            }
        }
        return false;
    }

    public String smsFromGirl()
    {
        Cursor cursor = BM_Context.getInstance().getContext().getContentResolver().query(Uri.parse("content://sms/sent"), null, null, null, null);
        String girlPhone = standardizePhoneNumber(SharedPreferencesHelper.getInstance().getDestNumber());
        int number = cursor.getColumnIndexOrThrow("address");
        int body = cursor.getColumnIndexOrThrow(Telephony.Sms.BODY);
        cursor.moveToFirst();
        String phoneNumber = standardizePhoneNumber(cursor.getString(number));
        if (girlPhone.compareToIgnoreCase(phoneNumber) == 0) return cursor.getString(body);
        return null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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