package com.uet.beman.operator;

import android.provider.ContactsContract;
import android.text.format.Time;
import android.util.Pair;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by nam on 14/04/2015.
 */
public class BM_Moment
{
    private int id;
    private ArrayList<Integer> listMoment;
    private ArrayList<Integer> listDay;
    public BM_Moment(int id, ArrayList<Integer> moment, ArrayList<Integer> list)
    {
        this.id = id;
        this.listMoment = moment;
        this.listDay = list;
    }


    int getDay(int day)
    {
        return day;
    }

    private BM_ExtractTime getExtractTime()
    {
        BM_ExtractTime extractTime = new BM_ExtractTime();
        extractTime.setId(id);
        extractTime.setListDay(listDay);
        extractTime.setRepeat(true);
        return  extractTime;
    }

    Long getTime(int day, int moment)
    {
        Pair<Integer,Integer> hourAndMinute = getTime(moment);
        BM_ExtractTime extractTime = getExtractTime();
        return extractTime.getTime(day,hourAndMinute.first, hourAndMinute.second);
    }

    Pair<Integer,Integer> getTime(int moment)
    {
        Random rand = new Random();
        int hour = 0,minute = rand.nextInt(60);
        switch(moment)
        {
            case MORNING:
                hour = rand.nextInt(4) + 6; // 6-9
                break;
            case NOON:
                hour = rand.nextInt(3) + 11; // 11-13
                break;
            case AFTERNOON:
                hour = rand.nextInt(5) + 14; // 14-18
                break;
            case EVENING:
                hour = rand.nextInt(3) + 19; // 19-21
                break;
            case NIGHT:
                hour = rand.nextInt(3) + 22; // 22-24
                break;
        }

        return Pair.create(hour,minute);
    }

    public void setRecord()
    {

        for(int day: listDay)
            for(int moment:listMoment)
            {
                long time = getTime(day,moment);
                //setMessage(id,time);
            }

    }

    private final int MORNING = 0;
    private final int NOON = 1;
    private final int AFTERNOON = 2;
    private final int EVENING = 3;
    private final int NIGHT = 4;

}
