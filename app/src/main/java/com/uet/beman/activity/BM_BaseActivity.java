package com.uet.beman.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.uet.beman.R;
import com.uet.beman.common.BM_ActivitySetting;

public class BM_BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bm_base);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bm_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = null;

        //noinspection SimplifiableIfStatement
        switch (id) {

            case R.id.activity_message_list:
                intent = new Intent(this, BM_ActivityMessageList.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                intent = new Intent(this, BM_ActivitySetting.class);
                startActivity(intent);
                break;
            case R.id.activity_setup:
                intent = new Intent(this, BM_ActivitySimpleSetup.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
