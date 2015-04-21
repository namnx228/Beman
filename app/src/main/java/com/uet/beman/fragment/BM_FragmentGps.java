package com.uet.beman.fragment;

import android.app.Activity;
import android.net.Uri;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BM_FragmentGps.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BM_FragmentGps#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BM_FragmentGps extends Fragment implements CompoundButton.OnCheckedChangeListener{
        //View.OnClickListener{

    //ManageWifiDialogFragment.ManageWifiClickListener
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



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


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BM_FragmentGps.
     */
    // TODO: Rename and change types and number of parameters
    public static BM_FragmentGps newInstance(String param1, String param2) {
        BM_FragmentGps fragment = new BM_FragmentGps();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BM_FragmentGps() {
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

        View view = inflater.inflate(R.layout.fragment_gps, container, false);


        // Update name reference
        TextView headline = (TextView) view.findViewById(R.id.fragment_gps_headline);
        String name = SharedPreferencesHelper.getInstance().getUserName();
        BM_Utils.updateNameReferences(headline, getResources(), R.string.line_fragment_gps1, name);

        // Initialize wifi switches
        homeSwitch = (SwitchCompat) view.findViewById(R.id.fragment_gps_switch_home);
        homeSwitch.setOnCheckedChangeListener(this);


        workSwitch = (SwitchCompat) view.findViewById(R.id.fragment_gps_switch_work);
        workSwitch.setOnCheckedChangeListener(this);


        girlSwitch = (SwitchCompat) view.findViewById(R.id.fragment_gps_switch_girl);
        girlSwitch.setOnCheckedChangeListener(this);


        // Initialize add buttons

        // Initialize wifi detail text views
        //loadWifiDetail();

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (onCreateViewRunning)
            return;

        switch (buttonView.getId()) {
            case R.id.fragment_gps_switch_home:
//                spHelper.setWifiState(Constant.HOME_WIFI_STATE, isChecked);
                break;
            case R.id.fragment_gps_switch_work:
  //              spHelper.setWifiState(Constant.WORK_WIFI_STATE, isChecked);
                break;
            case R.id.fragment_wifi_switch_girl:
    //            spHelper.setWifiState(Constant.GIRL_WIFI_STATE, isChecked);
                break;
        }
    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_wifi_add_button_home:
                //showWifiDialog(Constant.HOME_WIFI_LIST);
                break;
            case R.id.fragment_wifi_add_button_work:
                //showWifiDialog(Constant.WORK_WIFI_LIST);
                break;
            case R.id.fragment_wifi_add_button_girl:
                //showWifiDialog(Constant.GIRL_WIFI_LIST);
                break;
        }*/


   /* private void showWifiDialog(String key) {
        if (!checkEnableWifi()) {
            Toast.makeText(getActivity(), "Please enable Wifi", Toast.LENGTH_SHORT).show();
            return;
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
    }*/

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
