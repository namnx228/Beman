package com.uet.beman.operator;

import com.uet.beman.common.SharedPreferencesHelper;

/**
 * Created by nam on 21/04/2015.
 */
public class BM_StopSend
{
    private SharedPreferencesHelper preference = SharedPreferencesHelper.getInstance();
    private boolean girlWifi = preference.getGirlWifiState();
    private boolean girlGps = false;
    public void setGirlWifi(boolean state)
    {
        preference.setGirlWifiState(state);
    }

    public boolean getGirlWifi()
    {
        return girlWifi;
    }

    public void setGirlGps(boolean state)
    {
        preference.setGirlGpsState(state);
    }

    public boolean getGirlGps()
    {
        return girlGps;
    }



}
