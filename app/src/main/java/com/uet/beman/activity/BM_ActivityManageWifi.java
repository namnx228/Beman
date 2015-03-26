package com.uet.beman.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.uet.beman.R;

import java.util.List;

public class BM_ActivityManageWifi extends ActionBarActivity {

    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bm_activity_manage_wifi);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bm_activity_manage_wifi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getCurrentWifiInfo(View view){
        ConnectivityManager connectManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        String s;
        if (networkInfo.isConnected()){
            WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null){
//                s += "Wifi name: " + wifiInfo.getSSID() + "\n";
//                s += "MAC address: " + wifiInfo.getMacAddress();
                s = wifiInfo.getSSID() + "\n" + wifiInfo.getMacAddress();
            }else{
                s = "No Wifi Information";
            }
        }else{
            s = "No connection";
        }

//        TextView textView = new TextView(ctx);
//        textView.setTextSize(20);
//        textView.setText(s);
//        setContentView(textView);

        Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
    }

    public void getScanWifi(View view){
//        Toast.makeText(ctx, "Scan Wifi", Toast.LENGTH_LONG).show();
        ConnectivityManager connectManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        String s = "";
        if (networkInfo.isConnected()){
            WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
            List<ScanResult> scanResultList =  wifiManager.getScanResults();
            if (scanResultList == null || scanResultList.isEmpty())
                s = "No Wifi Information";
            else
                for (ScanResult sr: scanResultList)
                    s += sr.SSID + " _ " + sr.BSSID + "\n";
        }else{
            s = "No connection";
        }

        Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
    }

    public void getConfiguredWifi(View view){
//        Toast.makeText(ctx, "Configured Wifi", Toast.LENGTH_LONG).show();
        ConnectivityManager connectManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        String s = "";
        if (networkInfo.isConnected()){
            WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
            List<WifiConfiguration> wifiConfigurationList =  wifiManager.getConfiguredNetworks();
            if (wifiConfigurationList == null || wifiConfigurationList.isEmpty())
                s = "No Wifi Information";
            else
                for (WifiConfiguration wc: wifiConfigurationList)
                    s += wc.SSID + " _ " + wc.BSSID + "\n";
        }else{
            s = "No connection";
        }

        Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
    }
}
