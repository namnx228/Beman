package com.uet.beman.login;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.uet.beman.R;
import com.uet.beman.activity.BM_ActivityMain;

public class BM_ActivityLogin extends ActionBarActivity {

    EditText pin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bm__activity_login);
        pin = (EditText)findViewById(R.id.pin);
        PinWatcher pinWatcher = new PinWatcher();

        pinWatcher.setBeMenMode(new Intent(this, BM_ActivityMain.class));
        pinWatcher.setGirlMode(new Intent(this, BM_GirlMode.class));
        pinWatcher.setWrongPass(new Intent(this, BM_WrongPass.class));
        pinWatcher.setActivity(this);
        pin.addTextChangedListener(pinWatcher);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bm__activity_login, menu);
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
