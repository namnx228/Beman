
package com.uet.beman.support;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.uet.beman.fragment.BM_FragmentDays;
import com.uet.beman.fragment.BM_FragmentGps;
import com.uet.beman.fragment.BM_FragmentInfo;
import com.uet.beman.fragment.BM_FragmentIntelligentMessage;
import com.uet.beman.fragment.BM_FragmentMessageList;
import com.uet.beman.fragment.BM_FragmentPhoneNumber;
import com.uet.beman.fragment.BM_FragmentWelcomeScreen;
import com.uet.beman.fragment.BM_FragmentWifi;
import com.uet.beman.fragment.BM_MomentConfirm;

/**
 * Created by PhanDuy on 3/31/2015.
 */
public class BM_ViewPagerAdapter extends FragmentPagerAdapter {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 9;

    BM_FragmentWelcomeScreen BMFragmentWelcomeScreen;
    BM_FragmentInfo BMFragmentInfo;
    BM_FragmentPhoneNumber BMFragmentPhoneNumber;
    BM_FragmentWifi BMFragmentWifi;
    BM_FragmentDays BMFragmentDays;
    BM_FragmentMessageList BMFragmentMessageList;
    BM_FragmentIntelligentMessage BMFragmentIntelligentMessage;
    BM_FragmentGps BMFragmentGPS;
    BM_MomentConfirm BMMomentConfirm;

    public BM_ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                if (this.BMFragmentWelcomeScreen == null) this.BMFragmentWelcomeScreen = new BM_FragmentWelcomeScreen();
                return this.BMFragmentWelcomeScreen;
            case 1:
                if (this.BMFragmentInfo == null) this.BMFragmentInfo = new BM_FragmentInfo();
                return this.BMFragmentInfo;
            case 2:
                if (this.BMFragmentPhoneNumber == null) this.BMFragmentPhoneNumber = new BM_FragmentPhoneNumber();
                return this.BMFragmentPhoneNumber;

            case 3:
                if(this.BMFragmentWifi == null) this.BMFragmentWifi = new BM_FragmentWifi();
                return this.BMFragmentWifi;
            case 4:
                if(this.BMFragmentGPS == null) this.BMFragmentGPS = new BM_FragmentGps();
                return this.BMFragmentGPS;

            case 5:
                if(this.BMFragmentMessageList == null) this.BMFragmentMessageList = new BM_FragmentMessageList();
                return this.BMFragmentMessageList;
            case 6:
                if(this.BMFragmentDays == null) this.BMFragmentDays = new BM_FragmentDays();
                return this.BMFragmentDays;

            case 7:
                if (this.BMMomentConfirm == null) this.BMMomentConfirm = new BM_MomentConfirm();
                return this.BMMomentConfirm;

            case 8:
                if(BMFragmentIntelligentMessage == null) BMFragmentIntelligentMessage = new BM_FragmentIntelligentMessage();
                return BMFragmentIntelligentMessage;

        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
