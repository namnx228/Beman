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
import com.uet.beman.operator.BM_StopSend;
import com.uet.beman.support.BM_GPStracker;
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
public class BM_FragmentGps extends Fragment implements SwitchCompat.OnCheckedChangeListener{
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



    private SwitchCompat homeSwitch;
    private SwitchCompat workSwitch;
    private SwitchCompat girlSwitch;

    private  boolean onCreateViewRunning;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView", "start onCreateViewGps");
        onCreateViewRunning = true;

        View view = inflater.inflate(R.layout.fragment_gps, container, false);


        // Update name reference
        TextView headline = (TextView) view.findViewById(R.id.fragment_gps_headline);
        String name = SharedPreferencesHelper.getInstance().getUserName();
        BM_Utils.updateNameReferences(headline, getResources(), R.string.line_fragment_gps1, name);

        // Initialize wifi switches
        homeSwitch = (SwitchCompat) view.findViewById(R.id.fragment_gps_switch_home);
        homeSwitch.setOnCheckedChangeListener(this);
        homeSwitch.setChecked(SharedPreferencesHelper.getInstance().getGpsCheck(Constant.HOME_GPS_CHECKED));

        workSwitch = (SwitchCompat) view.findViewById(R.id.fragment_gps_switch_work);
        workSwitch.setOnCheckedChangeListener(this);
        workSwitch.setChecked(SharedPreferencesHelper.getInstance().getGpsCheck(Constant.WORK_GPS_CHECKED));

        girlSwitch = (SwitchCompat) view.findViewById(R.id.fragment_gps_switch_girl);
        girlSwitch.setOnCheckedChangeListener(this);
        girlSwitch.setChecked(SharedPreferencesHelper.getInstance().getGpsCheck(Constant.GIRL_GPS_CHECKED));


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

    private void notifyAfterSetGps(String longitudeOfPlace, String latitudeOfPlace, Float longitude, Float latitude)
    {
        String msg = longitudeOfPlace + " is " + longitude.toString() + '\n'
                     + latitudeOfPlace + " is " + latitude.toString();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        Log.d("gps home",msg);
    }

    private void notifyAfterTurnOffGps(String place)
    {
        String msg = place + " gps is turn off";
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }



    private void setGps(int id)
    {
        //Toast.makeText(getActivity(),"vao day", Toast.LENGTH_LONG).show();
        BM_GPStracker gpsTracker = new BM_GPStracker(getActivity());
        float longitude = gpsTracker.getLongitude(), latitude = gpsTracker.getLatitude();
        SharedPreferencesHelper preference = SharedPreferencesHelper.getInstance();
        String longitudeOfPlace = "", latitudeOfPlace = "";
        switch (id) {
            case R.id.fragment_gps_switch_home:
                longitudeOfPlace = Constant.HOME_LONGITUDE;
                latitudeOfPlace = Constant.HOME_LATITUDE;
                preference.setGpsCheck(Constant.HOME_GPS_CHECKED, true);

                break;
            case R.id.fragment_gps_switch_work:
                longitudeOfPlace = Constant.WORK_LONGITUDE;
                latitudeOfPlace = Constant.WORK_LATITUDE;
                preference.setGpsCheck(Constant.WORK_GPS_CHECKED, true);
                break;
            case R.id.fragment_gps_switch_girl:
                longitudeOfPlace = Constant.GIRL_LONGITUDE;
                latitudeOfPlace = Constant.GIRL_LATITUDE;
                preference.setGpsCheck(Constant.GIRL_GPS_CHECKED, true);
                BM_StopSend stopSend = new BM_StopSend();
                stopSend.setGirlGps(true);
                break;
        }
        preference.setLongitude(longitudeOfPlace, longitude);
        preference.setLatitude(latitudeOfPlace, latitude);
        notifyAfterSetGps(longitudeOfPlace, latitudeOfPlace, longitude, latitude);
    }

    private void setGpsToZero(int id)
    {
        String longitudeOfPlace = "", latitudeOfPlace = "", place = "";
        SharedPreferencesHelper preference = SharedPreferencesHelper.getInstance();
        switch (id) {
            case R.id.fragment_gps_switch_home:
                longitudeOfPlace = Constant.HOME_LONGITUDE;
                latitudeOfPlace = Constant.HOME_LATITUDE;
                place = "Home";
                preference.setGpsCheck(Constant.HOME_GPS_CHECKED, false);
                break;
            case R.id.fragment_gps_switch_work:
                longitudeOfPlace = Constant.WORK_LONGITUDE;
                latitudeOfPlace = Constant.WORK_LATITUDE;
                place = "Work";
                preference.setGpsCheck(Constant.WORK_GPS_CHECKED, false);
                break;
            case R.id.fragment_gps_switch_girl:
                longitudeOfPlace = Constant.GIRL_LONGITUDE;
                latitudeOfPlace = Constant.GIRL_LATITUDE;
                place = "Girl's home";
                preference.setGpsCheck(Constant.GIRL_GPS_CHECKED, false);
                BM_StopSend stopSend = new BM_StopSend();
                stopSend.setGirlGps(false);
                break;
        }
        notifyAfterTurnOffGps(place);
        preference.setLongitude(longitudeOfPlace, 0);
        preference.setLatitude(latitudeOfPlace, 0);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (onCreateViewRunning)
            return;
        if (isChecked) setGps(buttonView.getId());
        else setGpsToZero(buttonView.getId());
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
