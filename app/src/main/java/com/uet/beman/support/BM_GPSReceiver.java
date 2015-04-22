package com.uet.beman.support;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.operator.BM_Place_Message;
import com.uet.beman.operator.BM_StopSend;
import com.uet.beman.util.Constant;

public class BM_GPSReceiver extends BroadcastReceiver {

    private final float delta = 0.0000001f;

    public BM_GPSReceiver() {
    }

    private boolean equal(float x, float y)
    {
        return (x - y < delta);
        //return x == y;
    }

    private void sendMessage(String place)
    {
        BM_Place_Message place_message = new BM_Place_Message();
        place_message.sendMessage(place);
    }

    private void checkHome(SharedPreferencesHelper sp, float longitude, float latitude)
    {
        if (equal(longitude, sp.getLongitude(Constant.HOME_LONGITUDE)) &&
            equal(latitude, sp.getLatitude(Constant.HOME_LATITUDE))) sendMessage(Constant.HOME);
    }

    private void checkWork(SharedPreferencesHelper sp, float longitude, float latitude)
    {
        if (equal(longitude, sp.getLongitude(Constant.WORK_LONGITUDE)) &&
                equal(latitude, sp.getLatitude(Constant.WORK_LATITUDE))) sendMessage(Constant.WORK);
    }

    private void checkGirl(SharedPreferencesHelper sp, float longitude, float latitude)
    {
        BM_StopSend stopSend = new BM_StopSend();
        stopSend.setGirlGps(false);
        if (equal(longitude, sp.getLongitude(Constant.GIRL_LONGITUDE)) &&
                equal(latitude, sp.getLatitude(Constant.GIRL_LATITUDE))) stopSend.setGirlGps(true);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        BM_GPStracker gpsTracker = new BM_GPStracker(context);
        float longitude = gpsTracker.getLongitude(), latitude = gpsTracker.getLatitude();

        SharedPreferencesHelper sp = SharedPreferencesHelper.getInstance();

        checkHome(sp,longitude, latitude);
        checkWork(sp, longitude, latitude);
        checkGirl(sp, longitude, latitude);

        Toast.makeText(context, "thay doi vi tri", Toast.LENGTH_LONG);

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
