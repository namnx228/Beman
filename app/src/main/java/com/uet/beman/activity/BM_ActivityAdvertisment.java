package com.uet.beman.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.uet.beman.R;
import com.uet.beman.common.SharedPreferencesHelper;

public class BM_ActivityAdvertisment extends ActionBarActivity {

    private void next()
    {
        boolean checkLogin = SharedPreferencesHelper.getInstance().getCheckLogin();
        if (checkLogin)
        {
            Intent intent = new Intent(this,BM_ActivityLogin.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, BM_ActivityMain.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisment);
        Thread adThread = new Thread() {
            @Override
            public void run()
            {
                try
                {
                    super.run();
                    sleep(1000);
                }
                catch (Exception e) {e.printStackTrace();}
                finally
                {
                    next();
                    finish();
                }
            }

        };
        adThread.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_advertisment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
