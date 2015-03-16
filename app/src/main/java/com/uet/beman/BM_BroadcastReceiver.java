package com.uet.beman;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by PhanDuy on 3/15/2015.
 */
public class BM_BroadcastReceiver extends BroadcastReceiver{

    String SMS_SENT = "SENT";
    String SMS_DELIVERED = "DELIVERED";

    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Approved", Toast.LENGTH_LONG).show();
        Bundle bundle = intent.getExtras();
        String phoneNumber = bundle.getString("phoneNo");
        String message = bundle.getString("message");

        String action = intent.getAction();

        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0,
                new Intent(SMS_SENT), 0);

        PendingIntent deliveredIntent = PendingIntent.getBroadcast(context, 0,
                new Intent(SMS_DELIVERED), 0);

        context.getApplicationContext().registerReceiver(new BM_SentBroadcastReceiver(), new IntentFilter(SMS_SENT));
        context.getApplicationContext().registerReceiver(new BM_DeliveredBroadcastReceiver(), new IntentFilter(SMS_DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentIntent, deliveredIntent);
    }

}
