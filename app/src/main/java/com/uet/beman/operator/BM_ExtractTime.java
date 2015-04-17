package com.uet.beman.operator;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by nam on 17/04/2015.
 */
public class BM_ExtractTime {

    private int id;
    private int hour;
    private int minute;
    private boolean repeat;
    private ArrayList<Integer> listDay;
    public void setId(int id){this.id = id;}
    public void setHour(int hour){ this.hour = hour;}
    public void setMinute(int minute){this.minute = minute;}
    public  void setListDay(ArrayList<Integer> listDay){this.listDay = listDay;}

    public void setRepeat(boolean repeat) { this.repeat = repeat;}

    int getDay(int day)
    {
        return day;
    }

    GregorianCalendar checkIfNewBeforeNow(GregorianCalendar newTime, Calendar now, boolean repeat)
    {
        if (newTime.before(now))
        {
            if (repeat) newTime.add(Calendar.WEEK_OF_MONTH, 1);
            else newTime = null;
        }
        return newTime;
    }

    long getTime(int day, int hour, int minute)
    {
        Calendar now = Calendar.getInstance();

        GregorianCalendar newTime = new GregorianCalendar();
        newTime.set(Calendar.YEAR,now.get(Calendar.YEAR));
        newTime.set(Calendar.MONTH, now.get(Calendar.MONTH));
        newTime.set(Calendar.WEEK_OF_MONTH, now.get(Calendar.WEEK_OF_MONTH));
        newTime.set(Calendar.DAY_OF_WEEK, getDay(day));
        newTime.set(Calendar.HOUR_OF_DAY, hour);
        newTime.set(Calendar.MINUTE, minute);
        newTime = checkIfNewBeforeNow(newTime, now, repeat);
        if (newTime == null) return 0;

        return newTime.getTime().getTime();
    }

    long getTime(int day)
    {
        return getTime(day,hour,minute);
    }



    public void setRecord()
    {
        for(int day: listDay)
            {
                long time = getTime(day);
                //if (time != 0 )setMessage(id,time,repeat);
            }

    }

}
