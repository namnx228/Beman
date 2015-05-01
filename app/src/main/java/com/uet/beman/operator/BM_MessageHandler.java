package com.uet.beman.operator;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import com.uet.beman.common.BM_AlarmManager;
import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.database.BM_ModelScheduler;
import com.uet.beman.fragment.BM_FragmentIntelligentMessage;
import com.uet.beman.object.SentenceNode;
import com.uet.beman.support.BM_Context;
import com.uet.beman.support.BM_DeliveredBroadcastReceiver;
import com.uet.beman.support.BM_HandlerResponse;
import com.uet.beman.support.BM_MakeBotRequest;
import com.uet.beman.support.BM_SentBroadcastReceiver;
import com.uet.beman.util.Constant;

import java.util.Calendar;
import java.util.List;

/**
 * Created by nam on 24/04/2015.
 */
public class BM_MessageHandler {

    private SentenceNode sendingNode;
    private boolean stopSend;

    private static BM_MessageHandler instance;
    public static BM_MessageHandler getInstance()
    {
        if (instance == null) instance = new BM_MessageHandler();
        return instance;
    }


    public void setMesageAlarmTime(SentenceNode node)
    {
        BM_AlarmManager.getInstance().createAlarm(Integer.valueOf(node.getId()), node.getSendTime());
        Time now = new Time();
        now.set(Long.valueOf(node.getSendTime()));
       // Toast.makeText(BM_Context.getInstance().getContext(), now.toString(), Toast.LENGTH_LONG).show();
        Log.d("time send",now.toString());
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

    public void send(String time, Context context)
    {

        preSend(time);
        send(context);
        postSend();
    }

    private void preSend(String time)
    {

        //xoa 1 node trong schedule
        List<SentenceNode> listNode = BM_ModelScheduler.getInstance().getSentenceNodeByDate(time);
        delNodeInSchedule(listNode);
        if (listNode.size() > 0) sendingNode = listNode.get(0);
        else
        {
            stopSend = true;
            return;
        }
        BM_StopSend stopSend = new BM_StopSend();
        this.stopSend = stopSend.checkStopSend(sendingNode);
    }

    private void postSend()
    {
        if (sendingNode != null && notHomeOrWorkOrReply(sendingNode))
        {
            BM_Moment moment = new BM_Moment();
            moment.setSchedule(findDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)), sendingNode.getLabel());
        }
    }

    private void send(Context context)
    {
        if (stopSend) return;
        sending(sendingNode.getMessage(), context);

    }

    public void sending(String message, Context context)
    {
        String SMS_SENT = "SENT";
        String SMS_DELIVERED = "DELIVERED";
        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0,
                new Intent(SMS_SENT), 0);

        PendingIntent deliveredIntent = PendingIntent.getBroadcast(context, 0,
                new Intent(SMS_DELIVERED), 0);

        context.getApplicationContext().registerReceiver(new BM_SentBroadcastReceiver(), new IntentFilter(SMS_SENT));
        context.getApplicationContext().registerReceiver(new BM_DeliveredBroadcastReceiver(), new IntentFilter(SMS_DELIVERED));

        String phoneNumber = SharedPreferencesHelper.getInstance().getDestNumber();
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentIntent, deliveredIntent);
    }



    public void prepareSendReply(String messageFromGirl)
    {
        String request = BM_MakeBotRequest.makeRequest(messageFromGirl);
        BM_HandlerResponse handlerResponse = new BM_HandlerResponse(request);
        handlerResponse.start();
    }

    public void sendReply(String message, String time)
    {
        int id = 123456;
        BM_AlarmManager.getInstance().createAlarmReply(id, time, message);

      /*  if (message == null) message = "tao la null";
        Log.d("AI",message);*/
    }

    private void delNodeInSchedule(List<SentenceNode> list)
    {
        for(SentenceNode node : list) BM_ModelScheduler.getInstance().delMessageInSchedule(node);
    }

    private int findDay(int day)
    {
        if (day == 1) return  6;
        return day - 2;
    }

    private boolean notHomeOrWorkOrReply(SentenceNode sendingNode)
    {
        String label = sendingNode.getLabel();
        return label.length() > 0 && label.compareTo(Constant.WORK) != 0 && label.compareTo(Constant.HOME) != 0;
    }


}
