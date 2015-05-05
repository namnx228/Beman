package com.uet.beman.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uet.beman.R;
import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.support.BM_MakeBotRequest;
import com.uet.beman.support.BM_RequestTask;

public class BM_FragmentIntelligentMessage extends Fragment implements View.OnClickListener, BM_RequestTask.DisplayResponse
        , CompoundButton.OnCheckedChangeListener {

    private EditText messageRequest;
    private Button requestButton;
    private EditText messageResponse;
    private Button setTimeReplyButton;
    private EditText timeForReply;
    private SwitchCompat enableReply;
    private TextView textView;
    private OnFragmentInteractionListener mListener;

    private boolean onCreateView;

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
        onCreateView = true;
        View view = inflater.inflate(R.layout.fragment_bm__fragment_intelligent_message, container, false);


        messageRequest = (EditText) view.findViewById(R.id.edit_text_message_request);
        messageResponse = (EditText) view.findViewById(R.id.edit_text_message_response);

        requestButton = (Button) view.findViewById(R.id.button_request);
        requestButton.setOnClickListener(this);

        setTimeReplyButton = (Button) view.findViewById(R.id.buttonReply);
        setTimeReplyButton.setOnClickListener(this);

        timeForReply = (EditText) view.findViewById(R.id.timeForReply);

        enableReply = (SwitchCompat) view.findViewById(R.id.switchReply);
        enableReply.setChecked(SharedPreferencesHelper.getInstance().getAutoReply());
        enableReply.setOnCheckedChangeListener(this);

        textView = (TextView) view.findViewById(R.id.textReply);

        setVisibleForReplyOption(SharedPreferencesHelper.getInstance().getAutoReply());

        onCreateView = false;
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
                requestTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
                //BM_MessageHandler.getInstance().prepareSendReply(messageRequest.getText().toString());
                break;
            case R.id.buttonReply:
                SharedPreferencesHelper.getInstance().setReplyWaitTime(Integer.valueOf(timeForReply.getText().toString()));
                Toast.makeText(getActivity(), "Time waiting for reply = " +
                        SharedPreferencesHelper.getInstance().getReplyWaitTime() + "s", Toast.LENGTH_LONG).show();
                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        if (onCreateView) return;
        SharedPreferencesHelper.getInstance().setAutoReply(isChecked);
        setVisibleForReplyOption(isChecked);
    }

    @Override
    public void displayResponse(String response) {
        if (response != null)
            messageResponse.setText(response);
        else
            messageResponse.setText("NULL");
    }

    private void setVisibleForReplyOption(boolean check) {
        int status;
        if (check) status = View.VISIBLE;
        else status = View.GONE;

        textView.setVisibility(status);
        timeForReply.setVisibility(status);
        setTimeReplyButton.setVisibility(status);

    }

    public interface OnFragmentInteractionListener {
    }

}
