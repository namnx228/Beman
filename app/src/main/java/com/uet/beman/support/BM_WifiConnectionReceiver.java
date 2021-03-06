package com.uet.beman.support;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.uet.beman.R;
import com.uet.beman.activity.BM_ActivityLogin;
import com.uet.beman.activity.BM_ActivityMain;
import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.notification.BM_CustomNotification;
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
            if(SharedPreferencesHelper.getInstance().getWifiCheck(Constant.HOME_WIFI_CHECK))
                wifi_message.send(Constant.HOME);
            //setMessage
        }
        if (workWifiList.contains(currWifi)) {
            // At work
            if(SharedPreferencesHelper.getInstance().getWifiCheck(Constant.WORK_WIFI_CHECK))
                wifi_message.send(Constant.WORK);
        }
        if (girlWifiList.contains(currWifi)) {
            // At girl's house
            if(SharedPreferencesHelper.getInstance().getWifiCheck(Constant.GIRL_WIFI_CHECK))
                stopSend.setGirlWifi(true);
        }

        Log.d("onReceive", "end onReceive 2: " + currWifi);

        showNotification(context, currWifi);
    }

    private void showNotification(Context context, String currWifi) {
//        BM_SimpleNotification noti = new BM_SimpleNotification(context, "Demo", "Current Wifi: " + currWifi, R.drawable.abc_btn_rating_star_on_mtrl_alpha);
        BM_CustomNotification noti = new BM_CustomNotification(context, "Demo", "Current Wifi: " + currWifi, R.drawable.abc_btn_rating_star_on_mtrl_alpha);
        noti.setPendingIntent(BM_ActivityLogin.class);
        noti.show(BM_ActivityMain.class);
    }
}
