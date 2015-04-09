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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uet.beman.R;
import com.uet.beman.common.BM_Utils;
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
        ManageWifiDialogFragment.ManageWifiClickListener, View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private SharedPreferencesHelper spHelper;

    private SwitchCompat homeSwitch;
    private SwitchCompat workSwitch;
    private SwitchCompat girlSwitch;

    private ImageView homeAddButton;
    private ImageView workAddButton;
    private ImageView girlAddButton;

    private TextView homeTextView;
    private TextView workTextView;
    private TextView girlTextView;

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

        // Update name reference
        TextView headline = (TextView) view.findViewById(R.id.fragment_wifi_headline);
        String name = SharedPreferencesHelper.getInstance().getUserName();
        BM_Utils.updateNameReferences(headline, getResources(), R.string.line_fragment_wifi1, name);

        // Initialize wifi switches
        homeSwitch = (SwitchCompat) view.findViewById(R.id.fragment_wifi_switch_home);
        homeSwitch.setOnCheckedChangeListener(this);
        homeSwitch.setChecked(spHelper.getWifiState(Constant.HOME_WIFI_STATE));

        workSwitch = (SwitchCompat) view.findViewById(R.id.fragment_wifi_switch_work);
        workSwitch.setOnCheckedChangeListener(this);
        workSwitch.setChecked(spHelper.getWifiState(Constant.WORK_WIFI_STATE));

        girlSwitch = (SwitchCompat) view.findViewById(R.id.fragment_wifi_switch_girl);
        girlSwitch.setOnCheckedChangeListener(this);
        girlSwitch.setChecked(spHelper.getWifiState(Constant.GIRL_WIFI_STATE));

        // Initialize add buttons
        homeAddButton = (ImageView) view.findViewById(R.id.fragment_wifi_add_button_home);
        homeAddButton.setOnClickListener(this);

        workAddButton = (ImageView) view.findViewById(R.id.fragment_wifi_add_button_work);
        workAddButton.setOnClickListener(this);

        girlAddButton = (ImageView) view.findViewById(R.id.fragment_wifi_add_button_girl);
        girlAddButton.setOnClickListener(this);

        // Initialize wifi detail text views
        homeTextView = (TextView) view.findViewById(R.id.fragment_wifi_text_view_detail_home);
        workTextView = (TextView) view.findViewById(R.id.fragment_wifi_text_view_detail_work);
        girlTextView = (TextView) view.findViewById(R.id.fragment_wifi_text_view_detail_girl);
        loadWifiDetail();

        onCreateViewRunning = false;
        return view;
    }

    private void loadWifiDetail() {
        TextView tv[] = {homeTextView, workTextView, girlTextView};
        String key[] = {Constant.HOME_WIFI_LIST, Constant.WORK_WIFI_LIST, Constant.GIRL_WIFI_LIST};
        for (int i = 0; i < tv.length; i++) {
            ArrayList<String> wifiList = spHelper.getWifiList(key[i]);
            if (wifiList.isEmpty()) {
                tv[i].setText("");
                continue;
            }
            String text = "";
            for (String s : wifiList)
                text += s + " ; ";
            text = text.substring(0, text.length() - 3);
            tv[i].setText(text);
        }
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
            case R.id.fragment_wifi_switch_home:
                spHelper.setWifiState(Constant.HOME_WIFI_STATE, isChecked);
                break;
            case R.id.fragment_wifi_switch_work:
                spHelper.setWifiState(Constant.WORK_WIFI_STATE, isChecked);
                break;
            case R.id.fragment_wifi_switch_girl:
                spHelper.setWifiState(Constant.GIRL_WIFI_STATE, isChecked);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_wifi_add_button_home:
                showWifiDialog(Constant.HOME_WIFI_LIST);
                break;
            case R.id.fragment_wifi_add_button_work:
                showWifiDialog(Constant.WORK_WIFI_LIST);
                break;
            case R.id.fragment_wifi_add_button_girl:
                showWifiDialog(Constant.GIRL_WIFI_LIST);
                break;
        }
    }

    private void showWifiDialog(String key) {
        if (!checkEnableWifi())
            Toast.makeText(getActivity(), "Please enable Wifi", Toast.LENGTH_SHORT).show();

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
            loadWifiDetail();
        } else {
            Toast.makeText(getActivity(), "Save fail!", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}
