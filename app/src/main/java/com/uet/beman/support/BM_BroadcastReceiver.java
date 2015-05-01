package com.uet.beman.support;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.uet.beman.R;
import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.fragment.BM_FragmentIntelligentMessage;
import com.uet.beman.notification.BM_CustomNotification;
import com.uet.beman.operator.BM_MessageHandler;

/**
 * Created by PhanDuy on 3/15/2015.
 */
public class BM_BroadcastReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Approved", Toast.LENGTH_LONG).show();
        Bundle bundle = intent.getExtras();
        String time = bundle.getString("sendTime");
        boolean reply = bundle.getBoolean("reply");
        if (!reply) sendMessage(time, context);
        else {

            sendReply(bundle.getString("message"), context);
        }
    }

    private void sendMessage(String time, Context context) {
        BM_MessageHandler messageHandler = new BM_MessageHandler();
        messageHandler.send(time, context);
    }

    private void sendReply(String message, Context context) {
        BM_MessageHandler messageHandler = new BM_MessageHandler();
        messageHandler.sending(message, context);
        showNotification(message);
    }

    public void showNotification(String message) {

        Context context = BM_Context.getInstance().getContext();
        String title = "Auto Reply", girl = SharedPreferencesHelper.getInstance().getDestName(),
               content = message + "\nDo you want me to send this for " + girl + " ?";
        BM_CustomNotification notification = new BM_CustomNotification(context, title, content, R.drawable.cake_icon );
        notification.setPendingIntent(BM_FragmentIntelligentMessage.class);
        notification.show(BM_FragmentIntelligentMessage.class);
    }
        /*Context context = BM_Context.getInstance().getContext();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        //.setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, BM_FragmentIntelligentMessage.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(BM_FragmentIntelligentMessage.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }*/

}
