package com.uet.beman.common;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by thanhpd on 3/14/2015.
 */
public class SharedPreferencesHelper {

    private static SharedPreferencesHelper instance = null;

    private SharedPreferencesHelper() {

    }

    public static SharedPreferencesHelper getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesHelper();
        }
        return instance;
    }

    public boolean getCheckLogin() {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("CHECK_LOGIN", false);
    }

    public void setCheckLogin(boolean checkLogin) {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor user = sharedPreferences.edit();
        user.putBoolean("CHECK_LOGIN", checkLogin);
        user.apply();
    }

    public String getUserRealPW() {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("PIN_REAL", "");
    }

    public void setUserRealPW(String realPIN) {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor user = sharedPreferences.edit();
        user.putString("PIN_REAL", realPIN);
        user.apply();
    }

    public String getUserFakePW() {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("PIN_FAKE", "");
    }

    public void setUserFakePW(String fakePIN) {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor user = sharedPreferences.edit();
        user.putString("PIN_FAKE", fakePIN);
        user.apply();
    }

    public void setDestNumber(String destNumber) {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor user = sharedPreferences.edit();
        user.putString("DEST_NUMBER", destNumber);
        user.apply();
    }

    public String getDestNumber() {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("DEST_NUMBER", "");
    }

    public void setDestName(String destName) {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor user = sharedPreferences.edit();
        user.putString("DEST_NAME", destName);
        user.apply();
    }

    public String getDestName() {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("DEST_NAME", "");
    }

    public void setUserName(String name) {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor user = sharedPreferences.edit();
        user.putString("USER_NAME", name);
        user.apply();
    }

    public String getUserName() {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("USER_NAME", "Bro");
    }

    public void setWifiList(String key, ArrayList<String> wifiList) {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("WifiInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor user = sharedPreferences.edit();
        Set<String> wifiSet = new HashSet<>(wifiList);
        user.putStringSet(key, wifiSet);
        user.apply();
    }

    public ArrayList<String> getWifiList(String key) {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("WifiInfo", Context.MODE_PRIVATE);
        Set<String> wifiSet = sharedPreferences.getStringSet(key, null);
        ArrayList<String> wifiList = new ArrayList<>();
        if (wifiSet != null)
            wifiList.addAll(wifiSet);
        Collections.sort(wifiList);
        return wifiList;
    }

    public void setWifiState(String key, boolean state) {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("WifiInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor user = sharedPreferences.edit();
        user.putBoolean(key, state);
        user.apply();
    }

    public boolean getWifiState(String key) {
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("WifiInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }
}
