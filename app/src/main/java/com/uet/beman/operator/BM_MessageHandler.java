package com.uet.beman.operator;

import com.uet.beman.object.SentenceNode;

/**
 * Created by nam on 24/04/2015.
 */
public class BM_MessageHandler {
    public void messageReadyToSend(SentenceNode node, String alarmTime)
    {
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
