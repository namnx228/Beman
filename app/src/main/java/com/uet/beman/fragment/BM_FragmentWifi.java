package com.uet.beman.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.uet.beman.R;
import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.object.ManageWifiDialogFragment;
import com.uet.beman.util.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BM_FragmentWifi.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BM_FragmentWifi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BM_FragmentWifi extends Fragment implements CompoundButton.OnCheckedChangeListener,
        ManageWifiDialogFragment.ManageWifiClickListener {

    private OnFragmentInteractionListener mListener;

    private SharedPreferencesHelper spHelper;

    private SwitchCompat homeSwitch;
    private SwitchCompat workSwitch;
    private SwitchCompat girlSwitch;

    private static boolean onCreateViewRunning;

    public static BM_FragmentWifi newInstance() {
        BM_FragmentWifi fragment = new BM_FragmentWifi();
        return fragment;
    }

    public BM_FragmentWifi() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate", "start onCreate");
        super.onCreate(savedInstanceState);
        spHelper = SharedPreferencesHelper.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView", "start onCreateView");
        onCreateViewRunning = true;

        View view = inflater.inflate(R.layout.fragment_wifi, container, false);

        homeSwitch = (SwitchCompat) view.findViewById(R.id.switch_home_wifi);
        homeSwitch.setOnCheckedChangeListener(this);
        homeSwitch.setChecked(spHelper.getWifiState(Constant.HOME_WIFI_STATE));

        workSwitch = (SwitchCompat) view.findViewById(R.id.switch_work_wifi);
        workSwitch.setOnCheckedChangeListener(this);
        workSwitch.setChecked(spHelper.getWifiState(Constant.WORK_WIFI_STATE));

        girlSwitch = (SwitchCompat) view.findViewById(R.id.switch_girl_wifi);
        girlSwitch.setOnCheckedChangeListener(this);
        girlSwitch.setChecked(spHelper.getWifiState(Constant.GIRL_WIFI_STATE));

        onCreateViewRunning = false;
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (onCreateViewRunning)
            return;

        switch (buttonView.getId()) {
            case R.id.switch_home_wifi:
                spHelper.setWifiState(Constant.HOME_WIFI_STATE, isChecked);
                if (isChecked)
                    if (!showWifiDialog(Constant.HOME_WIFI_LIST))
                        homeSwitch.setChecked(false);
                break;
            case R.id.switch_work_wifi:
                spHelper.setWifiState(Constant.WORK_WIFI_STATE, isChecked);
                if (isChecked)
                    if (!showWifiDialog(Constant.WORK_WIFI_LIST))
                        workSwitch.setChecked(false);
                break;
            case R.id.switch_girl_wifi:
                spHelper.setWifiState(Constant.GIRL_WIFI_STATE, isChecked);
                if (isChecked)
                    if (!showWifiDialog(Constant.GIRL_WIFI_LIST))
                        girlSwitch.setChecked(false);
                break;
        }
    }

    private boolean showWifiDialog(String key) {
        if (!checkEnableWifi()) {
            Toast.makeText(getActivity(), "Please enable Wifi", Toast.LENGTH_SHORT).show();
            return false;
        }

        ArrayList<String> savedWifi = getSavedWifi(key);
        ArrayList<String> configuredWifi = getConfiguredWifi();

        int requestCode = -1;
        switch (key) {
            case Constant.HOME_WIFI_LIST:
                requestCode = 0;
                break;
            case Constant.WORK_WIFI_LIST:
                requestCode = 1;
                break;
            case Constant.GIRL_WIFI_LIST:
                requestCode = 2;
                break;
        }

        ManageWifiDialogFragment frag = ManageWifiDialogFragment.getInstance(configuredWifi, savedWifi);
        frag.setTargetFragment(this, requestCode);
        frag.show(getFragmentManager(), "wifi dialog");

        return true;
    }

    private boolean checkEnableWifi() {
        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    private ArrayList<String> getSavedWifi(String key) {
        return spHelper.getWifiList(key);
    }

    private ArrayList<String> getConfiguredWifi() {
        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        ArrayList<String> res = new ArrayList<>();
        if (wifiManager != null) {
            List<WifiConfiguration> wifiConfig = wifiManager.getConfiguredNetworks();
            if (wifiConfig != null && !wifiConfig.isEmpty())
                for (WifiConfiguration wc : wifiConfig) {
                    String s = wc.SSID;
                    s = s.substring(1, s.length() - 1);
                    res.add(s);
                }
        }
        res = removeDuplicate(res);
        Collections.sort(res);
        return res;
    }

    private ArrayList<String> removeDuplicate(ArrayList<String> list) {
        if (list == null)
            return null;
        HashSet<String> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    public void saveWifiList(int requestCode, ArrayList<String> wifiList) {
        String key = null;
        switch (requestCode) {
            case 0:
                key = Constant.HOME_WIFI_LIST;
                break;
            case 1:
                key = Constant.WORK_WIFI_LIST;
                break;
            case 2:
                key = Constant.GIRL_WIFI_LIST;
                break;
        }

        if (key != null) {
            spHelper.setWifiList(key, wifiList);
        } else {
            Toast.makeText(getActivity(), "Save fail!", Toast.LENGTH_SHORT).show();
        }
    }
}
