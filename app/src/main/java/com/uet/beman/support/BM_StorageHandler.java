package com.uet.beman.support;

import com.uet.beman.object.SentenceNode;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PhanDuy on 4/19/2015.
 */
public class BM_StorageHandler {
    public static BM_StorageHandler instance = null;
    private BM_NodeListHandler handler;
    HashMap<String, List<SentenceNode>> messageSet;

    private BM_StorageHandler() {
        handler = new BM_NodeListHandler();
        messageSet = new HashMap<>();
    }

    public static BM_StorageHandler getInstance() {
        if(instance == null) instance = new BM_StorageHandler();
        return instance;
    }

    public void initListInMessageSet(String label) {
        List<SentenceNode> nodeList = handler.getMessageList(label);
        messageSet.put(label, nodeList);
    }

    public List<SentenceNode> getListInMessageSet(String label) {
        return messageSet.get(label);
    }

    public void updateItemInMessageSet(String label, SentenceNode node) {
        int idx = Integer.parseInt(node.getId());
        String newLabel = "\"" + label + "\"";
        List<SentenceNode> nodeList = getListInMessageSet(newLabel);
        nodeList.set(idx-1, node);
        messageSet.put(newLabel, nodeList);
        handler.updateItemInMessageList(node);
    }

    public void addItemInMessageSet(SentenceNode node) {
        String label = "\"" + node.getLabel() +"\"";
        long idx = handler.addNewNode(node);
        node.setId(String.valueOf(idx));
        List<SentenceNode> nodeList = getListInMessageSet(label);
        nodeList.add(node);
        messageSet.put(label, nodeList);
    }
}
