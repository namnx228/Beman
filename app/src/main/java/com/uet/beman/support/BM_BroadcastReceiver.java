package com.uet.beman.support;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

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
        boolean reply = bundle.getBoolean("reply");
        if (!reply) sendMessage(time, context);
        else
        {

            sendReply( bundle.getString("message"),context);
        }
    }

    private void sendMessage(String time, Context context)
    {
        BM_MessageHandler messageHandler = new BM_MessageHandler();
        messageHandler.send(time, context);
    }

    private void sendReply(String message, Context context)
    {
        BM_MessageHandler messageHandler = new BM_MessageHandler();
        messageHandler.sending(message, context);
    }

}
