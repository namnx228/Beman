package com.uet.beman.activity;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.uet.beman.R;
import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.object.ManageWifiDialogFragment;
import com.uet.beman.util.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class BM_ActivityManageWifi extends ActionBarActivity {

    private Context ctx = this;
    private String currentKey = null;

    private EditText editTextHomeWifi;
    private EditText editTextWorkWifi;
    private EditText editTextGirlWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bm_activity_manage_wifi);

        editTextHomeWifi = (EditText) findViewById(R.id.editText_home_wifi);
        editTextWorkWifi = (EditText) findViewById(R.id.editText_work_wifi);
        editTextGirlWifi = (EditText) findViewById(R.id.editText_girl_wifi);

        loadEditText();
    }

    private void loadEditText() {
        showWifiPreview(editTextHomeWifi, getSavedWifi(Constant.HOME_WIFI_KEY));
        showWifiPreview(editTextWorkWifi, getSavedWifi(Constant.WORK_WIFI_KEY));
        showWifiPreview(editTextGirlWifi, getSavedWifi(Constant.GIRL_HOUSE_WIFI_KEY));
    }

    private void showWifiPreview(EditText editText, ArrayList<String> wifiList) {
        if (!wifiList.isEmpty()) {
            String text = "";
            for (String s : wifiList)
                text += s + " , ";
            text = text.substring(0, text.length() - 3);
            editText.setText(text);
        } else {
            editText.setText("");
        }
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

    public void manageHomeWifi(View view) {
        showWifiDialog(Constant.HOME_WIFI_KEY);
    }

    public void manageWorkWifi(View view) {
        showWifiDialog(Constant.WORK_WIFI_KEY);
    }

    public void manageGirlHouseWifi(View view) {
        showWifiDialog(Constant.GIRL_HOUSE_WIFI_KEY);
    }

    private void showWifiDialog(String key) {
        if (!checkEnableWifi()) {
            Toast.makeText(ctx, "Please enable Wifi", Toast.LENGTH_SHORT).show();
            return;
        }

        currentKey = key;
        ArrayList<String> savedWifi = getSavedWifi(key);
        ArrayList<String> configuredWifi = getConfiguredWifi();

        ManageWifiDialogFragment frag = ManageWifiDialogFragment.getInstance(configuredWifi, savedWifi);
        frag.show(getSupportFragmentManager(), "wifi dialog");
    }

    private boolean checkEnableWifi() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    private ArrayList<String> getSavedWifi(String key) {
        SharedPreferencesHelper spHelper = SharedPreferencesHelper.getInstance();
        return spHelper.getWifiList(key);
    }

    private ArrayList<String> getConfiguredWifi() {
        ArrayList<String> res = new ArrayList<>();
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            List<WifiConfiguration> wifiConfig = wifiManager.getConfiguredNetworks();
            if (wifiConfig != null && !wifiConfig.isEmpty())
                for (WifiConfiguration wc : wifiConfig) {
                    String s = wc.SSID;
                    s = s.substring(1, s.length() - 1);
                    res.add(s);
                }
        }
        res = removeDuplicate(res);
        Collections.sort(res);
        return res;
    }

    private ArrayList<String> removeDuplicate(ArrayList<String> list) {
        if (list == null)
            return null;
        HashSet<String> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }

    public void saveWifiList(ArrayList<String> wifiList) {
        if (currentKey != null) {
            Collections.sort(wifiList);
            SharedPreferencesHelper spHelper = SharedPreferencesHelper.getInstance();
            spHelper.setWifiList(currentKey, wifiList);
            currentKey = null;

            loadEditText();
        } else {
            Toast.makeText(ctx, "Save fail!", Toast.LENGTH_SHORT).show();
        }
    }
}
