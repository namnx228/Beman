package com.uet.beman.support;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class BM_WifiConnectionReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("WifiReceiver", "Start onReceive");

        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        String s;
        if (networkInfo.isConnected()){
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null){
                s = wifiInfo.getSSID() + "/n" + wifiInfo.getMacAddress();
            }else{
                s = "No Wifi Information";
            }
        }else{
            s = "No Connection";
        }

        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        Log.d("WifiReceiver", s);

        Log.d("WifiReceiver", "End onReceive");
    }
}
