package com.uet.beman.support;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.uet.beman.operator.BM_MessageHandler;

/**
 * Created by PhanDuy on 3/15/2015.
 */
public class BM_BroadcastReceiver extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Approved", Toast.LENGTH_LONG).show();
        Bundle bundle = intent.getExtras();
        String time = bundle.getString("sendTime");
        sendMessage(time, context);
    }

    private void sendMessage(String time, Context context)
    {
        BM_MessageHandler messageHandler = new BM_MessageHandler();
        messageHandler.send(time, context);
    }

}
