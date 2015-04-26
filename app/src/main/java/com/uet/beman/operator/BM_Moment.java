package com.uet.beman.operator;

import android.provider.ContactsContract;
import android.text.format.Time;
import android.util.Pair;

import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.database.BM_ModelScheduler;
import com.uet.beman.object.SentenceNode;
import com.uet.beman.support.BM_CallLog;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Created by nam on 14/04/2015.
 */
public class BM_Moment
{



    private HashMap<Integer,String> mapIndexToMoment()
    {
        HashMap<Integer,String> result = new HashMap<>();
        result.put(0, MORNING);
        result.put(1, NOON);
        result.put(2,NIGHT);
        result.put(3, EAT);
        result.put(4, MISS);
        return result;
    }

    private ArrayList<String> getListMoment(int day)
    {
        ArrayList<String> result = new ArrayList<>();
        String dayPrefence = SharedPreferencesHelper.getInstance().getDaysPreferences();
        HashMap<Integer, String> map = mapIndexToMoment();

        for(int i = 0 ;i < 5;i++)
        {
            int j = i + day * 5;
            if (dayPrefence.charAt(j) == 1) result.add(map.get(i));
        }
        return result;
    }

    private boolean checkNodeInThatDay(int day, SentenceNode node)
    {
        String listDay = node.getDays();
        return listDay.charAt(day) == 1;
    }

    private void chooseRandMessage(int day, ArrayList<SentenceNode>messageRand)
    {
        Random rand = new Random();
        int index = rand.nextInt(messageRand.size());
        SentenceNode chosenMessage = messageRand.get(index);
        String time = getTime(day, chosenMessage.getLabel()).toString();

        BM_MessageHandler messageHandler = new BM_MessageHandler();
        messageHandler.messageReadyToSend(chosenMessage, time);

    }

    private  void setWithEachMessage(int day, ArrayList<SentenceNode> list)
    {

        ArrayList<SentenceNode> messageRand =  new ArrayList<SentenceNode>();
        for (SentenceNode node:list)
        {
            if (checkNodeInThatDay(day,node)) messageRand.add(node);

        }
        chooseRandMessage(day, messageRand);
    }

    private void setWithEachMoment(int day, ArrayList<String> list)
    {
        BM_ModelScheduler modelScheduer = new BM_ModelScheduler();
        for(String moment:list)
        {
            ArrayList<SentenceNode> listSentence = modelScheduer.getMessages(moment);
            setWithEachMessage(day, listSentence);
        }
    }

    private GregorianCalendar checkIfNewBeforeNow(GregorianCalendar newTime, Calendar now)
    {
        if (newTime.before(now)) newTime.add(Calendar.WEEK_OF_MONTH, 1);
        return newTime;
    }

    private int getDay(int day)
    {
        return day;
    }

    private long getTime(int day, int hour, int minute)
    {
        Calendar now = Calendar.getInstance();

        GregorianCalendar newTime = new GregorianCalendar();
        newTime.set(Calendar.YEAR,now.get(Calendar.YEAR));
        newTime.set(Calendar.MONTH, now.get(Calendar.MONTH));
        newTime.set(Calendar.WEEK_OF_MONTH, now.get(Calendar.WEEK_OF_MONTH));
        newTime.set(Calendar.DAY_OF_WEEK, getDay(day));
        newTime.set(Calendar.HOUR_OF_DAY, hour);
        newTime.set(Calendar.MINUTE, minute);
        newTime = checkIfNewBeforeNow(newTime, now);
        if (newTime == null) return 0;

        return newTime.getTime().getTime();
    }

    private Long getTime(int day, String moment)
    {
        Pair<Integer,Integer> hourAndMinute = getTime(moment);
        return getTime(day,hourAndMinute.first, hourAndMinute.second);
    }

    private boolean equal(String x, String y)
    {
        return x.compareTo(y) == 1;
    }

    private int min(int x, int y)
    {
        if (x < y) return x;
        return  y;
    }

    private int chooseForMeal()
    {
        Random rand = new Random();
        int lunch = rand.nextInt(2) + 11, dinner = rand.nextInt(3) + 18, choose = rand.nextInt(2) ;
        if (choose == 0) return lunch;
        return dinner;
    }

    private int chooseForMiss()
    {
        BM_CallLog callLog = new BM_CallLog(SharedPreferencesHelper.getInstance().getDestName());
        return min(callLog.getGeometricCallTime(), MISS_TIME) + callLog.getLastTimeCall();
    }

    Pair<Integer,Integer> getTime(String moment)
    {
        Random rand = new Random();
        int hour = 0,minute = rand.nextInt(60);
        if(equal(moment,MORNING)) hour = rand.nextInt(4) + 6; // 6-9
        if(equal(moment,NOON)) hour = rand.nextInt(3) + 11; // 11-13
        if(equal(NIGHT,moment))  hour = rand.nextInt(3) + 22; // 22-24
        if(equal(moment,EAT)) hour = chooseForMeal();
        if(equal(moment,MISS)) hour = chooseForMiss();

        return Pair.create(hour,minute);
    }

    public void setSchedule()
    {
        for(int day = 0;day<7;day++)
        {
            ArrayList<String> listMoment = getListMoment(day);
            setWithEachMoment(day, listMoment);
        }
    }

    private final String MORNING = "|morning|";
    private final String NOON = "|noon|";
    private final String NIGHT = "|night|";
    private final String EAT = "|eat|";
    private final String MISS = "|miss|";
    private final int MISS_TIME = 43200;

}
