package com.uet.beman;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by thanhpd on 3/14/2015.
 */
public class SharedPreferencesHelper {

    private static SharedPreferencesHelper instance = null;

    private SharedPreferencesHelper() {

    }

    public static SharedPreferencesHelper getInstance() {
        if(instance == null) {
            instance = new SharedPreferencesHelper();
        }
        return instance;
    }

    public String getUserRealPW(){
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("PIN_REAL", "");
    }

    public void setUserRealPW(String realPIN){
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor user = sharedPreferences.edit();
        user.putString("PIN_REAL", realPIN);
        user.apply();
    }

    public String getUserFakePW(){
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("PIN_FAKE", "");
    }

    public void setUserFakePW(String fakePIN){
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor user = sharedPreferences.edit();
        user.putString("PIN_FAKE", fakePIN);
        user.apply();
    }

    public String getDestNumber(){
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("DEST_NUMBER", "");
    }

    public void setDestNumber(String destNumber){
        SharedPreferences sharedPreferences = BM_Application.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor user = sharedPreferences.edit();
        user.putString("PIN_REAL", destNumber);
        user.apply();
    }
}
