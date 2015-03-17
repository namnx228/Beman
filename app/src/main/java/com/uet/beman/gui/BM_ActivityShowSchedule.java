package com.uet.beman.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.uet.beman.R;
import com.uet.beman.schedule.BM_ModelScheduler;
import com.uet.beman.schedule.SentenceNode;
import com.uet.beman.schedule.SentenceNodeListAdapter;

import java.util.List;


public class BM_ActivityShowSchedule extends Activity {
    BM_ModelScheduler model;
    List<SentenceNode> nodeList;
    ListView listView;
    SentenceNodeListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bm_activity_show_schedule);
        model = BM_ModelScheduler.getInstance();
        nodeList = model.getAllNodes();
        listView = (ListView) findViewById(R.id.list);
        adapter = new SentenceNodeListAdapter(this, R.layout.itemlistrow, nodeList);
        listView.setAdapter(adapter);
    }
}
