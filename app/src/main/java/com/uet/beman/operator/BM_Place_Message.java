package com.uet.beman.operator;


import android.os.SystemClock;
import android.text.format.Time;

import com.uet.beman.database.BM_ModelScheduler;
import com.uet.beman.object.SentenceNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by nam on 17/04/2015.
 */
public class BM_Place_Message {

    private int getRandomIdMessage(List<Integer> list)
    {
        Random rand = new Random();
        return rand.nextInt(list.size());
    }

    private boolean checkNodeInThatDay(int day, SentenceNode node)
    {
        if (node.getEnabled().compareTo("1") == 0) return true;
        String listDay = node.getDays();
        return listDay.charAt(day) == 1;
    }

    private void chooseRandMessage( ArrayList<SentenceNode>messageRand)
    {
        if (messageRand.size() <= 0) return;
        Random rand = new Random();
        int index = rand.nextInt(messageRand.size());
        SentenceNode chosenMessage = messageRand.get(index);
        //String time = getTime(day, chosenMessage.getLabel()).toString();
        Long time = SystemClock.currentThreadTimeMillis() + 10;
        BM_MessageHandler messageHandler = new BM_MessageHandler();
        messageHandler.messageReadyToSend(chosenMessage, time.toString());
    }

    private  void setWithEachMessage(int day, ArrayList<SentenceNode> list)
    {

        ArrayList<SentenceNode> messageRand =  new ArrayList<SentenceNode>();
        for (SentenceNode node:list)
        {
            if (checkNodeInThatDay(day,node)) messageRand.add(node);

        }
        chooseRandMessage(messageRand);
    }

    public void send(String label)
    {
        ArrayList<SentenceNode> list = BM_ModelScheduler.getInstance().getMessages(label);
        int day = findDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        if (list.size() > 0) setWithEachMessage(day, list);
    }

    private int findDay(int day)
    {
        if (day == 1) return  6;
        return day - 2;
    }
}
