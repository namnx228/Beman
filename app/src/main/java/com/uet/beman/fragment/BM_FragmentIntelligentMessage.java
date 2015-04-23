package com.uet.beman.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.uet.beman.R;
import com.uet.beman.support.BM_MakeBotRequest;
import com.uet.beman.support.BM_RequestTask;

public class BM_FragmentIntelligentMessage extends Fragment implements View.OnClickListener, BM_RequestTask.DisplayResponse {

    private EditText messageRequest;
    private Button requestButton;
    private EditText messageResponse;

    private OnFragmentInteractionListener mListener;

    public BM_FragmentIntelligentMessage() {
        // Required empty public constructor
    }

    public static BM_FragmentIntelligentMessage newInstance() {
        BM_FragmentIntelligentMessage fragment = new BM_FragmentIntelligentMessage();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bm__fragment_intelligent_message, container, false);

        messageRequest = (EditText) view.findViewById(R.id.edit_text_message_request);
        messageResponse = (EditText) view.findViewById(R.id.edit_text_message_response);

        requestButton = (Button) view.findViewById(R.id.button_request);
        requestButton.setOnClickListener(this);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_request:
                String request = BM_MakeBotRequest.makeRequest(messageRequest.getText().toString());
                BM_RequestTask requestTask = new BM_RequestTask();
                requestTask.setCallback(this);
                requestTask.execute(request);
        }
    }

    @Override
    public void displayResponse(String response) {
        if (response != null)
            messageResponse.setText(response);
        else
            messageResponse.setText("NULL");
    }

    public interface OnFragmentInteractionListener {
    }

}
