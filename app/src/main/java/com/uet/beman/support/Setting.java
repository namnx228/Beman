package com.uet.beman.support;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.material.widget.CheckBox;
import com.uet.beman.activity.*;
import com.uet.beman.common.BM_ActivitySetting;


/**
 * Created by nam on 12/04/2015.
 */
public class Setting extends ActionBarActivity
{
    private  ActionBarActivity activity;
    private int activityMessageList;
    private int actionSetting;
    private int activitySetup;
    public Setting(int activityMessageList, int actionSetting, int activitySetup)
    {
        this.activityMessageList = activityMessageList;
        this.actionSetting = actionSetting;
        this.activitySetup = activitySetup;

       }

    public Setting(ActionBarActivity act)
    {
       this.activity = act;
    }
    public  void setMenu(ActionBarActivity activity, MenuItem item)
    {
        int id = item.getItemId();
        Intent intent = null;
        //noinspection SimplifiableIfStatement
        if (id == activityMessageList) {
            intent = new Intent(this, BM_ActivityMessageList.class);
            startActivity(intent);
        }
        else if(id == actionSetting)
        {
            intent = new Intent(this, BM_ActivitySetting.class);
            startActivity(intent);
        }
        else if(id == actionSetting)
        {
            intent = new Intent(this, BM_ActivitySimpleSetup.class);
            startActivity(intent);
        }

    }
}
