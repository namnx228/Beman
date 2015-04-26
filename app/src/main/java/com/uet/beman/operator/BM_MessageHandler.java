package com.uet.beman.operator;

import android.os.SystemClock;
import android.provider.Telephony;

import com.uet.beman.common.BM_AlarmManager;
import com.uet.beman.database.BM_ModelScheduler;
import com.uet.beman.object.SentenceNode;

/**
 * Created by nam on 24/04/2015.
 */
public class BM_MessageHandler {


    public void setMesageAlarmTime(SentenceNode node)
    {
        long time = Long.valueOf(node.getSendTime());
        BM_AlarmManager.getInstance().createAlarm(Integer.valueOf(node.getId()), node.getMessage(), time - SystemClock.currentThreadTimeMillis());
    }


    private SentenceNode setSendTime(SentenceNode node, String time)
    {
        node.setSendTime(time);
        return node;
    }

    public void messageReadyToSend(SentenceNode node, String alarmTime)
    {
        node = setSendTime(node, alarmTime);
        BM_ModelScheduler.getInstance().addSchedule(node);
        setMesageAlarmTime(node);

        //set message to table_msg_time
    }

    public void send()
    {
        preSend();
        sending();
        postSend();
    }

    public void preSend()
    {}

    public void postSend()
    {}

    public void sending()
    {}

    public void sendReply()
    {}

}
