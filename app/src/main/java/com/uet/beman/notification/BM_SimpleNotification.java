package com.uet.beman.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

public class BM_SimpleNotification {
    private Context ctx;
    private NotificationCompat.Builder builder;

    public BM_SimpleNotification(Context _ctx) {
        ctx = _ctx;
        builder = new NotificationCompat.Builder(ctx);

        builder.setAutoCancel(true);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setVibrate(new long[]{1000});
    }

    public BM_SimpleNotification(Context ctx, String title, String text, int icon) {
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
        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
