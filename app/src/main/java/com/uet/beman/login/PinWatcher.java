package com.uet.beman.login;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;

import com.uet.beman.activity.BM_ActivityMain;
import com.uet.beman.common.SharedPreferencesHelper;

/**
 * Created by nam on 09/03/2015.
 */
public class PinWatcher implements TextWatcher
{
    private  String PIN_BEMEN = SharedPreferencesHelper.getInstance().getUserRealPW();
    private  String PIN_GIRL = SharedPreferencesHelper.getInstance().getUserFakePW();
    private  static final int textsize = 4;
    // public static final int BEMEN = 1;
    // public static final int GIRL = 2;
    //public static final int WRONG_PASS = 0;
    private Intent beMenMode;
    private Intent girlMode;
    private Intent wrongPass;
    private BM_ActivityLogin activity;

    public void setBeMenMode(Intent intent)
    {
        beMenMode = intent;
    }

    public void setGirlMode(Intent intent)
    {
        girlMode = intent;
    }

    public void setWrongPass(Intent intent)
    {
        wrongPass = intent;
    }

    public void setActivity(BM_ActivityLogin activity){ this.activity = activity; }

    private Intent pinComplete(String pin)
    {
        if ( pin.length() != textsize) return null;
        if (pin.compareTo(PIN_BEMEN) == 0) return beMenMode;
        if (pin.compareTo(PIN_GIRL) == 0) return girlMode;
        return wrongPass;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
    {
        /*Intent intent = pinComplete(charSequence);
        if (intent != null) activity.startActivity(intent);*/
    }

    @Override
    public void afterTextChanged(Editable text){
        //ActionBarActivity activity = new ActionBarActivity();

        Intent intent = pinComplete(text.toString());
        if (intent != null)
        {
            text.clear();
            activity.startActivity(intent);

        }
    }
}





