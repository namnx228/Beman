package com.uet.beman.support;

import com.uet.beman.database.BM_ModelScheduler;
import com.uet.beman.object.SentenceNode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by PhanDuy on 3/15/2015.
 */
public class BM_NodeListHandler {

    List<SentenceNode> nodeList, messageList;
    BM_ModelScheduler model;

    public static final String VALUE_MORNING = "*morning*";
    public static final String VALUE_NOON = "*noon*";
    public static final String VALUE_AFTERNOON = "*afternoon*";
    public static final String VALUE_EVENING = "*evening*";
    public static final String VALUE_NIGHT = "*night*";

    public BM_NodeListHandler() {
        model = BM_ModelScheduler.getInstance();
    }

    public List<SentenceNode> getScheduleList() {
        return model.getAllNodes();
    }

    public List<SentenceNode> getMessageList(String label) {
        messageList = model.getMessages(label);
        return messageList;
    }

    public void updateItemInMessageList(SentenceNode node) {
        model.updateDialog(node);
    }

    public long getEarliestTimeInEpoch() {
        SentenceNode earliestNode = nodeList.get(0);
        nodeList.remove(0);
        model.updateEarliestNode();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        long epoch = 0;
        String epochTime = "0";
        try {
            date = df.parse(earliestNode.getSendTime());
            epoch = date.getTime();
            epochTime = String.valueOf(epoch);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return epoch;
    }

    public long getCurrentTimeInEpoch() {
        Calendar c = Calendar.getInstance();
        long epoch = c.getTimeInMillis();
        return epoch;
    }

    public long getRemainingTime() {
        return (getEarliestTimeInEpoch() - getCurrentTimeInEpoch());
    }

    public long addNewNode(SentenceNode node) {
        return model.addSentence(node);
    }
}
