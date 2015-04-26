package com.uet.beman.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uet.beman.R;
import com.uet.beman.common.BM_CustomViewPager;
import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.database.BM_ModelScheduler;
import com.uet.beman.fragment.BM_FragmentDays;
import com.uet.beman.fragment.BM_FragmentGps;
import com.uet.beman.fragment.BM_FragmentInfo;
import com.uet.beman.fragment.BM_FragmentIntelligentMessage;
import com.uet.beman.fragment.BM_FragmentMessageDialog;
import com.uet.beman.fragment.BM_FragmentMessageList;
import com.uet.beman.fragment.BM_FragmentPhoneNumber;
import com.uet.beman.fragment.BM_FragmentWelcomeScreen;
import com.uet.beman.fragment.BM_FragmentWifi;
import com.uet.beman.fragment.BM_MomentConfirm;
import com.uet.beman.object.SentenceNode;
import com.uet.beman.operator.BM_MessageHandler;
import com.uet.beman.operator.BM_Moment;
import com.uet.beman.support.BM_MessageCardHandler;
import com.uet.beman.support.BM_StorageHandler;
import com.uet.beman.support.BM_ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class BM_ActivitySimpleSetup extends BM_BaseActivity implements
        BM_FragmentWelcomeScreen.OnFragmentInteractionListener, BM_FragmentInfo.OnFragmentInteractionListener,
        BM_FragmentPhoneNumber.OnFragmentInteractionListener, BM_FragmentWifi.OnFragmentInteractionListener,
        BM_FragmentDays.OnFragmentInteractionListener, BM_FragmentMessageList.OnFragmentInteractionListener,
        BM_FragmentIntelligentMessage.OnFragmentInteractionListener,
        BM_FragmentMessageDialog.MessageDialogListener,
        BM_FragmentGps.OnFragmentInteractionListener,
        BM_MomentConfirm.OnFragmentInteractionListener{

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private BM_CustomViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
//    private BM_ModelScheduler model;
//    private BM_StorageHandler storageHandler;
    private BM_MessageCardHandler messageCardHandler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bm_activity_simple_setup);

        // Instantiate a ViewPager and a PagerAdapter.
        checkIntrusion();
        mPager = (BM_CustomViewPager) findViewById(R.id.pager);
        mPagerAdapter = new BM_ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
//        model = BM_ModelScheduler.getInstance();
//        storageHandler = BM_StorageHandler.getInstance();
        messageCardHandler = BM_MessageCardHandler.getInstance();
        setAlarmFromDb();

        new Thread(new Task()).start();


//        mPager.setPageTransformer(true, new DepthPageTransformer());
//        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                //invalidateOptionsMenu();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
           return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    public void onFragmentInteraction(boolean status) {
        mPager.setPagingEnabled(status);
    }

    public void onDialogPositiveClick(DialogFragment dialog, SentenceNode currentNode) {
//        model.updateDialog(currentNode);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
        if(mPager.getCurrentItem() == 0 && fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(fragment);
            ft.attach(fragment);
            ft.commit();
            messageCardHandler.setCardView(null, 0, currentNode.getLabel(), this, fragment);
        }
    }

    public void onDialogNegativeClick(DialogFragment dialog) {
        // do nothing
    }

    // detect instrusion

    private void setDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Có người xâm nhập");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { /*cancel dialog */}
        }).show();
    }
    // for set color for dialog
    public  static void colorAlertDialogTitle(AlertDialog dialog, int color) {
        int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        if (dividerId != 0) {
            View divider = dialog.findViewById(dividerId);
            divider.setBackgroundColor(color);
        }

        int textViewId = dialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
        if (textViewId != 0) {
            TextView tv = (TextView) dialog.findViewById(textViewId);
            tv.setTextColor(color);
        }

        int iconId = dialog.getContext().getResources().getIdentifier("android:id/icon", null, null);
        if (iconId != 0) {
            ImageView icon = (ImageView) dialog.findViewById(iconId);
            icon.setColorFilter(color);
        }
    }


    private void checkIntrusion()
    {
        if (SharedPreferencesHelper.getInstance().getCheckIntrusion()) setDialog();
        SharedPreferencesHelper.getInstance().setCheckIntrusion(false);
    }

    class Task implements Runnable {
        @Override
        public void run() {
            BM_StorageHandler storageHandler = BM_StorageHandler.getInstance();
            storageHandler.initListInMessageSet("\"|morning|\"");
            storageHandler.initListInMessageSet("\"|night|\"");
            storageHandler.initListInMessageSet("\"|eat|\"");
            storageHandler.initListInMessageSet("\"|miss|\"");
        }
    }

    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.buttonConfirm:
                BM_Moment moment = new BM_Moment();
                moment.setSchedule();
                break;
        }

    }

    private void setAlarmFromDb()
    {
        List<SentenceNode> list = BM_ModelScheduler.getInstance().getAllNodes();
        for(SentenceNode node : list)
        {
            BM_MessageHandler.getInstance().setMesageAlarmTime(node);
        }
    }
}



