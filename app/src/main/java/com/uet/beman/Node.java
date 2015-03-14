package com.uet.beman;

/**
 * Created by thanhpd on 3/12/2015.
 */
public abstract class Node {
    private int id;
    private String message;
    private int messageId;
    private int parentId;

    public Node() {
    }

    public Node(int messageId, String message, int parentId) {
//        setId(id);
        setMessageId(messageId);
        setMessage(message);
        setParentId(parentId);
    }

    public Node(String message) {
        setMessage(message);
    }

//    public int getId() { return id; }
//
//    public void setId(int id) { this.id = id; }

    public int getParentId() { return parentId; }

    public void setParentId(int id) { parentId = id; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public int getMessageId() { return messageId; }

    public void setMessageId(int messageId) { this.messageId = messageId; }

    public abstract boolean isSentenceNode();

}
