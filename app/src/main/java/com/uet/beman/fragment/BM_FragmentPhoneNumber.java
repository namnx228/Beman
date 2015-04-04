package com.uet.beman.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.uet.beman.R;
import com.uet.beman.common.SharedPreferencesHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BM_FragmentPhoneNumber.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BM_FragmentPhoneNumber#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BM_FragmentPhoneNumber extends Fragment implements View.OnClickListener{
    public static final int PICK_CONTACT = 1;
    private OnFragmentInteractionListener mListener;
    SharedPreferencesHelper sharedPreferencesHelper;
    Button button;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BM_FragmentPhoneNumber.
     */
    // TODO: Rename and change types and number of parameters
    public static BM_FragmentPhoneNumber newInstance() {
        BM_FragmentPhoneNumber fragment = new BM_FragmentPhoneNumber();
        return fragment;
    }

    public BM_FragmentPhoneNumber() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_number, container, false);
        button = (Button)view.findViewById(R.id.numberBtn);
        button.setOnClickListener(this);
        String name = sharedPreferencesHelper.getDestName();
        String no = sharedPreferencesHelper.getDestNumber();
        if(name.isEmpty() && no.isEmpty()) {
            onFragmentChange(false);
        } else {
            button.setText(name +"\n"+no);
        }
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onFragmentChange(boolean status) {
        if (mListener != null) {
            mListener.onFragmentInteraction(status);
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
        public void onFragmentInteraction(boolean status);
    }

    private String getGirlNumber() {
        return sharedPreferencesHelper.getDestNumber();
    }

    private void setGirlNumber(String number) {
        sharedPreferencesHelper.setDestNumber(number);
    }

    private String getGirlName() {
        return sharedPreferencesHelper.getDestName();
    }

    private void setGirlName(String name) {
        sharedPreferencesHelper.setDestName(name);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  getActivity().getContentResolver().query(contactData, null, null, null, null);
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String name = "";
                        String no = "";

                        Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);
                        if(phoneCursor.moveToFirst()) {
                            name = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            no = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
//                        String number = c.getString(c.getColumnIndex(ContactsContract.Data.DATA1));
                        // TODO Whatever you want to do with the selected contact name.
                        setGirlName(name);
                        setGirlNumber(no);
                        String displayResult = name + "\n" + no;
                        button.setText(displayResult);
                        onFragmentChange(true);
                    }
                    c.close();
                }
                break;
        }
    }



}
