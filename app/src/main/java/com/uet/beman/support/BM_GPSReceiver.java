package com.uet.beman.support;

import android.os.AsyncTask;
import android.util.Log;

import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.operator.BM_Place_Message;
import com.uet.beman.operator.BM_StopSend;
import com.uet.beman.util.Constant;

public class BM_GPSReceiver extends AsyncTask<BM_GPStracker, Integer, Integer> {

    private final float delta = 0.0001f;
    private final long SLEEPING_TIME = 60 * 1000 * 5;

    public BM_GPSReceiver() {
    }

    private boolean equal(float x, float y) {
        return (Math.abs(x - y) < delta);
        //return x == y;
    }

    private void sendMessage(String place) {
        BM_Place_Message place_message = new BM_Place_Message();
        place_message.send(place);
    }

    private void checkHome(SharedPreferencesHelper sp, float longitude, float latitude) {
        if (equal(longitude, sp.getLongitude(Constant.HOME_LONGITUDE)) &&
                equal(latitude, sp.getLatitude(Constant.HOME_LATITUDE))) {
            Log.d("Catch home", "Tao dang o nha");
            sendMessage(Constant.HOME);
        }
    }

    private void checkWork(SharedPreferencesHelper sp, float longitude, float latitude) {
        if (equal(longitude, sp.getLongitude(Constant.WORK_LONGITUDE)) &&
                equal(latitude, sp.getLatitude(Constant.WORK_LATITUDE))) sendMessage(Constant.WORK);
    }

    private void checkGirl(SharedPreferencesHelper sp, float longitude, float latitude) {
        BM_StopSend stopSend = new BM_StopSend();
        stopSend.setGirlGps(false);
        if (equal(longitude, sp.getLongitude(Constant.GIRL_LONGITUDE)) &&
                equal(latitude, sp.getLatitude(Constant.GIRL_LATITUDE))) stopSend.setGirlGps(true);
    }

    @Override
    protected Integer doInBackground(BM_GPStracker... gpsTracker) {


        try {
            while (true) {
                Thread.sleep(SLEEPING_TIME);
                //wait(SLEEPING_TIME);

                Float longitude = gpsTracker[0].getLongitude(), latitude = gpsTracker[0].getLatitude();
                Log.d("GPS", longitude.toString() + latitude.toString());
                SharedPreferencesHelper sp = SharedPreferencesHelper.getInstance();

                checkHome(sp, longitude, latitude);
                checkWork(sp, longitude, latitude);
                checkGirl(sp, longitude, latitude);

                //Toast.makeText(BM_Context.getInstance().getContext(), "thay doi vi tri", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
}
