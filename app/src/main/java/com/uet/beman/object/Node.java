package com.uet.beman.object;

/**
 * Created by thanhpd on 3/12/2015.
 */
public abstract class Node {
    private String id;
    private String message;
    private String messageId;
    private String parentId;

    public Node() {
    }

    public Node(String messageId, String message, String parentId) {
//        setId(id);
        setMessageId(messageId);
        setMessage(message);
        setParentId(parentId);
    }

    public Node(String message) {
        setMessage(message);
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getParentId() { return parentId; }

    public void setParentId(String id) { parentId = id; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getMessageId() { return messageId; }

    public void setMessageId(String messageId) { this.messageId = messageId; }

    public abstract boolean isSentenceNode();

}
