package com.uet.beman.operator;

import android.os.SystemClock;

import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.database.BM_ModelScheduler;
import com.uet.beman.object.SentenceNode;
import com.uet.beman.support.BM_CallLog;
import com.uet.beman.support.BM_SMS;
import com.uet.beman.util.Constant;

import java.util.List;

/**
 * Created by nam on 21/04/2015.
 */
public class BM_StopSend
{
    private SharedPreferencesHelper preference = SharedPreferencesHelper.getInstance();


    public void setGirlWifi(boolean state)
    {
        preference.setGirlWifiState(state);
    }

    public void setGirlGps(boolean state)
    {
        preference.setGirlGpsState(state);
    }

    private boolean getGirlWifi()
    {
        return preference.getGirlWifiState();
    }

    private boolean getGirlGps()
    {
        return preference.getGirlGpsState();
    }

    private long miliSecondOfHour(int h)
    {
        return h * 3600 * 1000;
    }
    

    private boolean equal(String a, String b)
    {
        return a.compareTo(b) == 0;
    }
    
    private boolean checkCallAndSmsHistory(int hour)
    {
        BM_SMS sms = new BM_SMS();
        BM_CallLog callLog = new BM_CallLog();
        long timePeriod = miliSecondOfHour(hour);
        return (sms.checkSmsHistory(timePeriod) || callLog.checkCall(timePeriod) );
    }

    private boolean morning_noon_night_eat(SentenceNode node)
    {
        String label = node.getLabel();
        if(!equal(label, Constant.MORNING) 
            && !equal(label, Constant.NOON) 
            && !equal(label, Constant.NIGHT) 
            && !equal(label, Constant.EAT)) 
            return false;
        return checkCallAndSmsHistory(SHORT_PERIOD);
    }
    
    private boolean miss(SentenceNode node)
    {
        if (node.getLabel().compareTo(Constant.MISS) != 0) return false;
        return checkCallAndSmsHistory(LONG_PERIOD);
    }
    
    private boolean place(SentenceNode node)
    {

        if (checkCallAndSmsHistory(SHORT_PERIOD)) return true;
        List<SentenceNode> listNode = BM_ModelScheduler.getInstance().getAllNodes();
        return checkListPlaceNode(listNode);
    }

    public boolean checkStopSend(SentenceNode node)
    {
        if (getGirlGps() || getGirlWifi()) return true;
        if (node.getLabel().length() == 0) return false;
        if (morning_noon_night_eat(node)) return true;
        if (miss(node)) return true;
        return place(node);

    }
    
    private boolean checkListPlaceNode(List<SentenceNode> listNode)
    {
        for(SentenceNode node : listNode)
           if(checkPlaceNode(node)) return true;
        return false;
    }
    
    private boolean checkPlaceNode(SentenceNode node)
    {
        if (notHomeOrWork(node)) return false;
        long now = System.currentTimeMillis(), sendTime = Long.valueOf(node.getSendTime());
        return sendTime - now <= SHORT_PERIOD;
    }

    private boolean notHomeOrWork(SentenceNode sendingNode)
    {
        String label = sendingNode.getLabel();
        return label.compareTo(Constant.WORK) != 0 && label.compareTo(Constant.HOME) != 0;
    }
    
    private int SHORT_PERIOD = 3;
    private int LONG_PERIOD = 12;
    


}
