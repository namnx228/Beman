package com.uet.beman.schedule;

/**
 * Created by thanhpd on 3/12/2015.
 */
public class SentenceNode extends Node{
    private String sendTime;
    private String sendTimeEpoch;

    public SentenceNode() {
    }

    public SentenceNode(String message, String sendTime) {
        super(message);
        setSendTime(sendTime);
        // setSendTimeEpoch(sendTimeEpoch);
    }

    public String getSendTime() { return sendTime; }

    public void setSendTime(String sendTime) { this.sendTime = sendTime; }

    public String getSendTimeEpoch() { return sendTimeEpoch; }

    public void setSendTimeEpoch(String sendTimeEpoch) { this.sendTimeEpoch = sendTimeEpoch; }

    public boolean isSentenceNode() { return true; }
}
