package com.uet.beman.common;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uet.beman.R;
import com.uet.beman.support.BM_GPStracker;

public class BM_ActivitySetting extends ActionBarActivity {

    final private boolean HIDE = false;
    final private boolean SHOW = !HIDE;

    EditText pinReal, pinFake;
    Button set_pin;
    TextView textPinReal, textPinFake;
    private boolean enableLogin = SharedPreferencesHelper.getInstance().getCheckLogin();

    private void setDefaultChecked()
    {
        if (enableLogin)
        {
            CheckBox checkBox = (CheckBox)findViewById(R.id.checkbox);
            checkBox.setChecked(true);
        }
    }

    private  void enterPinPlace(boolean status)
    {
        int visibleStatus = 0;
        if (status) visibleStatus = View.VISIBLE;
        else visibleStatus = View.GONE;
        pinReal.setVisibility(visibleStatus);
        pinFake.setVisibility(visibleStatus);
        set_pin.setVisibility(visibleStatus);
        textPinReal.setVisibility(visibleStatus);
        textPinFake.setVisibility(visibleStatus);

    }
    private void enableLoginMode()
    {
        enableLogin = true;
        SharedPreferencesHelper.getInstance().setCheckLogin(enableLogin);
        enterPinPlace(SHOW);
    }

    private void disableLoginMode()
    {
        enableLogin = false;
        SharedPreferencesHelper.getInstance().setCheckLogin(enableLogin);
        enterPinPlace(HIDE);
    }

   /* public boolean getLoginPermission()
    {
        return enableLogin;
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bm__activity_setting);
        pinReal = (EditText)findViewById(R.id.pin_real);
        pinFake = (EditText)findViewById((R.id.pin_fake));
        set_pin = (Button)findViewById(R.id.SetPin);
        textPinReal = (TextView)findViewById(R.id.text_pin_real);
        textPinFake = (TextView)findViewById(R.id.text_pin_fake);
        setDefaultChecked();
        enterPinPlace(enableLogin);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bm__activity_setting, menu);
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



    public void onCheckboxClicked(View view)
    {
        boolean checked = ((CheckBox)view).isChecked();
        if (view.getId() == R.id.checkbox)
        {
            if (checked) enableLoginMode();
            else disableLoginMode();
        }
    }
    public void setPin(View view)
    {
        if (view.getId() == R.id.SetPin) {
            SharedPreferencesHelper.getInstance().setUserRealPW(pinReal.getText().toString());
            SharedPreferencesHelper.getInstance().setUserFakePW((pinFake.getText().toString()));
        }
        String message = "your pin is " +
                          SharedPreferencesHelper.getInstance().getUserRealPW() +
                          "\nyour fake pin is" +
                          SharedPreferencesHelper.getInstance().getUserFakePW();
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();

    }
    public void testGPS(View view)
    {
        if(view.getId() == R.id.test_gps)
        {
            BM_GPStracker gps = new BM_GPStracker(this);
            if (gps.canGetLocation())
            {
                Double latitude = gps.getLatitude(),
                       longtitude = gps.getLongitude();
                String mes = "your place is: Latutude = " + latitude.toString() + "\n Longtitude = " + longtitude.toString();
                Toast.makeText(getBaseContext(), mes, Toast.LENGTH_LONG).show();

            }
        }
    }
}
