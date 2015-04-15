package com.uet.beman.operator;

/**
 * Created by nam on 14/04/2015.
 */
public class BM_Moment
{
    public BM_Moment()
    {

    }
    private int id;
    //private List<Day> days
    //private List<moment>

    /*daytime getTime(Day day)
    {
        daytime = getTimeThisWeek(day);
        now.setToNow();
        if (daytime < now) daytime = daytime + daytimeaWeek();
        return daytime;
    }*/

    /*hourtime getTime(Moment moment)
    {
        Time time;
        switch(moment)
        {
            case MORNING:
                time = random(Time(6),Time(9));
                break;
            case NOON:
                time = random(Time(11),Time(13));
                break;
            case AFTERNOON:
                time = random(Time(14),Time(18));
                break;
            case EVENING:
                time = random(Time(19),Time(21));
                break;
            case NIGHT:
                time = random(Time(22),Time(0));
                break;
        }
        if (time<now) time = time + getTimeAWeek();
        return time;
    }*/

    public void setRecord()
    {
        /*for(Day day: days)
            for(Monent moment:monents)
            {
                daytime = getTime(day);
                hourtime = getTime(moment);
                time = Time(daytime,hourtime);
                setMessage(id,time);
            }*/

    }
}
