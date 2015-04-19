package com.uet.beman.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

import com.uet.beman.R;
import com.uet.beman.login.PinWatcher;

public class BM_ActivityLogin extends ActionBarActivity {

    EditText pin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bm_activity_login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        setTitle(R.string.app_name);
        pin = (EditText)findViewById(R.id.pin);
        pin.getBackground().setColorFilter(getResources().getColor(R.color.md_blue_800), PorterDuff.Mode.SRC_ATOP);

        PinWatcher pinWatcher = new PinWatcher();
        pinWatcher.setBeMenMode(new Intent(this, BM_ActivitySimpleSetup.class));
        pinWatcher.setGirlMode(new Intent(this, BM_GirlMode.class));
       // pinWatcher.setWrongPass(new Intent(this, BM_WrongPass.class));
        pinWatcher.setActivity(this);
        pin.addTextChangedListener(pinWatcher);
    }
}
