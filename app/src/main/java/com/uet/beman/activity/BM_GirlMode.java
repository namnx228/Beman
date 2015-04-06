package com.uet.beman.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.uet.beman.R;


public class BM_GirlMode extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bm__girl_mode);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bm__girl_mode, menu);
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

    public void gotoWebPage(View view) {
        String uri = "http://www.google.com/";
        switch (view.getId()) {
            case R.id.girl_mode_gift_layout_1:
                uri = "http://www.dienhoatructuyen.vn/";
                break;
            case R.id.girl_mode_gift_layout_2:
                uri = "https://chieuphimquocgia.com.vn/";
                break;
            case R.id.girl_mode_gift_layout_3:
                uri = "http://trangsuctre.vn/";
                break;
            case R.id.girl_mode_gift_layout_4:
                uri = "http://www.thuhuongbakery.com.vn";
                break;
            case R.id.girl_mode_gift_layout_5:
                uri = "http://www.dangoai.com/";
                break;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(browserIntent);
    }
}
