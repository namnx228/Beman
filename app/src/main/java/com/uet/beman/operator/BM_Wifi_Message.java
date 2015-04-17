package com.uet.beman.operator;


import android.text.format.Time;

import com.uet.beman.database.BM_ModelScheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by nam on 17/04/2015.
 */
public class BM_Wifi_Message {

    private String place;

    public void setPlace(String place) { this.place = place;}

    public BM_Wifi_Message(String place)
    {
        setPlace(place);
    }

    public void setMessage(int id)
    {
        BM_ModelScheduler.getInstance().addPlaceMessage(id,place);
    }

    private int getRandomIdMessage(List<Integer> list)
    {
        Random rand = new Random();
        return rand.nextInt(list.size());
    }

    public void getMessage()
    {
        List<Integer> list = BM_ModelScheduler.getInstance().getIdMessageByPlace(place);
        int chosenId = getRandomIdMessage(list);

        Time now = new Time();
        now.setToNow();
        long time = now.second;
       //setMessage(id,time,false)
    }
}
