package com.uet.beman.object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thanhpd on 3/12/2015.
 */
public class GroupNode extends Node {

    private List<Node> childNode = new ArrayList<Node>();

    public GroupNode() {
    }

    public GroupNode(String id, String message, String parentId, List<Node> childNode) {
        super(id, message, parentId);
        setChildNodeList(childNode);
    }

    public GroupNode(String id) { super(id, null, "0"); }

    public List<Node> getChildNodeList() { return childNode; }

    public void setChildNodeList(List<Node> childNode) { this.childNode = childNode; }

    public boolean isSentenceNode() { return false; }

    public List<GroupNode> getGroupGroupNode() {
        List<GroupNode> result = new ArrayList<>();
        for (Node i:childNode) {
            if (!i.isSentenceNode()) result.add((GroupNode) i);
        }
        return result;
    }

    public List<SentenceNode> getGroupSentenceNode() {
        List<SentenceNode> result = new ArrayList<SentenceNode>();
        for (Node i:childNode) {
            if (i.isSentenceNode()) result.add((SentenceNode) i);
        }
        return result;
    }
}
