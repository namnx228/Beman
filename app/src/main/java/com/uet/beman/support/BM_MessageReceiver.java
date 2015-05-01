package com.uet.beman.support;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.telephony.SmsManager;

import android.util.Log;
import android.widget.Toast;

import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.operator.BM_MessageHandler;

public class BM_MessageReceiver extends BroadcastReceiver {
    public BM_MessageReceiver() {
    }

    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.o
        Log.d("smsReceiver","Received");
        if ( !SharedPreferencesHelper.getInstance().getAutoReply()) return;
        BM_SMS sms = new BM_SMS();
        String message = sms.smsFromGirl();
        if (message != null && sms.checkLastSentMsgFromBeman())
        {

            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            BM_MessageHandler.getInstance().prepareSendReply(message);
        }
        else Toast.makeText(context, "khong gui reply", Toast.LENGTH_LONG).show();


    }
}

