package com.uet.beman.fragment;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uet.beman.R;
import com.uet.beman.common.BM_Utils;
import com.uet.beman.common.SharedPreferencesHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BM_FragmentDays.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BM_FragmentDays#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BM_FragmentDays extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedPreferencesHelper sharedPreferencesHelper;
    char[] charArray;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    GridLayout gridDays;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BM_FragmentDays.
     */
    // TODO: Rename and change types and number of parameters
    public static BM_FragmentDays newInstance(String param1, String param2) {
        BM_FragmentDays fragment = new BM_FragmentDays();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BM_FragmentDays() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance();
        charArray = sharedPreferencesHelper.getDaysPreferences().toCharArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bm_fragment_days, container, false);
        gridDays = (GridLayout) view.findViewById(R.id.grid_days);
        createButtons(SharedPreferencesHelper.LENGTH_OF_PREFERENCES);

        // Update name reference
        TextView headline = (TextView) view.findViewById(R.id.fragment_days_headline);
        String name = SharedPreferencesHelper.getInstance().getUserName();
        BM_Utils.updateNameReferences(headline, getResources(), R.string.line_fragment_days, name);

        // Inflate the layout for this fragment
        return view;
    }

    private void createButtons(int totalNum) {
        for(int i = 0; i < totalNum; i++) {
            Button btn = new Button(this.getActivity());
            btn.setId(i);
            btn.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
            if(charArray[i] == '1') {
                btn.getBackground().setColorFilter(getResources().getColor(R.color.md_green_600), PorterDuff.Mode.MULTIPLY);
            }
            btn.setOnClickListener(this);
            gridDays.addView(btn);
        }
    }

    @Override
    public void onClick(View view) {

        Button btn = (Button)view.findViewById(view.getId());
        int viewId = view.getId();
        if(charArray[viewId] == '0') {
            btn.getBackground().setColorFilter(getResources().getColor(R.color.md_green_600), PorterDuff.Mode.MULTIPLY);
            charArray[viewId] = '1';
        } else {
            btn.getBackground().setColorFilter(getResources().getColor(R.color.md_white_1000), PorterDuff.Mode.MULTIPLY);
            charArray[viewId] = '0';
        }
        String str1 = new String(charArray);
        sharedPreferencesHelper.setDaysPreferences(str1);
        String str = sharedPreferencesHelper.getDaysPreferences();
        Toast.makeText(getActivity(),str, Toast.LENGTH_LONG).show();
        switch(view.getId()) {
            case 1:
                //first button click
                break;
            //Second button click
            case 2:
                break;
            case 3:
                //third button click
                break;
            case 4:
                //fourth button click
                break;

            default:
                break;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
