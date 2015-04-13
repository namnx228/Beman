package com.uet.beman.object;

/**
 * Created by thanhpd on 3/12/2015.
 */
public class SentenceNode extends Node{
    private String sendTime;
    private String sendTimeEpoch;
    private String label;
    private String language;
    private String enabled;
    private String days;

    public SentenceNode() {
    }

    public SentenceNode(String message, String sendTime) {
        super(message);
        setSendTime(sendTime);
        enabled = "0";
        days = "0000000";
        // setSendTimeEpoch(sendTimeEpoch);
    }

    public SentenceNode(String message, String sendTime, String checkStatus) {
        super(message);
        setSendTime(sendTime);
        setEnabled(checkStatus);
    }

    public String getEnabled() { return enabled; }

    public void setEnabled(String checkStatus) { this.enabled = checkStatus; }

    public String getDays() { return days; }

    public void setDays(String days) { this.days = days; }

    public String getSendTime() { return sendTime; }

    public void setSendTime(String sendTime) { this.sendTime = sendTime; }

    public String getSendTimeEpoch() { return sendTimeEpoch; }

    public void setSendTimeEpoch(String sendTimeEpoch) { this.sendTimeEpoch = sendTimeEpoch; }

    public String getLabel() { return label; }

    public void setLabel(String label) { this.label = label; }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }

    public boolean isSentenceNode() { return true; }
}
