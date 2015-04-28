package com.uet.beman.support;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.uet.beman.R;
import com.uet.beman.activity.BM_GirlMode;
import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.operator.BM_Place_Message;
import com.uet.beman.operator.BM_StopSend;
import com.uet.beman.util.Constant;

import java.util.ArrayList;

public class BM_WifiConnectionReceiver extends BroadcastReceiver {

    private String getCurrentWifi(Context ctx) {
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String s = wifiInfo.getSSID();
        s = s.substring(1, s.length() - 1);
        return s;
    }

    private boolean isWifiConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Log.d("isWifiConnected", wifiInfo.toString());
        NetworkInfo mobileInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        Log.d("isWifiConnected", mobileInfo.toString());

        return (wifiInfo.isConnected() & !mobileInfo.isConnected());
    }

    private ArrayList<String> getSavedWifi(String key) {
        SharedPreferencesHelper spHelper = SharedPreferencesHelper.getInstance();
        return spHelper.getWifiList(key);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("onReceive", "start onReceive");

        if (!isWifiConnected(context)) {
            Log.d("onReceive", "end onReceive 1");
            return;
        }

        String currWifi = getCurrentWifi(context);
        ArrayList<String> homeWifiList = getSavedWifi(Constant.HOME_WIFI_LIST);
        ArrayList<String> workWifiList = getSavedWifi(Constant.WORK_WIFI_LIST);
        ArrayList<String> girlWifiList = getSavedWifi(Constant.GIRL_WIFI_LIST);


        BM_StopSend stopSend = new BM_StopSend();
        stopSend.setGirlWifi(false);

        BM_Place_Message wifi_message = new BM_Place_Message();

        if (homeWifiList.contains(currWifi)) {
            // At home
            wifi_message.send(Constant.HOME);
            //setMessage
        }
        if (workWifiList.contains(currWifi)) {
            // At work
            wifi_message.send(Constant.WORK);
        }
        if (girlWifiList.contains(currWifi)) {
            // At girl's house
            stopSend.setGirlWifi(true);
        }

        Log.d("onReceive", "end onReceive 2: " + currWifi);

        showNotification(context, currWifi);
    }

    private void showNotification(Context context, String currWifi) {
        // Create pending intent
        Intent intent = new Intent(context, BM_GirlMode.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set notification style
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Demo style");
        inboxStyle.addLine("line 1");
        inboxStyle.addLine("line 2");

        // Create notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
        builder.setContentTitle("Demo Notification");
        builder.setContentText("Current WiFi: " + currWifi);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setVibrate(new long[]{1000});
        builder.setStyle(inboxStyle);

        // Notify notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
