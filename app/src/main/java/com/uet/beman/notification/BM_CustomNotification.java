package com.uet.beman.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.uet.beman.R;
import com.uet.beman.activity.BM_ActivityLogin;

public class BM_CustomNotification {
    private Context ctx;
    private NotificationCompat.Builder builder;

    public BM_CustomNotification(Context _ctx) {
        ctx = _ctx;
        builder = new NotificationCompat.Builder(ctx);

        builder.setAutoCancel(true);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//        builder.setVibrate(new long[]{1000});
    }

    public BM_CustomNotification(Context ctx, String title, String text, int icon) {
        this(ctx);
        setTitle(title);
        setText(text);
        setSmallIcon(icon);
    }

    public void setTitle(String title) {
        builder.setContentTitle(title);
    }

    public void setText(String text) {
        builder.setContentText(text);
    }

    public void setSmallIcon(int icon) {
        builder.setSmallIcon(icon);
    }

    public void setPendingIntent(Class c) {
        Intent intent = new Intent(ctx, c);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
    }

    public void show() {
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("Demo BIG"));
        Intent i1 = new Intent(ctx, BM_ActivityLogin.class);
        PendingIntent pi1 = PendingIntent.getActivity(ctx, 0, i1, 0);
        builder.addAction(R.drawable.abc_btn_radio_material, "Edit", pi1);
        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}
